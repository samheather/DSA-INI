package seprini.models;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.math.Vector2;

/**
 * Implements an airport
 * Stores planes which enter the airport, launches planes when told to.
 * @author mjm540
 *
 */
public class Airport extends Waypoint {

	private Stack<Aircraft> aircraft = new Stack<Aircraft>();
	private final int airportCapacity = 3;


	@Override
	public boolean handleCollision(Aircraft a) {
		if (a.popWaypoint())
			return false;
		a.restoreWaypoints();
		if (canLand()) {
			a.deselect();
			// Remove plane image for this plane from list of sprites
			a.remove();
			// Remove plane from list of planes so Physics no longer applied to
			// it.
			a.removeFromAircraftListToAvoidFramerateProblems();
			aircraft.push(a);
			return true;
		} else {
			a.setCanControl(true);
			return false;
		}
	}

	/**
	 * Constructor - sets up with position of airport.
	 */
	public Airport() {
		super(new Vector2(400, 345), false);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter - get number of planes currently in the airport.
	 * @return number of aircraft in airport.
	 */
	public int getNumberInAirport() {
		return aircraft.size();
	}

	/**
	 * Returns if able to land a new plane at airport, if there is capacity
	 * @return boolean if can land
	 */
	public boolean canLand() {
		return getNumberInAirport() < airportCapacity;
	}

	/**
	 * Returns if can launch plane from airport, if there are planes stored.
	 * @return boolean if can launch
	 */
	public boolean canLaunch() {
		return getNumberInAirport() > 0;
	}

	/**
	 * Lands a aircraft, given as a parameter.  Directs it to the airport, where it will land.
	 * @param newAircraft
	 */
	public void land(Aircraft newAircraft) {
		// Create new list of waypoints, containing the Airport only.
		ArrayList<Waypoint> newFlightPlan = new ArrayList<Waypoint>();
		newFlightPlan.add(this);
		// Add the new flightplan to the aircraft's flightplan stack
		newAircraft.waypoints(newFlightPlan);
	}

	/**
	 * Launches an aircraft.
	 * @return
	 */
	public Aircraft launch() {
		// if not able to launch, return null, to be handled externally
		if (!canLaunch())
			return null;
		return aircraft.pop();
	}

}
