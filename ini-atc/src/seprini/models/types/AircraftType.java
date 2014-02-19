package seprini.models.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AircraftType {

	private String aircraftName;
	private Vector2 coords;
	private Vector2 velocity;
	private int radius;
	private int separationRadius;
	private TextureRegion texture;
	private float maxTurningSpeed;
	private float maxClimbRate;
	private float maxSpeed;
	private boolean isActive;
	private int probability;
	private boolean controllable = true;
	
	/**
	 * gets coords for the aircraft
	 * 
	 * @return coords
	 */
	public Vector2 getCoords() {
		return coords.cpy();
	}
	
	/**
	 * sets coords for the aircraft
	 * 
	 * @param coords
	 * 
	 */
	public AircraftType setCoords(Vector2 coords) {
		this.coords = coords;
		return this;
	}
	
	/**
	 * gets the velocity of the aircraft
	 * 
	 * @return velocity
	 */
	public Vector2 getVelocity() {
		return velocity.cpy();
	}

	/**
	 * sets velocity of the aircraft
	 * 
	 */
	public AircraftType setVelocity(Vector2 velocity) {
		this.velocity = velocity;
		return this;
	}

	/**
	 * getter for the radius of the plane
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * sets the radius for the aircraft
	 * 
	 * @param radius
	 * 
	 */
	public AircraftType setRadius(int radius) {
		this.radius = radius;
		return this;
	}

	/**
	 * gets the separation radius for the aircraft
	 * 
	 * @return separationRadius
	 */
	public int getSeparationRadius() {
		return separationRadius;
	}

	/**
	 * sets the separation radius for the aircraft
	 * 
	 * @param separationRadius
	 * 
	 */
	public AircraftType setSeparationRadius(int separationRadius) {
		this.separationRadius = separationRadius;
		return this;
	}

	/**
	 * gets the Texture size of the aircraft
	 * 
	 * @return texture
	 */
	public TextureRegion getTexture() {
		return texture;
	}
	
	/**
	 * sets the textureRegion for the aircraft
	 * 
	 * @param texture
	 */
	public AircraftType setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}
	
	/**
	 * gets the fastest turning speed the aircraft can turn at
	 * 
	 * @return maxTurningSpeed
	 */
	public float getMaxTurningSpeed() {
		return maxTurningSpeed;
	}
	
	/**
	 * sets the maximum turning speed for the aircraft
	 * 
	 * @param maxTurningSpeed
	 */
	public AircraftType setMaxTurningSpeed(float maxTurningSpeed) {
		this.maxTurningSpeed = maxTurningSpeed;
		return this;
	}
	
	/**
	 * gets the maximum rate at which the plane can climb 
	 * 
	 * @return maxClimbRate
	 */
	public float getMaxClimbRate() {
		return maxClimbRate;
	}
	
	/**
	 * sets the maximum rate at which the aircraft can climb
	 * 
	 * @param maxClimbRate
	 */
	public AircraftType setMaxClimbRate(float maxClimbRate) {
		this.maxClimbRate = maxClimbRate;
		return this;
	}
	
	/**
	 * gets the maximum speed a plane can travel at
	 * 
	 * @return maxSpeed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	/**
	 * sets the maximum speed for the aircraft
	 * 
	 * @param maxSpeed
	 */
	public AircraftType setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
		return this;
	}
	
	/**
	 * checks if the current plane has waypoints and is within the bounds of the screen
	 * 
	 * @return isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * sets if the current aircraft is active
	 * 
	 * @param isActive
	 */
	public AircraftType setActive(boolean isActive) {
		this.isActive = isActive;
		return this;
	}
	
	/**
	 * gets the name of the aircraft
	 * 
	 * @return aircraftName
	 */
	public String getAircraftName() {
		return aircraftName;
	}
	
	/**
	 * sets a name for the aircraft
	 * 
	 * @param aircraftName
	 */
	public AircraftType setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
		return this;
	}
	
	/**
	 * gets whether the aircraft is controllable
	 * 
	 * @return controllable
	 */
	public boolean getControllable() {
		return this.controllable;
	}
	
	/**
	 * sets if the current aircraft is controllable
	 * 
	 * @param canControl
	 */
	public AircraftType setControllable(boolean canControl) {
		this.controllable = canControl;
		return this;
	}
	
	/**
	 * gets the probability for the aircraft to spawn
	 * 
	 * @param coords
	 * @return
	 */
	public int getProbability() {
		return this.probability;
	}
	
	/**
	 * sets the spawn probability for the aircraft to spawn
	 * 
	 * @param coords
	 * @return
	 */
	public AircraftType setProbability(int chance) {
		this.probability = chance;
		return this;
	}
}
