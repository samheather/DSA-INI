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
		stored.add(a);
		ArrayList<Waypoint> temp = new ArrayList<Waypoint>();
		temp.add(new Exitpoint(pos, true));
		a.waypoints = temp;
		

		return true;
	}
	
	public boolean canLandPlane() {
		if (this.stored.size() < 3) {
			return true;
		}
		return false;
	}
	
	public boolean canLaunchPlane() {
		if (this.stored.size() > 0) {
			return true;
		}
		return false;
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

	public void planeCollision(Aircraft planeI) {
		if (stored.contains(planeI)) {
			planeI.remove();
		}
	}

}