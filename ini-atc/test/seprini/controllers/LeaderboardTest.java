package seprini.controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import seprini.models.LeaderboardEntry;

public class LeaderboardTest {

	@Test
	public void testAddLeaderBoardEntries() {
		LeaderboardEntry[] testLeaderboardEntries1 = new LeaderboardEntry[5];
		Leaderboard testLB = new Leaderboard();
		
		for (int i = 0; i < testLeaderboardEntries1.length; i++) {
			testLeaderboardEntries1[i] = new LeaderboardEntry();
		}	
			
			testLeaderboardEntries1[0].setName("d");
			testLeaderboardEntries1[0].setScore(300);
			testLeaderboardEntries1[1].setName("d");
			testLeaderboardEntries1[1].setScore(300);
			testLeaderboardEntries1[2].setName("d");
			testLeaderboardEntries1[2].setScore(300);
			testLeaderboardEntries1[3].setName("d");
			testLeaderboardEntries1[3].setScore(300);
			testLeaderboardEntries1[4].setName("d");
			testLeaderboardEntries1[4].setScore(300);
			
			testLB.addLeaderBoardEntries();
			
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[0].getName(), Leaderboard.leaderboardEntries[0].getName());
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[0].getScore(), Leaderboard.leaderboardEntries[0].getScore(), 0.001);
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[1].getName(), Leaderboard.leaderboardEntries[1].getName());
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[1].getScore(), Leaderboard.leaderboardEntries[1].getScore(), 0.001);
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[2].getName(), Leaderboard.leaderboardEntries[2].getName());
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[2].getScore(), Leaderboard.leaderboardEntries[2].getScore(), 0.001);
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[3].getName(), Leaderboard.leaderboardEntries[3].getName());
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[3].getScore(), Leaderboard.leaderboardEntries[3].getScore(), 0.001);
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[4].getName(), Leaderboard.leaderboardEntries[4].getName());
			assertEquals("Tests that the array that is created matches the array that I created", testLeaderboardEntries1[4].getScore(), Leaderboard.leaderboardEntries[4].getScore(), 0.001);		
	}

}
