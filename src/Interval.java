/**
 * Represents a binary interval
 */
public class Interval
{
    private int low;
    private int high;

    /**
     * Create a new Interval with given high and low values
     * @param low Low value of the interval
     * @param high High value of the interval
     */
    public Interval(int low, int high)
    {
        if (low > high)
        {
            throw new Error("Low interval value must be less than or equal to high value");
        }

        this.low = low;
        this.high = high;
    }

    /**
     * Get the low value of the interval
     * @return low value of the interval
     */
    public int getLow()
    {
        return this.low;
    }

    /**
     * Get the high value of the interval
     * @return high value of the interval
     */
    public int getHigh()
    {
        return this.high;
    }

    /**
     * Check if a given Interval overlaps this Interval
     * @param i the Interval to compare with
     * @return true if the Intervals overlap, false otherwise
     */
    public boolean overlaps(Interval i)
    {
        return this.getLow() <= i.getHigh() && this.getHigh() >= i.getLow();
    }

    /**
     * Check if a given Interval is equal to this Interval
     * @param i the interval to compare with
     * @return true if the Intervals are equal, false otherwise
     */
    public boolean equals(Interval i)
    {
        return this.getLow() == i.getLow() && this.getHigh() == i.getHigh();
    }

    public String toString()
    {
        return String.format("[%d, %d]", this.getLow(), this.getHigh());
    }
}
