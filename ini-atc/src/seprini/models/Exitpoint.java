package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public final class Exitpoint extends Waypoint {

	protected int radius;

	@Override
	public boolean handleCollision(Aircraft a) {
		super.handleCollision(a);
		seprini.data.State.changeScore(25);
		a.remove();
		a.removeFromAircraftListToAvoidFramerateProblems();
		return true;
	}

	public Exitpoint(Vector2 position) {
		super(position, false);
		this.texture = Art.getTextureRegion("exitpoint");
	}
}
