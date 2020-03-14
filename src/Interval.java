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
        this.low = low;
        this.high = high;
    }

    /**
     * Get the low value of the interval
     * @return low value of the interval
     */
    public int getLow()
    {
        return low;
    }

    /**
     * Get the high value of the interval
     * @return high value of the interval
     */
    public int getHigh()
    {
        return high;
    }
}