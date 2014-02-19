package seprini.controllers;

import java.util.ArrayList;
import java.util.Random;

import seprini.controllers.components.FlightPlanComponent;
import seprini.controllers.components.WaypointComponent;
import seprini.data.Art;
import seprini.data.Config;
import seprini.data.Debug;
import seprini.data.GameDifficulty;
import seprini.data.State;
import seprini.models.Aircraft;
import seprini.models.Airport;
import seprini.models.Airspace;
import seprini.models.Cloud;
import seprini.models.Map;
import seprini.models.Waypoint;
import seprini.models.types.AircraftType;
import seprini.screens.EndScreen;
import seprini.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * This is the controller for aircraft.
 * @author Samuel
 *
 */
public final class AircraftController extends InputListener implements
		Controller {

	Random rand = new Random();

	// aircraft and aircraft type lists
	private final ArrayList<AircraftType> aircraftTypeList = new ArrayList<AircraftType>();
	private final ArrayList<Aircraft> aircraftList = new ArrayList<Aircraft>();
	private final ArrayList<Cloud> clouds = new ArrayList<Cloud>();

	// Ints for controlling the spawning of aircraft.
	private final int maxAircraft, timeBetweenGenerations, separationRadius;

	// Values for controlling alert of user when separation rules are broken.
	private float lastWarned;
	private float lastGenerated = State.time();
	private boolean breachingSound, breachingIsPlaying;

	// The base types of aircraft
	private final AircraftType defaultAircraft = new AircraftType();
	private final AircraftType speedyAircraft = new AircraftType();
	private final AircraftType slowyAircraft = new AircraftType();
	private final AircraftType snakeyAircraft = new AircraftType();

	// Total probabilities - sum of all probabilities, necessary to allow us to
	// generate aircraft according to their probability.
	private int totalProbabilities;

	private Aircraft selectedAircraft;
	private boolean hasCollided = false;

	private final GameDifficulty difficulty;

	// helpers for this class
	private final WaypointComponent waypoints;
	private final FlightPlanComponent flightplan;

	// ui related
	private final Airspace airspace;
	private final SidebarController sidebar;
	private final GameScreen screen;

	private int aircraftId = 0;
	
	/**
	 * Adds a cloud to the airspace.
	 * @param c
	 */
	private void addCloud(Cloud c) {
		clouds.add(c);
		airspace.addActor(c);
	}

	MenuController mc;

	/**
	 * Constructor for aircraft controller, creates appropriate controller, e.g.
	 * for the difficulty.
	 * @param diff
	 *            game difficulty, changes number of aircraft and time between
	 *            them
	 * @param airspace
	 *            the group where all of the waypoints and aircraft will be
	 *            added
	 * @param sidebar
	 * @param screen
	 */
	public AircraftController(GameDifficulty diff, Airspace airspace,
			Table sidebar, GameScreen screen, MenuController mc) {
		this.mc = mc;
		this.difficulty = diff;
		this.airspace = airspace;
		this.screen = screen;

		// add the background
		airspace.addActor(new Map());

		// manages the sidebar
		this.sidebar = new SidebarController(sidebar, this, screen, mc);

		// manages the waypoints
		this.waypoints = new WaypointComponent(this, this.sidebar);

		// helper for creating the flight plan of an aircraft
		this.flightplan = new FlightPlanComponent(waypoints);

		// insert code here to initialise variables (eg max no of aircraft) to
		// wanted value for that difficulty level.
		switch (difficulty) {
		case EASY:
			maxAircraft = 10;
			timeBetweenGenerations = 3;
			separationRadius = 100;
			State.setDifficultyMultiplier(1);
			break;
		case MEDIUM:
			maxAircraft = 15;
			timeBetweenGenerations = 2;
			separationRadius = 125;
			State.setDifficultyMultiplier(1.5f);
			break;
		case HARD:
			maxAircraft = 30;
			timeBetweenGenerations = 1;
			separationRadius = 150;
			State.setDifficultyMultiplier(2);
			break;
		default:
			maxAircraft = 10;
			timeBetweenGenerations = 3;
			separationRadius = 100;
			State.setDifficultyMultiplier(1);
			break;
		}

		// initialise aircraft types.
		defaultAircraft.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(10).setMaxSpeed(1.5f).setMaxTurningSpeed(0.8f)
				.setRadius(15).setSeparationRadius(separationRadius)
				.setTexture(Art.getTextureRegion("aircraft"))
				.setVelocity(new Vector2(0.8f, 0.8f)).setProbability(3);

		speedyAircraft.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(10).setMaxSpeed(2.3f).setMaxTurningSpeed(1.2f)
				.setRadius(15).setSeparationRadius(separationRadius)
				.setTexture(Art.getTextureRegion("fastAircraft"))
				.setVelocity(new Vector2(1.3f, 1.3f)).setProbability(2);

		slowyAircraft.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(10).setMaxSpeed(0.8f).setMaxTurningSpeed(0.8f)
				.setRadius(15).setSeparationRadius(separationRadius)
				.setTexture(Art.getTextureRegion("slowAircraft"))
				.setVelocity(new Vector2(0.4f, 0.4f)).setProbability(1);

		snakeyAircraft.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(10).setMaxSpeed(1.5f).setMaxTurningSpeed(0.8f)
				.setRadius(15).setSeparationRadius(separationRadius)
				.setTexture(Art.getTextureRegion("snakeyaircraft"))
				.setVelocity(new Vector2(0.8f, 0.8f)).setControllable(false)
				.setProbability(1);

		// add aircraft types to airplaneTypes array.
		aircraftTypeList.add(defaultAircraft);
		aircraftTypeList.add(speedyAircraft);
		aircraftTypeList.add(slowyAircraft);
		aircraftTypeList.add(snakeyAircraft);

		// Calculate totalProbability
		for (AircraftType plane : aircraftTypeList) {
			totalProbabilities += plane.getProbability();
			plane.setProbability(totalProbabilities);
		}

		// Initialise the sidebar with values.
		this.sidebar.init();
	}

	/**
	 * Updates the aircraft positions. Generates a new aircraft and adds it to
	 * the stage. Collision Detection. Removes aircraft if inactive.
	 */

	float lastCloud = State.time();

	public void update() {

		// Updates all clouds positions
		for (int i = 0; i < clouds.size(); i++) {
			clouds.get(i).act();
		}
		float t = State.time();
		if (t - lastCloud > 30) {
			addCloud(new Cloud());
			lastCloud = t;
		}

		Aircraft planeI, planeJ;

		breachingSound = false;

		// wait at least 2 seconds before allowing to warn again of incoming 
		// collision
		breachingIsPlaying = (State.time() - lastWarned >= 2) ? false : true;

		// Updates aircraft in turn
		// Removes aircraft which are no longer active from aircraftList.
		// Manages collision detection.
		for (int i = 0; i < aircraftList.size(); i++) {
			// Update aircraft.
			planeI = aircraftList.get(i);
			
			// if a plane is inactive, remove it and update the index
			if (!planeI.isActive()) {
				removeAircraft(planeI);
				--i;
				continue;
			}
			// if a plane is removed during its update, update the index
			if (planeI.act()) {
				--i;
				continue;
			}
			planeI.isBreaching(false);

			// Collision Detection + Separation breach detection.
			for (int j = 0; j < aircraftList.size(); j++) {

				/*
				 * Quite simply checks if distance between the centres of both
				 * the aircraft <= the radius of aircraft i + radius of aircraft
				 * j
				 */
				planeJ = aircraftList.get(j);

				if (!planeI.equals(planeJ)
						// Check difference in altitude.
						&& Math.abs(planeI.getAltitude() - planeJ.getAltitude()) < Config.MIN_ALTITUDE_DIFFERENCE
						// Check difference in horizontal 2d plane.
						&& planeI.getCoords().dst(planeJ.getCoords()) < planeI
								.getRadius() + planeJ.getRadius()
						&& (!hasCollided)) {
					hasCollided = true;
					collisionHasOccured(planeI, planeJ);
				}

				// Checking for breach of separation.
				if (!planeI.equals(planeJ)
						// Check difference in altitude.
						&& Math.abs(planeI.getAltitude() - planeJ.getAltitude()) < planeI
								.getSeparationRadius()
						// Check difference in horizontal 2d plane.
						&& planeI.getCoords().dst(planeJ.getCoords()) < planeI
								.getSeparationRadius()) {

					separationRulesBreached(planeI, planeJ);
				}
			}

			if (planeI.getAltitude() <= 0) {
				screen.setScreen(new EndScreen());
			}

		}

		// make sure the breaching sound plays only when a separation breach
		// occurs. Also makes sure it start playing it only one time so there
		// aren't multiple warning sounds at the same time
		if (breachingSound && !breachingIsPlaying) {
			breachingIsPlaying = true;
			lastWarned = State.time();
			Art.getSound("warning").play(1.0f);
		}

		// try to generate a new aircraft

		if (!(aircraftList.size() + airport.getNumberInAirport() >= maxAircraft))
			if (!(State.time() - lastGenerated < timeBetweenGenerations
					+ rand.nextInt(100)))
				generateAircraft();

		seprini.data.State.changeScore(-airport.getNumberInAirport()
				* Gdx.graphics.getDeltaTime());
		// Update the values in the sidebar every frame, so they see the time 
		// they've played for and their current score.
		sidebar.update();

	}

	/**
	 * Handles what happens after a collision
	 * 
	 * @param a
	 *            first aircraft that collided
	 * @param b
	 *            second aircraft that collided
	 */
	private void collisionHasOccured(Aircraft a, Aircraft b) {
		// stop the ambience sound and play the crash sound
		Art.getSound("ambience").stop();
		Art.getSound("crash").play(0.6f);

		// change the screen to the endScreen
		screen.setScreen(new EndScreen());
	}

	private Airport airport = new Airport();

	/**
	 * Handles what happens after the separation rules have been breached
	 * 
	 * @param a
	 *            first aircraft that breached
	 * @param b
	 *            second aircraft that breached
	 */
	private void separationRulesBreached(Aircraft a, Aircraft b) {
		// for scoring mechanisms, if applicable
		a.isBreaching(true);
		b.isBreaching(true);
		breachingSound = true;
	}

	/**
	 * Generates aircraft of random type with 'random' flight plan.
	 * <p>
	 * Checks if maximum number of aircraft is not exceeded. If it isn't, a new
	 * aircraft is generated with the arguments randomAircraftType() and
	 * generateFlightPlan().
	 * 
	 * @return an <b>Aircraft</b> if the following conditions have been met: <br>
	 *         a) there are no more aircraft on screen than allowed <br>
	 *         b) enough time has passed since the last aircraft has been
	 *         generated <br>
	 *         otherwise <b>null</b>
	 * 
	 */
	private Aircraft generateAircraft() {
		return generateAircraft(flightplan.generate());
	}

	private Aircraft generateAircraft(ArrayList<Waypoint> waypoints) {
		// number of aircraft has reached maximum, abort
		if (aircraftList.size() + airport.getNumberInAirport() >= maxAircraft)
			return null;

		// time difference between aircraft generated - depends on difficulty
		// selected
		// if (State.time() - lastGenerated < timeBetweenGenerations
		// + rand.nextInt(100))
		// return null;

		AircraftType act = randomAircraftType();
		if (act == snakeyAircraft) {
			sidebar.addEvent(mc.planeMsg);
		}

		final Aircraft newAircraft = new Aircraft(act, waypoints, aircraftId++,
				this);

		aircraftList.add(newAircraft);

		// store the time when an aircraft was last generated to know when to
		// generate the next aircraft
		lastGenerated = State.time();
		// if the newly generated aircraft is not null (ie checking one was
		// generated), add it as an actor to the stage
		if (newAircraft != null) {

			// makes the aircraft clickable. Once clicked it is set as the
			// selected aircraft.
			newAircraft.addListener(new ClickListener() {

				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (event.getButton() == 0) {
						selectAircraft(newAircraft);
						// launchPlane();

					}

				}

			});

			// push the aircraft to the top so it's infront of the user created
			// waypoints
			newAircraft.toFront();

			// add it to the airspace (stage group) so its automatically drawn
			// upon calling root.draw()
			airspace.addActor(newAircraft);

			// play a sound to audibly inform the player that an aircraft as
			// spawned
			Art.getSound("ding").play(0.5f);
		}

		// finally, update the sidebar
		sidebar.update();
		return newAircraft;
	}

	/**
	 * Selects random aircraft type from aircraftTypeList.
	 * 
	 * @return AircraftType
	 */
	private AircraftType randomAircraftType() {
		int randNum = rand.nextInt(totalProbabilities);
		for (AircraftType plane : aircraftTypeList) {
			if (randNum > plane.getProbability()) {
				return plane;
			}
		}
		return aircraftTypeList.get(rand.nextInt(aircraftTypeList.size()));
	}

	/**
	 * Removes aircraft from aircraftList at index i.
	 * 
	 * @param planeI
	 */
	private void removeAircraft(Aircraft planeI) {

		if (planeI.equals(selectedAircraft))
			selectedAircraft = null;

		// removes the aircraft from the list of aircrafts on screen
		aircraftList.remove(planeI);

		// removes the aircraft from the stage
		planeI.remove();
	}

	/**
	 * Selects an aircraft.
	 * 
	 * @param aircraft
	 */
	private void selectAircraft(Aircraft aircraft) {
		// make sure old selected aircraft is no longer selected in its own
		// object
		if (selectedAircraft != null) {
			selectedAircraft.selected(false);
			
		// make sure old aircraft is not turning in circles
			selectedAircraft.turnLeft(false);
			selectedAircraft.turnRight(false);
		}

		// set new selected aircraft
		selectedAircraft = aircraft;

		// make new aircraft know it's selected
		selectedAircraft.selected(true);
	}

	public void deselectAircraft(Aircraft aircraft) {
		// make sure old selected aircraft is no longer selected in its own
		// object
		if (selectedAircraft == aircraft) {
			selectedAircraft.selected(false);
			selectedAircraft = null;
		}
	
		
	}

	/**
	 * Redirects aircraft to another waypoint.
	 * 
	 * @param waypoint
	 *            Waypoint to redirect to
	 */
	public void redirectAircraft(Waypoint waypoint) {
		Debug.msg("Redirecting aircraft " + 0 + " to " + waypoint);

		if (getSelectedAircraft() == null)
			return;
		selectedAircraft.setIgnorePath(false);
		getSelectedAircraft().insertWaypoint(waypoint);
	}

	/**
	 * Getter for currently selected aircraft.
	 * @return
	 */
	public Aircraft getSelectedAircraft() {
		return selectedAircraft;
	}

	/**
	 * Getter for aircraft list.
	 * @return
	 */
	public ArrayList<Aircraft> getAircraftList() {
		return aircraftList;
	}

	/**
	 * Getter for the current airspace.
	 * @return
	 */
	public Airspace getAirspace() {
		return airspace;
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {

		if (button == Buttons.LEFT && sidebar.allowNewWaypoints()) {
			// Add new waypoint for plane to touch down at.
			waypoints.createWaypoint(x, y, false);
			return true;
		}

		return false;
	}

	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		// If keys are one of the pre-selected ones perform action (e.g. press
		// left and plane turns left)

		if (keycode == Keys.SPACE)
			State.paused = (State.paused) ? false : true;

		if (selectedAircraft != null && !State.paused) {

			if (keycode == Keys.LEFT || keycode == Keys.A)
				selectedAircraft.turnLeft(true);

			if (keycode == Keys.RIGHT || keycode == Keys.D)
				selectedAircraft.turnRight(true);

			if (keycode == Keys.UP || keycode == Keys.W)
				selectedAircraft.increaseAltitude();

			if (keycode == Keys.DOWN || keycode == Keys.S)
				selectedAircraft.decreaseAltitude();

			if (keycode == Keys.E)
				selectedAircraft.increaseSpeed();

			if (keycode == Keys.Q)
				selectedAircraft.decreaseSpeed();
		}

		return false;
	}

	@Override
	public boolean keyUp(InputEvent event, int keycode) {

		if (selectedAircraft != null) {

			if (keycode == Keys.LEFT || keycode == Keys.A)
				selectedAircraft.turnLeft(false);

			if (keycode == Keys.RIGHT || keycode == Keys.D)
				selectedAircraft.turnRight(false);

		}

		return false;
	}

	/**
	 * Lands a plane at the airport.
	 * @param newAircraft
	 */
	public void landPlane(Aircraft newAircraft) {
		if (!newAircraft.canControl())
			return;
		newAircraft.deselect();
		newAircraft.setCanControl(false);
		airport.land(newAircraft);
	}

	/**
	 * Called to launch a plane
	 * @return the launched plane
	 */
	public Aircraft launchPlane() {
		Aircraft a = null;
		// if the aircraft cant launch, do nothing
		if (airport.canLaunch()) {
			// launch an aircraft from the airport
			a = airport.launch();
			// set its inital waypoint to the airport
			a.insertWaypoint(airport);
			// add it to the list of aircraft
			aircraftList.add(a);
			// add it to the airspace
			airspace.addActor(a);
			// allow user control
			a.setCanControl(true);
		}
		return a;
	}

	/** 
	 * Getter for number of planes in airport
	 * @return
	 */
	public int getAirportPlaneCount() {
		return airport.getNumberInAirport();
	}

	/**
	 * Removes an aircraft from the aircraft list.
	 * @param aircraft
	 */
	public void remove(Aircraft aircraft) {
		aircraftList.remove(aircraft);

	}

}
