package seprini.models;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class ExitpointTest {

	private Exitpoint testExitpoint1;

	@Before
	public void setUp() throws Exception {
		Exitpoint testexitpoint = new Exitpoint(new Vector2(3f, 3f));
		testexitpoint.radius = 5;
		testExitpoint1 = testexitpoint;
	}

	@Test
	public void testExitpoint() {
		Vector2 testcoordinates = new Vector2(3f, 3f);
		Vector2 result = testExitpoint1.coords;
		assertEquals(testcoordinates.x, result.x, 0);
		assertEquals(testcoordinates.y, result.y, 0);
	}

}
