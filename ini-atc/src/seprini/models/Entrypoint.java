package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public class Entrypoint extends Waypoint {

	public Entrypoint(Vector2 position, boolean deletable) {
		super(position, deletable);
		this.texture = Art.getTextureRegion("entrypoint");
	}
	public void handleCollision(Aircraft a) {
		return;
	}
	

}
