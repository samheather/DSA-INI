package seprini.models;

import seprini.data.Art;
import seprini.data.Config;

import com.badlogic.gdx.math.Vector2;

public class Waypoint extends Entity {

	private final boolean deletable;
	

	public void handleCollision(Aircraft a) {
		a.waypoints.remove(0);
	}

	public Waypoint(Vector2 position, boolean deletable) {
		coords = position;
		this.deletable = deletable;
		init();
	}

	public Waypoint(float x, float y, boolean deletable) {
		coords = new Vector2(x, y);
		this.deletable = deletable;
		init();
	}

	private void init() {
		this.debugShape = true;
		this.texture = Art.getTextureRegion("waypoint");
		this.size = Config.WAYPOINT_SIZE;

		// set the origin to the centre
		this.setOrigin(getWidth() / 2, getHeight() / 2);

		// set its bounds so it's clickable
		this.setBounds(getX() - getWidth() / 2, getY() - getWidth() / 2,
				getWidth(), getHeight());
	}

	public boolean isDeletable() {
		return deletable;
	}

	@Override
	public String toString() {
		return "Waypoint - x: " + getX() + " y: " + getY();
	}

	public Waypoint cpy() {
		return new Waypoint(getX(), getY(), this.deletable);
	}

}
