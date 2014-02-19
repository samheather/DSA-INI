package seprini.models;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.math.Vector2;

public class Airport extends Waypoint{ 
	
	private Stack<Aircraft> aircraft = new Stack<Aircraft>();
	private final int airportCapacity = 3;

	@Override
	public boolean handleCollision(Aircraft a) {
		if(a.popWaypoint())
			return false;
		a.restoreWaypoints();
		if (canLand())
		{
			a.deselect();
			// Remove plane image for this plane from list of sprites
			a.remove();
			// Remove plane from list of planes so Physics no longer applied to it.
			a.removeFromAircraftListToAvoidFramerateProblems();
			aircraft.push(a);
			return true;
		}
		return false;
	}


	public Airport() {
		super(new Vector2(400, 345), false);
		// TODO Auto-generated constructor stub
	}
	
	public int getNumberInAirport() {
		return aircraft.size();
	}
	
	public boolean canLand() {
		return getNumberInAirport() < airportCapacity;
	}

	public boolean canLaunch() {
		return getNumberInAirport() > 0;
	}
	public void land(Aircraft newAircraft) {
		ArrayList<Waypoint> newFlightPlan = new ArrayList<Waypoint>();
		newFlightPlan.add(this);
		newAircraft.waypoints(newFlightPlan);
	}
	
	public Aircraft launch() {
		if (!canLaunch())
			return null;
		return aircraft.pop();
	}

	

	
	

}
