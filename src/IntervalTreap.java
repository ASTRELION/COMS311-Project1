import java.util.ArrayList;
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
        this.root = null;
        this.size = 0;
        this.height = 0;
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
     * Perform a left-rotation centered at the given Node
     * @param x the Node to rotate around
     */
    public void leftRotate(Node x)
    {
        Node y = x.getRight();
        x.setRight(y == null ? null : y.getLeft());

        if (y != null && y.getLeft() != null)
        {
            y.getLeft().setParent(x);
        }

        if (y != null) y.setParent(x.getParent());

        if (x.getParent() == null)
        {
            this.root = y;
        }
        else if (x == x.getParent().getLeft())
        {
            x.getParent().setLeft(y);
        }
        else
        {
            x.getParent().setRight(y);
        }

        if (y != null) y.setLeft(x);
        x.setParent(y);
    }

    /**
     * Perform a right-rotation centered at the given Node
     * @param x the Node to rotate around
     */
    public void rightRotate(Node x)
    {
        Node y = x.getLeft();
        x.setLeft(y == null ? null : y.getRight());

        if (y != null && y.getRight() != null)
        {
            y.getRight().setParent(x);
        }

        if (y != null) y.setParent(x.getParent());

        if (x.getParent() == null)
        {
            this.root = y;
        }
        else if (x == x.getParent().getRight())
        {
            x.getParent().setRight(y);
        }
        else
        {
            x.getParent().setLeft(y);
        }

        if (y != null) y.setRight(x);
        x.setParent(y);
    }

    /**
     * Add a new Node to this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the Node to add to this Treap
     */
    public void intervalInsert(Node z)
    {
        int h = 0;
        Node y = null;
        Node x = this.getRoot();

        // Find parent of z
        while (x != null)
        {
            y = x;
            h++;
            x.setIMax(Math.max(x.getIMax(), z.getInterv().getHigh()));
            if (z.key() < x.key())
            {
                x = x.getLeft();
            }
            else
            {
                x = x.getRight();
            }
        }

        // Set parent of z
        z.setParent(y);

        // Insert
        if (y == null)
        {
            this.root = z;
        }
        else if (z.key() < y.key())
        {
            y.setLeft(z);
        }
        else
        {
            y.setRight(z);
        }

        // Rotate up the tree to satisfy priority
        while (z != this.getRoot() && z.getPriority() < z.getParent().getPriority())
        {
            if (z.getParent().getLeft() == z)
            {
                this.rightRotate(z.getParent());
            }
            else if (z.getParent().getRight() == z)
            {
                this.leftRotate(z.getParent());
            }
        }

        this.height = Math.max(h, this.height);
        this.size++;

        // TODO Implement rotations
    }

    /**
     * Remove a Node from this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the node to remove from this Treap
     */
    public void intervalDelete(Node z)
    {
        // TODO implement delete
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
        Node x = this.getRoot();

        while (x != null)
        {
            if (x.getInterv().equals(i))
            {
                return x;
            }
            else if (i.getLow() < x.key())
            {
                x = x.getLeft();
            }
            else
            {
                x = x.getRight();
            }
        }

        return null;
    }

    /**
     * EXTRA CREDIT (OPTIONAL), 10 points
     * Get the List of Nodes that overlap the given Interval
     * Expected run time: O(min(n, klog(n))), where k is the no. of
     * Intervals in the output list, on an n-node Treap
     * @param i the Interval to search for overlaps with
     * @return a List of Intervals that overlap i
     */
    public List<Interval> overlappingIntervals(Interval i)
    {
        List<Interval> intervals = new ArrayList<>();
        Node x = this.getRoot();

        while (x != null)
        {
            if (x.getInterv().overlaps(i))
            {
                intervals.add(x.getInterv());
            }

            if (x.getLeft() != null && x.getLeft().getIMax() >= i.getLow())
            {
                x = x.getLeft();
            }
            else
            {
                x = x.getRight();
            }
        }
        
        return intervals;
    }

    /**
     * Perform a default in-order traversal of this Treap starting from the
     * root Node
     */
    public void inOrder()
    {
        inOrder(this.root);
    }

    /**
     * Perform an in-order traversal of the Treap rooted at the given Node
     * @param x the Node to start the traversal from
     */
    public void inOrder(Node x)
    {
        if (x.getLeft() != null) inOrder(x.getLeft());
        System.out.println(x);
        if (x.getRight() != null) inOrder(x.getRight());
    }

    /**
     * Generate the String representation of this Treap
     * @param x the Node to start from
     * @param h the height of the starting Node
     * @return the String representing this Treap
     */
    private String getTreeString(Node x, int h)
    {
        String s = "";

        if (x != null)
        {
            for (int i = 0; i < h; i++)
            {
                s += ".";
            }

            if (h > 0) s += " ";
            s += String.format("%s|%d|%d\n", x, x.getIMax(), x.getPriority());
            s += getTreeString(x.getLeft(), h + 1) ;
            s += getTreeString(x.getRight(), h + 1);
        }

        return s;
    }

    public String toString()
    {
        return String.format(
            "TREAP - %d nodes - %d height\n" +
            "%s",
            this.getSize(),
            this.getHeight(),
            getTreeString(this.root, 0)
        );
    }

    private boolean equalsRecursive(Node x, Node y)
    {
        if (x != null)
        {
            if (x.equals(y))
            {
                if (x.getLeft() != null)
                {
                    return equalsRecursive(x.getLeft(), y.getLeft());
                }

                if (x.getRight() != null)
                {
                    return equalsRecursive(x.getRight(), y.getRight());
                }
            }
        }
        else if (y == null)
        {
            return true;
        }

        return false;
    }

    /**
     * Check if this Treap is equal to another Treap.
     * Two Treaps are "equal" if all nodes appear in the
     * same order and have the same Interval
     * @return true if the Treaps are equal, false otherwise
     */
    public boolean equals(IntervalTreap treap)
    {
        return equalsRecursive(this.getRoot(), treap.getRoot());
    }
}
