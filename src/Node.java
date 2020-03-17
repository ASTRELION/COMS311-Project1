import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A node of an IntervalTreap
 */
public class Node
{
    private static List<Integer> invalidPriorities = new ArrayList<>();
    private static final int PRIORITY_MAX = Integer.MAX_VALUE;

    private Node parent;
    private Node leftChild;
    private Node rightChild;

    private Interval interval;
    private int priority;
    private int iMax;

    /**
     * Create a new Node with given Interval
     * A priority will be generated on creation
     * @param i interval for the Node
     */
    public Node(Interval i)
    {
        this.interval = i;
        this.iMax = i.getHigh();
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.generatePriority();
    }

    /**
     * Get the key value of this Node
     * @return the key of this Node (Interval.getLow())
     */
    public int key()
    {
        return this.interval.getLow();
    }

    /**
     * Get the parent of this Node
     * @return the parent of this Node
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * Set the parent Node of this Node
     * @param parent the parent Node to set
     */
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    /**
     * Check if this node is a root of a tree (has no parent)
     * @return true if this Node is a root, false otherwise
     */
    public boolean isRoot()
    {
        return !this.hasParent();
    }

    /**
     * Check if this Node has a parent node
     * @return true if this Node has a parent, false otherwise
     */
    public boolean hasParent()
    {
        return this.parent != null;
    }

    /**
     * Get the left child of this Node
     * @return the left child of this Node
     */
    public Node getLeft()
    {
        return this.leftChild;
    }

    /**
     * Set the left child of this Node
     * @param left the left child to set
     */
    public void setLeft(Node left)
    {
        this.leftChild = left;
        if (left != null) left.setParent(this);
    }

    /**
     * Get the right child of this Node
     * @return the right child of this Node
     */
    public Node getRight()
    {
        return this.rightChild;
    }

    /**
     * Set the right child of this Node
     * @param right the right child to set
     */
    public void setRight(Node right)
    {
        this.rightChild = right;
        if (right != null) right.setParent(this);
    }

    /**
     * Check if this Node is a leaf (has no children)
     * @return true if this Node is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return !this.hasChild();
    }

    /**
     * Check if this Node has at least one child
     * @return true if this Node has a child, false otherwise
     */
    public boolean hasChild()
    {
        return this.rightChild != null || this.leftChild != null;
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
     * Set the Interval of this Node with given low/high values
     * @param low the low value of the Interval
     * @param high the high value of the Interval
     */
    public void setInterv(int low, int high)
    {
        setInterv(new Interval(low, high));
    }

    /**
     * Set the Interval of this Node with given Interval
     * @param i the Interval to set
     */
    public void setInterv(Interval i)
    {
        this.interval = i;
    }

    /**
     * Get the iMax field of this Node
     * @return the iMax of this Node
     */
    public int getIMax()
    {
        return iMax;
    }

    /**
     * Set the iMax of this Node
     * @param iMax the iMax value to set
     */
    public void setIMax(int iMax)
    {
        this.iMax = iMax;
    }

    /**
     * Get the priority of this Node
     * @return the priority of this Node
     */
    public int getPriority()
    {
        return priority;
    }

    /**
     * Set the priority of this Node
     * @param priority the priority to set
     */
    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    /**
     * Generates a random value and assigns it to this
     * Node's priority field
     */
    public void generatePriority()
    {
        Random random = new Random();

        // Prevent duplicate priorities
        int p = random.nextInt(PRIORITY_MAX);
        while (invalidPriorities.contains(p))
        {
            p = random.nextInt(PRIORITY_MAX);
        }

        this.setPriority(p);
        invalidPriorities.add(p);
    }

    public String toString()
    {
        return String.valueOf(this.key());
    }
}
