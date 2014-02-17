package seprini.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class WaypointTest {

	private Waypoint waypoint;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Waypoint testwaypoint = new Waypoint(3f, 3f, true);
		waypoint = testwaypoint;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWaypointVector2Boolean() {
		// This is just intialization of the waypoint which works as done in the
		// setup
	}

	@Test
	public void testWaypointFloatFloatBoolean() {
		assertTrue(waypoint.isDeletable() == true);
		Vector2 testCoordinates = new Vector2(3f, 3f);
		Vector2 result = waypoint.coords;
		assertEquals(testCoordinates.x, result.x, 0);
		assertEquals(testCoordinates.y, result.y, 0);

	}

	@Test
	public void testIsDeletable() {
		assertTrue(waypoint.isDeletable());
	}

	@Test
	public void testToString() {
		assertTrue(("Waypoint - x: 3.0 y: 3.0").equals(waypoint.toString()));
	}

	@Test
	public void testCpy() {
		assertTrue(waypoint.cpy().toString().equals(waypoint.toString()));
		assertTrue(waypoint.cpy().isDeletable() == waypoint.isDeletable());

	}
}