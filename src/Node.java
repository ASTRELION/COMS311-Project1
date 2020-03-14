/**
 * A node of a Treap
 */
public class Node
{
    private Interval interval;
    private int priority;
    private int imax;

    /**
     * Create a new Node with given Interval
     * A priority will be generated on creation
     * @param i interval for the Node
     */
    public Node(Interval i)
    {
        this.interval = i;
        priority = 0;
        imax = 0;
    }

    /**
     * Get the parent of this Node
     * @return the parent of this Node
     */
    public Node getParent()
    {
        return null;
    }

    /**
     * Get the left child of this Node
     * @return the left child of this Node
     */
    public Node getLeft()
    {
        return null;
    }

    /**
     * Get the right child of this Node
     * @return the right child of this Node
     */
    public Node getRight()
    {
        return null;
    }

    /**
     * Get the Interval for this Node
     * @return the Interval for this Node
     */
    public Interval getInterv()
    {
        return interval;
    }

    /**
     * Get the imax field of this Node
     * @return the imax of this Node
     */
    public int getIMax()
    {
        return imax;
    }

    /**
     * Get the priority of this Node
     * @return the priority of this Node
     */
    public int getPriority()
    {
        return priority;
    }
}
