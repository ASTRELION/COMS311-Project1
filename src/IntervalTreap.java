import java.util.ArrayList;
import java.util.List;

/**
 * An IntervalTreap containing Interval Nodes
 */
public class IntervalTreap
{
    private Node root;
    private int size;

    /**
     * Create a new IntervalTreap
     */
    public IntervalTreap()
    {
        this.root = null;
        this.size = 0;
    }

    /**
     * Get the root Node of this Treap
     * @return the root Node of this Treap
     */
    public Node getRoot()
    {
        return this.root;
    }

    /**
     * Set the root Node of this Treap
     * @param x the root Node to set
     */
    public void setRoot(Node x)
    {
        this.root = x;
    }

    /**
     * Get the size of this Treap
     * @return the size of this Treap
     */
    public int getSize()
    {
        return this.size;
    }

    /**
     * Get the height of this Treap
     * @return the height of this Treap
     */
    public int getHeight()
    {
        return this.getRoot() != null ? this.getRoot().getHeight() : 0;
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
            x.getParent().updateIMax();
            x.getParent().updateHeight();
        }
        else
        {
            x.getParent().setRight(y);
            x.getParent().updateIMax();
            x.getParent().updateHeight();
        }

        if (y != null) y.setLeft(x);
        x.setParent(y);

        // Update iMax and height
        // Constant time
        x.updateIMax();
        x.updateHeight();
        if (y != null)
        {
            y.updateIMax();
            y.updateHeight();

            if (y.getParent() != null)
            {
                y.getParent().updateIMax();
                y.getParent().updateHeight();
            }
        }
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
            x.getParent().updateIMax();
            x.getParent().updateHeight();
        }
        else
        {
            x.getParent().setLeft(y);
            x.getParent().updateIMax();
            x.getParent().updateHeight();
        }

        if (y != null) y.setRight(x);
        x.setParent(y);

        // Update iMax and height
        // constant time
        x.updateIMax();
        x.updateHeight();
        if (y != null)
        {
            y.updateIMax();
            y.updateHeight();

            if (y.getParent() != null)
            {
                y.getParent().updateIMax();
                y.getParent().updateHeight();
            }
        }
    }

    /**
     * Add a new Node to this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the Node to add to this Treap
     */
    public void intervalInsert(Node z)
    {
        Node y = null;
        Node x = this.getRoot();

        // Find parent of z
        while (x != null)
        {
            y = x;
            x.setIMax(Math.max(x.getIMax(), z.getIMax()));

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
            this.setRoot(z);
        }
        else if (z.key() < y.key())
        {
            y.setLeft(z);
        }
        else
        {
            y.setRight(z);
        }

        if (y != null)
        {
            y.updateIMax();
            y.updateHeight();
        }

        // Rotate up the tree to satisfy priority
        while (z != this.getRoot() && z.getPriority() < z.getParent().getPriority())
        {
            if (z.getParent().key() == z.key())
            {
                // Constant time, so no run-time change
                this.swap(z, z.getParent());
            }
            else if (z.getParent().getLeft() == z) // if left child, right rotate
            {
                this.rightRotate(z.getParent());
            }
            else if (z.getParent().getRight() == z) // if right child, left rotate
            {
                this.leftRotate(z.getParent());
            }
        }

        // Update remaining heights, if needed
        // Bounded by height of the tree above
        // where z was inserted
        Node t = z;
        while (t != null)
        {
            t.updateIMax();
            t.updateHeight();
            t = t.getParent();
        }

        this.size++;
    }

    /**
     * Remove a Node from this Treap
     * Expected run time: O(log(n)) on an n-node Treap
     * @param z the node to remove from this Treap
     */
    public void intervalDelete(Node z)
    {
        if (z.getLeft() == null) // no left child
        {
            Node parent = z.getParent();
            Node temp = z.getRight();
            this.transplant(z, temp);
            z = null;
            this.size--;

            // Update heights/iMax up the tree
            // Bounded by height of tree worst case
            while (parent != null)
            {
                parent.updateIMax();
                parent.updateHeight();
                parent = parent.getParent();
            }
        }
        else if (z.getRight() == null) // no right child
        {
            Node parent = z.getParent();
            Node temp = z.getLeft();
            this.transplant(z, temp);
            z = null;
            this.size--;

            while (parent != null)
            {
                parent.updateIMax();
                parent.updateHeight();
                parent = parent.getParent();
            }
        }
        else // has 2 children
        {
            if (z.getLeft().getPriority() < z.getRight().getPriority())
            {
                this.rightRotate(z);
            }
            else
            {
                this.leftRotate(z);
            }

            this.intervalDelete(z);
        }
    }

    /**
     * Replace the Treap rooted at x with the Treap rooted
     * at y
     * @param x the root of the first Treap
     * @param y the root of the second Treap
     */
    public void transplant(Node x, Node y)
    {
        Node xParent = x.getParent();

        if (xParent == null)
        {
            this.setRoot(y);
        }
        else if (x == xParent.getLeft())
        {
            xParent.setLeft(y);
        }
        else
        {
            xParent.setRight(y);
        }

        if (y != null)
        {
            y.setParent(xParent);
            y.updateIMax();
            y.updateHeight();
        }

        if (xParent != null)
        {
            xParent.updateIMax();
            xParent.updateHeight();
        }
    }

    /**
     * Swap Node x with Node y, changing parent/child relationships
     * accordingly. ONLY for nodes that are parent/child to each
     * other
     * @param x the child Node
     * @param y the parent Node
     */
    private void swap(Node x, Node y)
    {
        if (y != null)
        {
            // If swapping with root, set new root
            if (this.getRoot() == y)
            {
                this.setRoot(x);
            }

            // Update parents
            if (y.getParent() != null)
            {
                if (y == y.getParent().getLeft())
                {
                    y.getParent().setLeft(x);
                }
                else
                {
                    y.getParent().setRight(x);
                }
            }

            x.setParent(y.getParent());
            y.setParent(x);

            // Update children
            if (y.getLeft() == x) // x is left child of y
            {
                Node yRightTemp = y.getRight();
                y.setRight(x.getRight());
                y.setLeft(x.getLeft());
                x.setLeft(y);
                x.setRight(yRightTemp);
            }
            else // x is right child of y
            {
                Node yLeftTemp = y.getLeft();
                y.setRight(x.getRight());
                y.setLeft(x.getLeft());
                x.setLeft(yLeftTemp);
                x.setRight(y);
            }

            y.updateHeight();
            y.updateIMax();
            x.updateHeight();
            x.updateIMax();
        }
        else
        {
            this.setRoot(x);
            x.updateIMax();
            x.updateHeight();
        }
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
     * Find the successor Node of the given Node in the Treap
     * @param x the Node to find the successor for
     * @return the successor of x, otherwise null
     */
    public Node successor(Node x)
    {
        if (x.getRight() != null)
        {
            return minimum(x.getRight());
        }

        Node y = x.getParent();

        while (y != null && x == y.getRight())
        {
            x = y;
            y = y.getParent();
        }

        return y;
    }

    /**
     * Get the minimum element in the Treap
     * @return the minimum Node
     */
    public Node minimum()
    {
        return this.minimum(this.getRoot());
    }

    /**
     * Get the minimum element in the Treap by key starting
     * at Node x
     * @param x the Node to start searching from
     * @return the minimum Node
     */
    public Node minimum(Node x)
    {
        while (x.getLeft() != null)
        {
            x = x.getLeft();
        }

        return x;
    }

    /**
     * Get the maximum element in the Treap
     * @return the maximum Node
     */
    public Node maximum()
    {
        return this.maximum(this.getRoot());
    }

    /**
     * Get the maximum element in the Treap by key starting
     * at Node x
     * @param x the Node to start searching from
     * @return the maximum Node
     */
    public Node maximum(Node x)
    {
        while (x.getRight() != null)
        {
            x = x.getRight();
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
            if (x.key() > i.getLow())
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
        
        return intervals.size() > 0 ? intervals : null;
    }

    /**
     * Perform a default in-order traversal of this Treap starting from the
     * root Node
     * @return list of nodes in-order
     */
    public List<Node> inOrder()
    {
        List<Node> inOrderNodes = new ArrayList<>();
        return inOrder(this.root, inOrderNodes);
    }

    /**
     * Perform an in-order traversal of the Treap rooted at the given Node
     * @param x the Node to start the traversal from
     * @param inOrderNodes the list of Nodes
     * @return list of nodes in-order
     */
    public List<Node> inOrder(Node x, List<Node> inOrderNodes)
    {
        if (x.getLeft() != null) inOrder(x.getLeft(), inOrderNodes);
        inOrderNodes.add(x);
        if (x.getRight() != null) inOrder(x.getRight(), inOrderNodes);
        return inOrderNodes;
    }

    /**
     * Generate the String representation of this Treap
     * @param x the Node to start from
     * @param h the height of the starting Node
     * @return the String representing this Treap
     */
    public static String getTreeString(Node x, int h)
    {
        String s = "";

        if (x != null)
        {
            for (int i = 0; i < h; i++)
            {
                s += ".";
            }

            if (h > 0) s += " ";
            s += String.format("%s|%d|%d|%d\n", x.getInterv(), x.getIMax(), x.getHeight(), x.getPriority());
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
     * @param treap the IntervalTreap to compare with
     * @return true if the Treaps are equal, false otherwise
     */
    public boolean equals(IntervalTreap treap)
    {
        return equalsRecursive(this.getRoot(), treap.getRoot());
    }
}
