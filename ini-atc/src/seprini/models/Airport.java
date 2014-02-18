package seprini.models;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Airport extends Waypoint{ 
	
	private int numberInAirport = 2;
	private final int airportCapacity = 3;

	@Override
	public void handleCollision(Aircraft a) {
		if(a.waypoints.size() > 1)
			return;
		if (!canLand()) {
			a.restoreWaypoints();
		}
		else
		{
			++numberInAirport;
			// Remove plane image for this plane from list of sprites
			a.remove();
			// Remove plane from list of planes so Physics no longer applied to it.
			a.removeFromAircraftListToAvoidFramerateProblems();
		}
	}


	public Airport() {
		super(new Vector2(400, 345), false);
		// TODO Auto-generated constructor stub
	}
	
	public int getNumberInAirport() {
		return numberInAirport;
	}
	
	public boolean canLand() {
		return numberInAirport < airportCapacity;
	}

	public boolean canLaunch() {
		return numberInAirport > 0;
	}
	public void land(Aircraft newAircraft) {
		newAircraft.saveWaypoints();
		ArrayList<Waypoint> newFlightPlan = new ArrayList<Waypoint>();
		newFlightPlan.add(this);
		newAircraft.waypoints = newFlightPlan;
	}
	
	public boolean launch() {
		if (!canLaunch())
			return false;
		--numberInAirport;
		return true;
	}

	

	
	

}
