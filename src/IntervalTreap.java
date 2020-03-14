import java.util.List;

/**
 * An IntervalTreap containing Interval Nodes
 */
public class IntervalTreap
{
    private Node root;
    private int size;
    private int height;

    /**
     * Create a new IntervalTreap
     */
    public IntervalTreap()
    {

    }

    /**
     * Get the root Node of this Treap
     * @return the root Node of this Treap
     */
    public Node getRoot()
    {
        return root;
    }

    /**
     * Get the size of this Treap
     * @return the size of this Treap
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Get the height of this Treap
     * @return the height of this Treap
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Add a new Node to this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the Node to add to this Treap
     */
    public void intervalInsert(Node z)
    {

    }

    /**
     * Remove a Node from this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the node to remove from this Treap
     */
    public void intervalDelete(Node z)
    {

    }

    /**
     * Search for a Node that overlaps that Interval in this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param i the interval to search in
     * @return the Node in the Treap if found, otherwise null
     */
    public Node intervalSearch(Interval i)
    {
        // Begin search at root Node
        Node x = this.getRoot();

        while (x != null && !i.overlaps(x.getInterv()))
        {
            if (x.getLeft() != null && x.getLeft().getIMax() >= i.getLow())
            {
                x = x.getLeft();
            }
            else
            {
                x = x.getRight();
            }
        }

        return x;
    }

    /**
     * EXTRA CREDIT (OPTIONAL), 5 points
     * Get a Node in the Treap with exactly the given Interval
     * Expected run time: O(log(n)) on an n-node Treap
     * @param i the Interval to search for
     * @return the Node with Interval i if found, otherwise null
     */
    public Node intervalSearchExactly(Interval i)
    {
        return null;
    }

    /**
     * EXTRA CREDIT (OPTIONAL), 10 points
     * Get the List of Nodes that overlap the given Interval
     * Expected run time: O(min(n, klog(n))), where k is the no. of
     * Intervals in the output list, on an n-node Treap
     * @param i the Interval to search for overlaps with
     * @return a List of Intervals that overlap i if found, otherwise null
     */
    public List<Interval> overlappingINtervals(Interval i)
    {
        return null;
    }
}
