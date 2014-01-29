package seprini.data;

import com.badlogic.gdx.Gdx;

public class State {

	private static State instance = null;
	private static float score;

	public static boolean paused = false;
	private static float time = 0;

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
		incScore(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Get time
	 * 
	 * @return delta time added every frame
	 */
	public static float time() {
		return time;
	}

	/**
	 * Reset timer
	 */
	public static void reset() {
		time = 0;
	}
	
	public static void resetScore() {
		score = 0;
	}
	
	public static void incScore(float increment) {
		score += increment;
	}
	
	public static int getScore() {
		return Math.round(score);
	}

}
