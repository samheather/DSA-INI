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
	private float probability;
	private boolean controllable = true;

	public Vector2 getCoords() {
		return coords.cpy();
	}

	public AircraftType setCoords(Vector2 coords) {
		this.coords = coords;
		return this;
	}

	public Vector2 getVelocity() {
		return velocity.cpy();
	}

	public AircraftType setVelocity(Vector2 velocity) {
		this.velocity = velocity;
		return this;
	}

	public int getRadius() {
		return radius;
	}

	public AircraftType setRadius(int radius) {
		this.radius = radius;
		return this;
	}

	public int getSeparationRadius() {
		return separationRadius;
	}

	public AircraftType setSeparationRadius(int separationRadius) {
		this.separationRadius = separationRadius;
		return this;
	}

	public TextureRegion getTexture() {
		return texture;
	}

	public AircraftType setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	public float getMaxTurningSpeed() {
		return maxTurningSpeed;
	}

	public AircraftType setMaxTurningSpeed(float maxTurningSpeed) {
		this.maxTurningSpeed = maxTurningSpeed;
		return this;
	}

	public float getMaxClimbRate() {
		return maxClimbRate;
	}

	public AircraftType setMaxClimbRate(float maxClimbRate) {
		this.maxClimbRate = maxClimbRate;
		return this;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public AircraftType setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public AircraftType setActive(boolean isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getAircraftName() {
		return aircraftName;
	}

	public AircraftType setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
		return this;
	}
	
	public boolean getControllable(){
		return this.controllable;
	}
	
	public AircraftType setControllable(boolean canControl){
		this.controllable = canControl;
		return this;
	}
	
	public float getProbability(){
		return this.probability;
	}
	
	public AircraftType setProbability(float chance){
		this.probability = chance;
		return this;
	}
}
