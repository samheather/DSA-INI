package seprini.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import seprini.data.Art;
import seprini.models.types.AircraftType;

import com.badlogic.gdx.math.Vector2;

public class AirportTest {

	private Aircraft testAircraft1;
	private Aircraft testAircraft2;
	private Aircraft testAircraft3;
	private Aircraft testAircraft4;
	private Stack<Aircraft> testAirport = new Stack<Aircraft>();
	private final int airportCapacity = 3;
	
	@Before
	public void setUp() {
		AircraftType defaultAircraft = new AircraftType();

		defaultAircraft.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(10).setMaxSpeed(1.5f).setMaxTurningSpeed(0.4f)
				.setRadius(15).setSeparationRadius(100)
				.setTexture(Art.getTextureRegion("aircraft"))
				.setVelocity(new Vector2(0.8f, 0.8f));

		ArrayList<Waypoint> testPlan1 = new ArrayList<Waypoint>();
		testPlan1.add(new Waypoint(1, 1, true));
		testPlan1.add(new Waypoint(2, 2, true));

		ArrayList<Waypoint> testPlan2 = new ArrayList<Waypoint>();
		testPlan2.add(new Waypoint(4, 4, true));
		testPlan2.add(new Waypoint(5, 5, true));

		testAircraft1 = new Aircraft(defaultAircraft, testPlan1, 0, null);
		testAircraft2 = new Aircraft(defaultAircraft, testPlan2, 0, null);
		testAircraft3 = new Aircraft(defaultAircraft, testPlan2, 0, null);
		testAircraft4 = new Aircraft(defaultAircraft, testPlan2, 0, null);
	}
	
	@Test
	public void testHandleCollision() {
		fail("Not yet implemented");
	}

	@Test
	public void testAirport() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberInAirport() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanLand() {
		assertFalse(testAirport.ca);
	}

	@Test
	public void testCanLaunch() {
		fail("Not yet implemented");
	}

	@Test
	public void testLand() {
		testAircraft1.l
	}

	@Test
	public void testLaunch() {
		fail("Not yet implemented");
	}

}
