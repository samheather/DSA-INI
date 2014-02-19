package seprini.models;

import seprini.data.Art;
import seprini.data.Config;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a waypoint in the game.
 * 
 * @author mjm540
 * 
 */
public class Waypoint extends Entity {

	// Waypoints initially created are not deletable, user can delete
	// waypoints that they create.
	private final boolean deletable;

	/**
	 * Function that's called when an aircraft that has this waypoint at the top
	 * of its flightplan hits this waypoint
	 * 
	 * @param a
	 *            The aircraft that collided
	 * @return Whether or not the aircraft was destroyed
	 */
	public boolean handleCollision(Aircraft a) {
		// Increment user score by 5 for going through waypoint as dictated by
		// flight plan
		seprini.data.State.changeScore(5);
		a.popWaypoint();
		return false;
	}



	/**
	 * Consturctor, where passed floats for x and y, for creating waypoint.
	 * @param x
	 * @param y
	 * @param deletable
	 */
	public Waypoint(float x, float y, boolean deletable) {
		this(new Vector2(x, y), deletable);
	}

	/**
	 * Constructor, where passed Vector2, for creating waypoint.
	 * @param position
	 * @param deletable
	 */
	public Waypoint(Vector2 position, boolean deletable) {
		coords = position;
		this.deletable = deletable;
		this.debugShape = true;
		this.texture = Art.getTextureRegion("waypoint");
		this.size = Config.WAYPOINT_SIZE;

		// set the origin to the centre
		this.setOrigin(getWidth() / 2, getHeight() / 2);

		// set its bounds so it's clickable
		this.setBounds(getX() - getWidth() / 2, getY() - getWidth() / 2,
				getWidth(), getHeight());
	}

	/**
	 * Getter for isDeletable
	 * @return
	 */
	public boolean isDeletable() {
		return deletable;
	}

	@Override
	public String toString() {
		return "Waypoint - x: " + getX() + " y: " + getY();
	}

	/**
	 * Creates a copy of this waypoint.
	 * @return
	 */
	public Waypoint cpy() {
		return new Waypoint(getX(), getY(), this.deletable);
	}

}
