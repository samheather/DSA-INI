package seprini.controllers.components;

import java.util.ArrayList;
import java.util.Random;
import seprini.models.Exitpoint;
import seprini.models.Waypoint;

import com.badlogic.gdx.math.Vector2;

public class FlightPlanComponent {

	static Random rand = new Random();

	private WaypointComponent waypoints;

	public FlightPlanComponent(WaypointComponent waypoints) {
		this.waypoints = waypoints;

	}

	/**
	 * Generates a flight plan - a list of waypoints - for aircraft. Aircraft
	 * with the same entry and exit points will always follow the same route.
	 * 
	 * @return completeFlightPlan
	 */
	public ArrayList<Waypoint> generate() {

		// Initialisation of parameters required by flightPlanWaypointGenerator.
		ArrayList<Waypoint> flightPlan = new ArrayList<Waypoint>();
		Waypoint entryWaypoint = setStartpoint();
		//System.out.println("Entry point " + entryWaypoint.toString());
		Waypoint lastWaypoint = setEndpoint(entryWaypoint, 620 );
		//System.out.print("Exit point " + lastWaypoint.toString());
		//System.out.print("\n \n");
		
		// entryWaypoint immediately added to aircrafts flightPlan.
		flightPlan.add(entryWaypoint);

		return flightPlanWaypointGenerator(flightPlan, entryWaypoint,
				lastWaypoint);
	}

	/**
	 * Adds a selection of waypoints + lastWaypoint to flighPlan.
	 * 
	 * @param flightPlan
	 * @param currentWaypoint
	 * @param lastWaypoint
	 * @return completeFlightPlan
	 */
	private ArrayList<Waypoint> flightPlanWaypointGenerator(
			ArrayList<Waypoint> flightPlan, Waypoint currentWaypoint,
			Waypoint lastWaypoint) {

		// Base Case; self explanatory.
		if (currentWaypoint.equals(lastWaypoint)) {
			return flightPlan;
		}

		else {
			// Find normal vector from currentWaypoint to lastWaypoint and
			// normalise.
			Vector2 normalVectorFromCurrentToLast = (lastWaypoint.getCoords()
					.cpy().sub(currentWaypoint.getCoords())).nor();

			// Create the list of waypoints for the generator to choose from,
			// including the final waypoint so that the base case can be
			// satisfied;
			ArrayList<Waypoint> waypointSelectionList = new ArrayList<Waypoint>(waypoints.getPermanentList());
			waypointSelectionList.add(lastWaypoint);

			// Call selectNextWaypoint.
			Waypoint nextWaypoint = selectNextWaypoint(currentWaypoint,
					lastWaypoint, flightPlan, normalVectorFromCurrentToLast,
					waypointSelectionList, 30, 150);
			
			waypointSelectionList.clear();
			
			// Recurse with updated flightPlan and nextWaypoint.
			return flightPlanWaypointGenerator(flightPlan, nextWaypoint,
					lastWaypoint);
		}
	}
	/**
	 * Selects a waypoint to insert into flightPlan, under certain constraints.
	 * 
	 * @param currentWaypoint
	 * @param flightPlan
	 * @param normalVectorFromCurrentToLast
	 * @param waypointSelectionList
	 * @param maxAngle
	 *            - minimum angle from currentWaypoint to nextWaypoint, where 0
	 *            degrees is the angle from currentWaypoint to lastWaypoint
	 * @param minDistance
	 *            - minimum distance from currentWaypoint to nextWaypoint. Ideal
	 *            value = diameter of the turning circle of the aircraft.
	 * @return nextWaypoint
	 */
	private static Waypoint selectNextWaypoint(Waypoint currentWaypoint,
			Waypoint lastWaypoint, ArrayList<Waypoint> flightPlan,
			Vector2 normalVectorFromCurrentToLast,
			ArrayList<Waypoint> waypointSelectionList, int maxAngle,
			int minDistance) {
		Waypoint nextWaypoint = null;

		for (Waypoint waypoint : waypointSelectionList) {
			// Find normal vector from current item in waypointSelectionList to
			// lastWaypoint.
			Vector2 normalVectorFromCurrentToPotential = new Vector2(waypoint
					.getCoords().cpy().sub(currentWaypoint.getCoords())).nor();
			// Check that waypoint in waypointSelectoinList:
			// 1. Is not already in flighPlan
			// 2. Angle between normalVectorFromCurrentToPotential and
			// normalVectorFromCurrentToLast is less than specified maxAngle.
			// 3. Is minDistance away from currentWaypoint
			if (!flightPlan.contains(waypoint)
					// the acos returns a value of radians, which is then converted to degrees.
					&& (Math.acos(normalVectorFromCurrentToPotential
							.dot(normalVectorFromCurrentToLast)) * 180 / Math.PI) < maxAngle
					&& waypoint.getCoords().dst(currentWaypoint.getCoords()) > minDistance
					&& waypoint.getCoords().dst(lastWaypoint.getCoords()) > minDistance) {
				// If all conditions are met, choose this waypoint as the
				// nextWaypoint.
				nextWaypoint = waypoint;
				break;
			}
		}
		if (nextWaypoint == null) {
			nextWaypoint = lastWaypoint;
		}

		// add nextWaypoint to flightPlan.
		flightPlan.add(nextWaypoint);
		return nextWaypoint;
	}

	/**
	 * Selects random waypoint from entrypointList.
	 * 
	 * @return Waypoint
	 */
	private Waypoint setStartpoint() {
		return waypoints.getEntryList()
				.get(rand.nextInt(waypoints.getEntryList().size()));
	}

	/**
	 * Selects random exitpoint from exitpointList, that is at least minDistance
	 * away from the aircrafts entryWaypoint
	 * 
	 * @param entryWaypoint
	 *            - where this aircraft entered the game
	 * @param minDistance
	 *            - desired minimum distance between aircraft's entryWaypoint and
	 *            its exitWaypoint.
	 * @return Exitpoint
	 */
	private Exitpoint setEndpoint(Waypoint entryWaypoint, int minDistance) {
		Exitpoint chosenExitPoint = waypoints.getExitList().get(rand
				.nextInt(waypoints.getExitList().size()));
		if (chosenExitPoint.getCoords().dst(entryWaypoint.getCoords()) < minDistance) {
			chosenExitPoint = setEndpoint(entryWaypoint, minDistance);
		}

		return chosenExitPoint;
	}

}
