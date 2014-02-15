package seprini.models;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Airport {
	
	private Vector2 pos = new Vector2(400, 345);
	
	public Airport() {
		
	}
	
	public boolean landPlane(Aircraft a) {
		if (!canLandPlane()) {
			return false;
		}
		if(stored.contains(a))
			return false;
		stored.add(a);
		ArrayList<Waypoint> temp = new ArrayList<Waypoint>();
		temp.add(new Exitpoint(pos, true));
		a.waypoints = temp;
		

		return true;
	}
	
	public boolean canLandPlane() {
		return this.stored.size() < 3;
	}
	
	public boolean canLaunchPlane() {
		return this.landed.size() > 0;
	}
	
	public int getPlaneCount() {
		return this.stored.size();
	}
	

	
	private float radius = 50;

	public float getRadius() {
		return radius;
		// TODO Auto-generated method stub
	}

	public Vector2 getCoords() {
		// TODO Auto-generated method stub
		return pos;
	}
	
	private ArrayList<Aircraft> stored = new ArrayList<Aircraft>();
	private ArrayList<Aircraft> landed = new ArrayList<Aircraft>();

	public void planeCollision(Aircraft planeI) {
		if (stored.contains(planeI)) {
			planeI.remove();
			landed.add(planeI);
		}
	}

	public Aircraft launchPlane() {
		if(!canLaunchPlane())
			return null;
		Aircraft rem = landed.remove(0);
		stored.remove(rem);
		return rem;
	}

}