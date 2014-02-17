package seprini.models;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EntityTest {
	private Entity entity;
	protected Vector2 coords;
	protected TextureRegion texture;
	protected Vector2 size;
	protected boolean debugshape;

	@Before
	public void setUp() throws Exception {
		Entity entitytest = new Waypoint(0, 1, false);
		entitytest.texture = new TextureRegion();
		entitytest.size = new Vector2(1, 2);
		entitytest.debugShape = false;
		entity = entitytest;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetX() {
		assertEquals(0, entity.getX(), 0);
	}

	@Test
	public void testGetY() {
		assertEquals(1, entity.getY(), 0);
	}

	@Test
	public void testGetWidth() {
		assertEquals(1, entity.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		assertEquals(2, entity.getHeight(), 0);
	}

	@Test
	public void testDrawSpriteBatchFloat() {
		// FUNCTIONAL
	}

	@Test
	public void testAdditionalDraw() {
	}

	@Test
	public void testGetRegion() {
		// FUNCTIONAL
	}

	@Test
	public void testGetCoords() {
		Vector2 testcoords = new Vector2();
		testcoords.x = 0;
		testcoords.y = 1;
		assertEquals(testcoords.x, entity.getCoords().x, 0);
		assertEquals(testcoords.y, entity.getCoords().y, 0);
	}

}