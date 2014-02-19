package seprini.models;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import seprini.controllers.AircraftController;
import seprini.data.Config;
import seprini.data.Debug;
import seprini.data.State;
import seprini.models.types.AircraftType;
import seprini.screens.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public final class Aircraft extends Entity {

	private final int id;

	private static final float INITIAL_VELOCITY_SCALAR = 1f;
	private static final float SPEED_CHANGE = 0.1f;
	private static final int ALTITUDE_CHANGE = 5000; 
	private int desiredAltitude;

	private int altitude;
	private Vector2 velocity = new Vector2(0, 0);

	private Stack<ArrayList<Waypoint>> waypointStack = new Stack<ArrayList<Waypoint>>();

	public ArrayList<Waypoint> waypoints() {
		return waypointStack.peek();
	}

	public boolean popWaypoint() {
		if (waypoints().size() > 0)
			waypoints().remove(0);
		return waypoints().size() > 0;
	}

	public void waypoints(ArrayList<Waypoint> newwaypoints) {
		waypointStack.push(newwaypoints);
	}

	public ArrayList<Waypoint> restoreWaypoints() {
		return waypointStack.pop();
	}

	private final float radius, separationRadius, maxTurningRate, maxClimbRate,
			maxSpeed;

	double minSpeed;

	private float velocityScalar;

	@SuppressWarnings("unused")
	private final int sepRulesBreachCounter = 0;
	private boolean breaching;
	private boolean isActive = true;
	// When user has taken control of the
	// aircraft

	private boolean canControl;
	private boolean ignorePath = false;

	// whether the aircraft is selected by the player
	private boolean selected;

	private boolean turnRight, turnLeft;

	// used for smooth turning
	// remember last angle to check if it's increasing or not
	private float previousAngle = 0;
	// if is increasing, switch rotation sides so it uses the 'smaller' angle
	private boolean rotateRight = false;

	private AircraftController aircrafts;

	public void removeFromAircraftListToAvoidFramerateProblems() {
		aircrafts.remove(this);
	}

	public void deselect() {
		aircrafts.deselectAircraft(this);
	}

	public Aircraft(AircraftType aircraftType, ArrayList<Waypoint> flightPlan,
			int id, AircraftController aircrafts) {
		this.aircrafts = aircrafts;

		// allows drawing debug shape of this entity
		debugShape = true;

		this.id = id;

		// initialise all of the aircraft values according to the passed
		// aircraft type
		radius = aircraftType.getRadius();
		separationRadius = aircraftType.getSeparationRadius();
		texture = aircraftType.getTexture();
		maxTurningRate = aircraftType.getMaxTurningSpeed();
		maxClimbRate = aircraftType.getMaxClimbRate();
		maxSpeed = aircraftType.getMaxSpeed();
		minSpeed = Math.max((maxSpeed - 1.5), 0.4);
		velocityScalar = INITIAL_VELOCITY_SCALAR;
		velocity = aircraftType.getVelocity();
		canControl = aircraftType.getControllable();

		Random rand = new Random();
		altitude = Config.ALTITUDES[rand.nextInt(Config.ALTITUDES.length)];
		desiredAltitude = altitude;

		// set the flightplan to the generated by the controller
		waypoints(flightPlan);

		// set the size
		size = new Vector2(76, 63);

		// set the coords to the entry point, remove it from the flight plan
		Waypoint entryPoint = waypoints().get(0);
		coords = new Vector2(entryPoint.getX(), entryPoint.getY());
		waypoints().remove(0);

		// set origin to center of the aircraft, makes rotation more intuitive
		this.setOrigin(size.x / 2, size.y / 2);

		this.setScale(0.5f);

		// set bounds so the aircraft is clickable
		this.setBounds(getX() - getWidth() / 2, getY() - getWidth() / 2,
				getWidth(), getHeight());

		// set rotation & velocity angle to fit next waypoint
		float relativeAngle = relativeAngleToWaypoint();

		this.velocity.setAngle(relativeAngle);
		this.setRotation(relativeAngle);

		velocity.len();

		Debug.msg("||\nGenerated aircraft id " + id + "\nEntry point: "
				+ coords + "\nRelative angle to first waypoint: "
				+ relativeAngle + "\nVelocity" + velocity + "\nWaypoints: "
				+ waypoints() + "\n||");
	}

	/**
	 * Additional drawing for if the aircraft is breaching
	 * 
	 * @param batch
	 */
	@Override
	protected void additionalDraw(SpriteBatch batch) {

		ShapeRenderer drawer = Screen.shapeRenderer;

		// if the user takes control of the aircraft, draw a line to the
		// exitpoint
		if (selected) {

			batch.end();

			drawer.begin(ShapeType.Line);
			drawer.setColor(1, 0, 0, 0);
			drawer.line(getX(), getY(), waypoints().get(0).getX(), waypoints()
					.get(0).getY());
			for (int i = 1; i < waypoints().size(); i++) {
				drawer.line(waypoints().get(i - 1).getX(),
						waypoints().get(i - 1).getY(), waypoints().get(i)
								.getX(), waypoints().get(i).getY());
			}
			drawer.end();

			batch.begin();
		}

		// if the aircraft is either selected or is breaching, draw a circle
		// around it
		if (selected || breaching) {
			batch.end();

			drawer.begin(ShapeType.Line);
			drawer.setColor(1, 0, 0, 0);
			drawer.circle(getX(), getY(), getSeparationRadius() * 0.5f);
			drawer.end();

			batch.begin();
		}

		// draw the altitude for each aircraft
		Color color;

		if (getAltitude() <= 7500) {
			color = Color.GREEN;
		} else if (getAltitude() <= 12500) {
			color = Color.ORANGE;
		} else if (getAltitude() > 12500) {
			color = Color.RED;
		} else {
			color = Color.BLACK;
		}

		Screen.drawString("alt: " + getAltitude(), getX() - 30, getY() - 20,
				color, batch, true, 1);

		// Changing scores for violation rules
		if (this.breaching) {
			seprini.data.State.changeScore(-3 * Gdx.graphics.getDeltaTime());
		}

		// debug line from aircraft centre to waypoint centre
		if (Config.DEBUG_UI) {
			Vector2 nextWaypoint = vectorToWaypoint();

			batch.end();

			drawer.begin(ShapeType.Line);
			drawer.setColor(1, 0, 0, 0);
			drawer.line(getX(), getY(), nextWaypoint.x, nextWaypoint.y);
			drawer.end();

			batch.begin();
		}

	}

	/**
	 * Update the aircraft rotation & position
	 */
	public boolean act() {
		// System.out.println("rofl");
		// if player is holding D or -> on the keyboard, turn right
		if (turnRight && this.canControl) {
			turnRight();
		}

		// if the player is holding A or <-, turn left
		if (turnLeft && this.canControl) {
			turnLeft();
		}

		// if the player has taken control of the aircraft, ignore all waypoints
		if (!ignorePath) {

			// Vector to next waypoint
			Vector2 nextWaypoint = vectorToWaypoint();

			// relative angle from the aircraft coordinates to the next waypoint
			float relativeAngle = angleCoordsToWaypoint(nextWaypoint);

			// smoothly rotate aircraft
			// sets a threshold due to float imprecision, should be generally
			// relativeAngle != 0
			if (relativeAngle > 1) {

				// if the current angle is bigger than the previous, it means we
				// are rotating towards the wrong side
				if (previousAngle < relativeAngle) {
					// switch to rotate to the other side
					rotateRight = !rotateRight;
				}

				// instead of using two rotation variables, it is enough to
				// store one and just switch that one
				if (rotateRight) {
					rotate(maxTurningRate);
				} else {
					rotate(-maxTurningRate);
				}

				// save the current angle as the previous angle for the next
				// iteration
				previousAngle = relativeAngle;
			}

			// checking whether aircraft is at the next waypoint. Whether it's
			// close enough is dictated by the WP size in the config.
			if (nextWaypoint.sub(coords).len() < Config.WAYPOINT_SIZE.x / 2) {
				boolean removed = false;
				if (removed = waypoints().get(0).handleCollision(this))
					return removed;
			}

			// set velocity angle to fit rotation, allows for smooth turning
			velocity.setAngle(getRotation());
		}

		// For when the user takes control of the aircraft. Allows the aircraft
		// to detect when it is at its designated exit WP.
		if (waypoints().get(waypoints().size() - 1).cpy().getCoords()
				.sub(coords).len() < Config.EXIT_WAYPOINT_SIZE.x / 2) {
			waypoints().get(0).handleCollision(this);
		}

		// finally updating coordinates
		coords.add(velocity.cpy().scl(velocityScalar));

		// allows for smooth decent/ascent
		if (this.canControl) {
			if (altitude > desiredAltitude) {
				this.altitude -= this.maxClimbRate;
			} else if (altitude < desiredAltitude) {
				this.altitude += this.maxClimbRate;
			}
		}

		// updating bounds to make sure the aircraft is clickable
		this.setBounds(getX() - getWidth() / 2, getY() - getWidth() / 2,
				getWidth(), getHeight());
		return false;
	}

	/**
	 * Calculate the angle between the aircraft's coordinates and the vector the
	 * next waypoint
	 * 
	 * @param waypoint
	 * @return angle IN DEGREES, NOT RADIANS
	 */
	private float angleCoordsToWaypoint(Vector2 waypoint) {
		Vector2 way = new Vector2(waypoint.x - coords.x, waypoint.y - coords.y)
				.nor();
		Vector2 coord = velocity.cpy().nor();

		float angle = (float) Math.toDegrees(Math.acos(way.dot(coord)
				/ way.len() * coord.len()));

		return angle;
	}

	/**
	 * Calculates the vector to the next waypoint
	 * 
	 * @return 3d vector to the next waypoint
	 */
	private Vector2 vectorToWaypoint() {
		// Creates a new vector to store the new velocity in temporarily
		Vector2 nextWaypoint = new Vector2();

		// round it to 2 points after decimal, makes it more manageable later
		nextWaypoint.x = (float) (Math
				.round(waypoints().get(0).getCoords().x * 100.0) / 100.0);
		nextWaypoint.y = (float) (Math
				.round(waypoints().get(0).getCoords().y * 100.0) / 100.0);

		return nextWaypoint;
	}

	/**
	 * Calculate relative angle of the aircraft to the next waypoint
	 * 
	 * @return relative angle in degrees, rounded to 2 points after decimal
	 */
	private float relativeAngleToWaypoint() {
		return relativeAngleToWaypoint(vectorToWaypoint());
	}

	/**
	 * Calculate relative angle of the aircraft to a waypoint
	 * 
	 * @param waypoint
	 * @return angle in degrees, rounded to 2 points after decimal
	 */
	private float relativeAngleToWaypoint(Vector2 waypoint) {
		return new Vector2(waypoint.x - getX(), waypoint.y - getY()).angle();
	}

	/**
	 * Adding a new waypoint to the head of the arraylist
	 * 
	 * @param newWaypoint
	 */
	public void insertWaypoint(Waypoint newWaypoint) {
		waypoints().add(0, newWaypoint);
	}

	/**
	 * Increase speed of the aircraft <br>
	 * Actually changes a scalar which is later multiplied by the velocity
	 * vector
	 * 
	 * @return <b>true</b> on success <br>
	 *         <b>false</b> when increased speed will be more than allowed
	 *         (maxSpeed)
	 */
	public boolean increaseSpeed() {
		if ((!selected)
				|| velocity.cpy().scl(velocityScalar + SPEED_CHANGE).len() > maxSpeed)
			return false;

		velocityScalar += SPEED_CHANGE;

		Debug.msg("Decrease speed; Velocity Scalar: " + velocityScalar);

		return true;
	}

	/**
	 * Decrease speed of the aircraft <br>
	 * Actually changes a scalar which is later multiplied by the velocity
	 * vector
	 * 
	 * @return <b>true</b> on success <br>
	 *         <b>false</b> when decreased speed will be less than allowed
	 *         (minSpeed)
	 */
	public boolean decreaseSpeed() {

		// If plane not selected or the velocity scaled by the magnitude of
		// velocityScalar-SPEED_CHANGE is less than the minimum speed allowed
		// for the plane, don't change velocity and return false.

		if ((!selected)
				|| velocity.cpy().scl(velocityScalar - SPEED_CHANGE).len() < minSpeed)
			return false;

		// Else, change the velocity as requested and return true.
		velocityScalar -= SPEED_CHANGE;

		Debug.msg("Increasing speed; Velocity scalar: " + velocityScalar);

		return true;
	}

	/**
	 * Increases rate of altitude change
	 */
	public void increaseAltitude() {
		if (desiredAltitude + ALTITUDE_CHANGE > 15000) {
			return;
		}

		this.desiredAltitude += ALTITUDE_CHANGE;
	}

	/**
	 * Decreasing rate of altitude change
	 */
	public void decreaseAltitude() {
		if (desiredAltitude - ALTITUDE_CHANGE < 5000) {
			return;
		}

		this.desiredAltitude -= ALTITUDE_CHANGE;
	}

	public void turnRight(boolean set) {
		if (set)
			this.setIgnorePath(true);
		turnRight = set;
	}

	public void turnLeft(boolean set) {
		if (set)
			this.setIgnorePath(true);
		turnLeft = set;
	}

	/**
	 * Turns right by maxTurningRate * 2
	 */
	public void turnRight() {
		float angle = 0;
		for (int i = 0; i < waypoints().size(); i++) {
			if (i != waypoints().size() - 1) {
				waypoints().remove(i);
			}
		}

		if (getRotation() - maxTurningRate * 2 < 0) {
			angle = (float) (360 - maxTurningRate * 2);
		}

		if (angle == 0) {
			this.rotate(-maxTurningRate * 2);
		} else {
			this.setRotation(angle);
		}

		velocity.setAngle(getRotation());
	}

	/**
	 * Turns left by maxTurningRate * 2
	 */
	public void turnLeft() {
		float angle = 0;
		for (int i = 0; i < waypoints().size(); i++) {
			if (i != waypoints().size() - 1) {
				waypoints().remove(i);
			}
		}

		if (getRotation() + maxTurningRate * 2 >= 360.0f) {
			angle = (float) (maxTurningRate * 2);
		}

		if (angle == 0) {
			this.rotate(maxTurningRate * 2);
		} else {
			this.setRotation(angle);
		}

		velocity.setAngle(getRotation());
	}

	/**
	 * Regular regular getter for radius
	 * 
	 * @return int radius
	 */
	public float getRadius() {
		return radius;
	}

	public float getSeparationRadius() {
		return separationRadius;
	}

	public void isBreaching(boolean is) {
		this.breaching = is;
	}

	public int getAltitude() {
		return altitude;
	}

	public boolean getIgnorePath() {
		return ignorePath;
	}

	public void setIgnorePath(boolean x) {
		ignorePath = x;
	}

	/**
	 * Returns aircraft velocity scalar times 700
	 * 
	 * @return the velocity scalar
	 */
	public float getSpeed() {
		return velocityScalar;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * Returns false if aircraft flightplan is empty, true otherwise.
	 * 
	 * @return whether is activeprivate int score;
	 */
	public boolean isActive() {
		// FIXME
		if (getX() < -10 || getY() < -10 || getX() > Config.SCREEN_WIDTH - 190
				|| getY() > Config.SCREEN_HEIGHT + 105) {
			this.isActive = false;

			State.changeScore(-20);
		}

		if (waypoints().size() == 0) {
			System.out.println("Fatal error: plane with zero waypoints: "
					+ this.id);
			System.exit(2);
			this.isActive = false;
		}

		return this.isActive;
	}

	/**
	 * Setter for selected
	 * 
	 * @param newSelected
	 * @return whether is selected
	 */
	public boolean selected(boolean newSelected) {
		if (canControl) {
			return this.selected = newSelected;
		}
		return false;
	}

	/**
	 * Setter for canControl
	 * 
	 * @param b
	 */
	public void setCanControl(boolean b) {
		this.canControl = b;
	}

	@Override
	public String toString() {
		return "Aircraft - x: " + getX() + " y: " + getY()
				+ "\n\r flight plan: " + waypoints().toString();
	}

	public boolean canControl() {
		// TODO Auto-generated method stub
		return this.canControl;
	}
}
