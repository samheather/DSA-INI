package seprini.models.types;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import seprini.data.Art;
import seprini.models.types.AircraftType;

public class AircraftTypeTest {

	private AircraftType aircraftType;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		AircraftType testAircraftType = new AircraftType();

		testAircraftType.setCoords(new Vector2(0, 0)).setActive(true)
				.setMaxClimbRate(0).setMaxSpeed(0.8f).setMaxTurningSpeed(0.4f)
				.setRadius(15).setSeparationRadius(100)
				.setTexture(Art.getTextureRegion("aircraft"))
				.setVelocity(new Vector2(0.8f, 0.8f))
				.setAircraftName("Test Aircraft");

		aircraftType = testAircraftType;

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCoords() {
		Vector2 testCoords = new Vector2(0, 0);
		Vector2 result = aircraftType.getCoords();
		assertEquals(testCoords.x, result.x, 0);
		assertEquals(testCoords.y, result.y, 0);

	}

	@Test
	public void testSetCoords() {
		Vector2 testCoords = new Vector2(2, 3);
		aircraftType.setCoords(testCoords);
		Vector2 result = aircraftType.getCoords();
		assertEquals(testCoords.x, result.x, 0);
		assertEquals(testCoords.y, result.y, 0);
	}

	@Test
	public void testGetVelocity() {
		Vector2 testVelocity = new Vector2(0.8f, 0.8f);
		Vector2 result = aircraftType.getVelocity();
		assertEquals(testVelocity.x, result.x, 0);
		assertEquals(testVelocity.y, result.y, 0);

	}

	@Test
	public void testSetVelocity() {
		Vector2 testVelocity = new Vector2(7f, 8f);
		aircraftType.setVelocity(testVelocity);
		Vector2 result = aircraftType.getVelocity();
		assertEquals(testVelocity.x, result.x, 0);
		assertEquals(testVelocity.y, result.y, 0);
	}

	@Test
	public void testGetRadius() {
		assertEquals(aircraftType.getRadius(), 15, 0);
	}

	@Test
	public void testSetRadius() {
		aircraftType.setRadius(50);
		assertEquals(aircraftType.getRadius(), 50, 0);
	}

	@Test
	public void testGetSeparationRadius() {
		assertEquals(aircraftType.getSeparationRadius(), 100, 0);
	}

	@Test
	public void testSetSeparationRadius() {
		aircraftType.setSeparationRadius(200);
		assertEquals(aircraftType.getSeparationRadius(), 200, 0);
	}

	@Test
	public void testGetTexture() {
		// fail("Not yet implemented");
	}

	@Test
	public void testSetTexture() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetMaxTurningSpeed() {
		assertEquals(aircraftType.getMaxTurningSpeed(), 0.4f, 0);
	}

	@Test
	public void testSetMaxTurningSpeed() {
		aircraftType.setMaxTurningSpeed(1.5f);
		assertEquals(aircraftType.getMaxTurningSpeed(), 1.5f, 0);
	}

	@Test
	public void testGetMaxClimbRate() {
		assertEquals(aircraftType.getMaxClimbRate(), 0f, 0);
	}

	@Test
	public void testSetMaxClimbRate() {
		aircraftType.setMaxClimbRate(20f);
		assertEquals(aircraftType.getMaxClimbRate(), 20f, 0);
	}

	@Test
	public void testGetMaxSpeed() {
		assertEquals(aircraftType.getMaxSpeed(), 0.8f, 0);
	}

	@Test
	public void testSetMaxSpeed() {
		aircraftType.setMaxSpeed(1.8f);
		assertEquals(aircraftType.getMaxSpeed(), 1.8f, 0);
	}

	@Test
	public void testIsActive() {
		assertTrue(aircraftType.isActive());
	}

	@Test
	public void testSetActive() {
		aircraftType.setActive(false);
		assertFalse(aircraftType.isActive());
	}

	@Test
	public void testGetAircraftName() {
		assertTrue(aircraftType.getAircraftName().equals("Test Aircraft"));
	}

	@Test
	public void testSetAircraftName() {
		aircraftType.setAircraftName("Aircraft Name");
		assertTrue(aircraftType.getAircraftName().equals("Aircraft Name"));
	}

}
