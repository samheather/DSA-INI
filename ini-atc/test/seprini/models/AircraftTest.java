package seprini.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import seprini.data.Art;
import seprini.models.types.AircraftType;

import com.badlogic.gdx.math.Vector2;

public class AircraftTest {

	private Aircraft testAircraft1;
	private Aircraft testAircraft2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdditionalDraw() {
		// fail("Not yet implemented");
	}

	@Test
	public void testAircraft() {
		// fail("Not yet implemented");
	}

	@Test
	public void testAct() {
		// fail("Not yet implemented");

	}

	@Test
	public void testInsertWaypoint() {
		Waypoint newWaypoint = new Waypoint(7, 8, true);
		testAircraft1.insertWaypoint(newWaypoint);
		assertEquals(
				"Checks that the newly added waypoint is the next waypoint that the plane will travel to",
				testAircraft1.waypoints().get(0), newWaypoint);

	}

	@Test
	public void testIncreaseSpeed() {
		testAircraft2.selected(true);
		testAircraft2.increaseSpeed();
		assertEquals(
				"Checks that the speed of the currently selected plane is equal to the calculated value",
				1.1f, testAircraft2.getSpeed(), 0);

	}

	@Test
	public void testDecreaseSpeed() {
		testAircraft1.selected(true);
		testAircraft1.decreaseSpeed();
		assertEquals(
				"Checks that the speed of the currently selected plane is equal to the calculated value",
				0.9f, testAircraft1.getSpeed(), 0);
	}

	@Test
	public void testIncreaseAltitude() {
	}

	@Test
	public void testDecreaseAltitude() {
	}

	@Test
	public void testTurnRight() {
	}

	@Test
	public void testTurnLeft() {
	}

	@Test
	public void testGetRadius() {
		assertEquals(15f, testAircraft1.getRadius(), 0);
	}

	@Test
	public void testGetSeparationRadius() {
		float result = testAircraft1.getSeparationRadius();
		assertEquals(result, 100, 0);
	}

	@Test
	public void testIsBreaching() {
	}

	@Test
	public void testGetAltitude() {
		float result = testAircraft1.getAltitude();
		if (result == 5000) {
			assertEquals(5000, result, 0);
		} else if (result == 10000) {
			assertEquals(10000, result, 0);
		} else if (result == 15000) {
			assertEquals(15000, result, 0);
		}
	}

	@Test
	public void testGetSpeed() {
		float result = testAircraft1.getSpeed();
		assertEquals(1f, result, 0);
	}

	@Test
	public void testIsActive() {
		assertTrue(testAircraft1.isActive());
	}

	@Test
	public void testSelected() {
	}

	@Test
	public void testToString() {
		assertTrue(("Aircraft - x: 1.0 y: 1.0\n\r flight plan: [Waypoint - x: 2.0 y: 2.0]")
				.equals(testAircraft1.toString()));
	}
}
