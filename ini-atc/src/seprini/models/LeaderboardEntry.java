package seprini.models;

/**
 * LeaderboardEntry - represents a single entry in the leaderboard, with
 * 
 * @param String
 *            name, and double score.
 */

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

	/**
	 * Name of person in leaderboard
	 */
	private String name;
	/**
	 * Score of this person.
	 */
	private double score;

	/**
	 * Empty constructor.
	 */
	public LeaderboardEntry() {
		// Do nothing.
	}

	/**
	 * LeaderboardEntry constructor
	 * 
	 * @param String
	 *            name, double score
	 */
	public LeaderboardEntry(String name, double score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * Accessor for score
	 * 
	 * @param double score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Accessor for Name
	 * 
	 * @param String
	 *            name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for double score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * Setter for String name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(LeaderboardEntry other) {
		if (this.score > other.score) {
			return -1;
		}
		if (this.score < other.score) {
			return 1;
		}
		return 0;
	}
}
