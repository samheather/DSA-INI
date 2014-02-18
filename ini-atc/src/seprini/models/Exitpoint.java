package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public final class Exitpoint extends Waypoint {

	protected int radius;
	private boolean hidden;
	
	@Override
	public void handleCollision(Aircraft a) {
		super.handleCollision(a);
		seprini.data.State.changeScore(25);
		a.remove();
		a.removeFromAircraftListToAvoidFramerateProblems();
	}
	
	public Exitpoint(Vector2 position, boolean hidden) {
		this(position);
		this.hidden = hidden;
	}

	public Exitpoint(Vector2 position) {
		super(position, false);
		this.texture = Art.getTextureRegion("exitpoint");
	}
}
