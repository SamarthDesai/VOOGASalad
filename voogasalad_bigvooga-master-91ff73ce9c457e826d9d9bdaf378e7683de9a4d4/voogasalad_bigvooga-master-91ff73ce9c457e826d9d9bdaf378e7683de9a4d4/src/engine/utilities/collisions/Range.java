package engine.utilities.collisions;

/**
 * A min-max range of doubles, used primarily as a helper for collisions.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class Range {

	private double min;
	private double max;

	public Range(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getDifference() {
		return max - min;
	}

	public boolean contains(double value) {
		return value >= min && value <= max;
	}

	/**
	 * Checks the overlap with another range.
	 * 
	 * @param other
	 *            The other Range to check against
	 * @return The <it>signed</it> amount this range would need to be translated to
	 *         separate them.
	 */
	public double getOverlap(Range other) {
		if (!intersects(other))
			return 0;
		if (Math.abs(max - other.getMin()) < Math.abs(min - other.getMax()))
			return other.getMin() - max;
		else
			return other.getMax() - min;
	}

	/**
	 * @return Whether this range contains any point in other.
	 */
	public boolean intersects(Range other) {
		return contains(other.getMin()) || other.contains(min);
	}

	public String toString() {
		return "[" + min + ", " + max + "]";
	}
}
