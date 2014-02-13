package seprini.models;

import seprini.data.Art;

import com.badlogic.gdx.math.Vector2;

public final class Exitpoint extends Waypoint {

	protected int radius;
	private boolean hidden;
	
	public Exitpoint(Vector2 position, boolean hidden) {
		this(position);
		this.hidden = hidden;
	}

	public Exitpoint(Vector2 position) {
		super(position, false);
		this.texture = Art.getTextureRegion("exitpoint");
	}
}
