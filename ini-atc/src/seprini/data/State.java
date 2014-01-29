package seprini.data;

import com.badlogic.gdx.Gdx;

public class State {

	private static State instance = null;

	public static boolean paused = false;
	private static double time = 0;

	private State() {

	}

	public static State getInstance() {
		if (instance == null) {
			instance = new State();
		}
		return instance;
	}

	/**
	 * Tick time; time += delta
	 */
	public synchronized static void tick() {
		time += Gdx.graphics.getDeltaTime();
	}

	/**
	 * Get time
	 * 
	 * @return delta time added every frame
	 */
	public static double time() {
		return time;
	}

	/**
	 * Reset timer
	 */
	public static void reset() {
		time = 0;
	}

}
