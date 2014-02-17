package seprini.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTime() {
		assertEquals(State.time(), 0f, 0);
	}

	@Test
	public void testReset() {
		State.reset();
		assertEquals(State.time(), 0f, 0);
	}
	
	@Test
	public void testChangeScore() {
		State.setDifficultyMultiplier(2.0f);
		State.changeScore(14.0f);
		assertEquals("Checks that the score is correct when played on hard.", 28, State.getScore());
		State.setDifficultyMultiplier(1.5f);
		State.changeScore(14.0f);
		assertEquals("Checks that the score is correct when played on medium. It should be the hard score plus 21.", 49, State.getScore());
		State.setDifficultyMultiplier(1.0f);
		State.changeScore(14.0f);
		assertEquals("Checks that the score is correct when played on easy. It should be both of the previous scores add 14.", 63, State.getScore());
		
	}
}
