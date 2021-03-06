/**
 * 
 */
package seprini.models;

import java.util.Random;

import seprini.data.Art;
import seprini.data.Config;

import com.badlogic.gdx.math.Vector2;

/**
 * @author mjm540
 * 
 */
public class Cloud extends Entity {

	/**
	 * 
	 */

	Random rnd = new Random();
	private static final float INITIAL_VELOCITY_SCALAR = 1f;

	private int altitude;
	private Vector2 velocity = new Vector2(0, 0);

	private float velocityScalar;

	private final float magic_constant = 100.0f;

	public Cloud() {

		coords = rnd.nextBoolean() ? new Vector2((int) Math.round(rnd
				.nextDouble() * Config.SCREEN_WIDTH),
				rnd.nextBoolean() ? Config.SCREEN_HEIGHT : 0) : new Vector2(
				rnd.nextBoolean() ? Config.SCREEN_WIDTH : 0,
				(int) Math.round(rnd.nextDouble() * Config.SCREEN_HEIGHT));

		// allows drawing debug shape of this entity
		debugShape = true;

		// initialise all of the aircraft values according to the passed
		// aircraft type
		size = new Vector2(146 - 62, 756 - 722);
		this.setScale(5.0f);
		texture = Art.getTextureRegion("clouds");
		velocityScalar = INITIAL_VELOCITY_SCALAR;
		velocity = new Vector2(Config.SCREEN_WIDTH / 2,
				Config.SCREEN_HEIGHT / 2)
				.sub(coords)
				.scl((float) Math.sqrt(((size.x * this.getScaleX()) / (float) Config.SCREEN_WIDTH)
						* ((size.y * this.getScaleY()) / (float) Config.SCREEN_HEIGHT)))
				.scl(velocityScalar / magic_constant); // new
		// Vector2(10,
		// 10);

		altitude = Config.ALTITUDES[Config.ALTITUDES.length - 1];

		// set origin to center of the aircraft, makes rotation more intuitive
		this.setOrigin(size.x / 2, size.y / 2);

		coords.add(velocity.cpy().scl(-magic_constant));

	}

	/**
	 * Update the aircraft rotation & position
	 */
	public void act() {
		// if player is holding D or -> on the keyboard, turn right

		// finally updating coordinates
		coords.add(velocity);

	}

	public int getAltitude() {
		return altitude;
	}

	public float getSpeed() {
		return velocityScalar;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

}
