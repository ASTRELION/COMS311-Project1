import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Tests would usually be in another package but
 * since the project uses the default Java package
 * as requested we must include tests here
 *
 * _ before each method name ensures it runs BEFORE the
 * deletion tests.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // force tests to run alphabetically
public class Tests
{
    // File paths to input files from Piazza
    private static String smallPath = "testInputs/small_1.txt";
    private static String mediumPath = "testInputs/medium_1.txt";
    private static String largePath = "testInputs/large_1.txt";

    // Small input structures
    private static IntervalTreap smallTreap = new IntervalTreap();
    private static List<Node> smallNodes = new ArrayList<>();
    private static List<Interval> smallPositives = new ArrayList<>();
    private static List<Interval> smallNegatives = new ArrayList<>();

    // Medium input structures
    private static IntervalTreap mediumTreap = new IntervalTreap();
    private static List<Node> mediumNodes = new ArrayList<>();
    private static List<Interval> mediumPositives = new ArrayList<>();
    private static List<Interval> mediumNegatives = new ArrayList<>();

    // Large input structures
    private static IntervalTreap largeTreap = new IntervalTreap();
    private static List<Node> largeNodes = new ArrayList<>();
    private static List<Interval> largePositives = new ArrayList<>();
    private static List<Interval> largeNegatives = new ArrayList<>();

    /**
     * Reads the positive test cases from file
     * @param input the Scanner to read from
     * @return a list of positive Intervals
     */
    private static List<Interval> readPositives(Scanner input)
    {
        List<Interval> intervals = new ArrayList<>();

        input.nextLine();
        while (input.hasNext())
        {
            String line = input.nextLine();

            if (line.equals("TN"))
            {
                return intervals;
            }
            else
            {
                String nums[] = line.split(" ");
                intervals.add(new Interval(
                    Integer.parseInt(nums[0]),
                    Integer.parseInt(nums[1])
                ));
            }
        }

        return intervals;
    }

    /**
     * Reads the negative test cases from file
     * @param input the Scanner to read from
     * @return a list of negative Intervals
     */
    private static List<Interval> readNegatives(Scanner input)
    {
        List<Interval> intervals = new ArrayList<>();

        while (input.hasNext())
        {
            String line = input.nextLine();

            if (line.equals("Intervals"))
            {
                return intervals;
            }
            else
            {
                String nums[] = line.split(" ");
                intervals.add(new Interval(
                        Integer.parseInt(nums[0]),
                        Integer.parseInt(nums[1])
                ));
            }
        }

        return intervals;
    }

    /**
     * Reads the nodes from file
     * @param input the Scanner to read from
     * @return a list of Nodes
     */
    private static List<Node> readNodes(Scanner input)
    {
        List<Node> nodes = new ArrayList<>();

        while (input.hasNext())
        {
            String line = input.nextLine();
            String nums[] = line.split(" ");
            Node n = new Node(new Interval(
                Integer.parseInt(nums[0]),
                Integer.parseInt(nums[1]
            )));
            nodes.add(n);
        }

        return nodes;
    }

    /**
     * Test Treap starting at Node x
     * Validates Treap order properties
     * @param x Node to start from
     */
    private void _testStructureRecursive(Node x)
    {
        if (x != null)
        {
            if (x.getLeft() != null)
            {
                assertTrue(x.getLeft().getPriority() > x.getPriority());
                assertTrue(x.getLeft().key() < x.key());
                assertTrue(x.getLeft().getIMax() <= x.getIMax());
                assertTrue(x.getLeft().getHeight() < x.getHeight());
            }

            if (x.getRight() != null)
            {
                assertTrue(x.getRight().getPriority() > x.getPriority());
                assertTrue(x.getRight().key() >= x.key());
                assertTrue(x.getRight().getIMax() <= x.getIMax());
                assertTrue(x.getRight().getHeight() < x.getHeight());
            }

            if (x.getLeft() != null && x.getRight() != null)
            {
                int iMax = x.getInterv().getHigh();
                iMax = Math.max(iMax, x.getLeft().getIMax());
                iMax = Math.max(iMax, x.getRight().getIMax());
                assertEquals(iMax, x.getIMax());
                assertEquals(Math.max(x.getLeft().getHeight() + 1, x.getRight().getHeight() + 1), x.getHeight());
            }
            else if (x.getLeft() != null)
            {
                assertEquals(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()), x.getIMax());
                assertEquals(x.getLeft().getHeight() + 1, x.getHeight());
            }
            else if (x.getRight() != null)
            {
                assertEquals(Math.max(x.getInterv().getHigh(), x.getRight().getIMax()), x.getIMax());
                assertEquals(x.getRight().getHeight() + 1, x.getHeight());
            }
            else
            {
                assertEquals(x.getInterv().getHigh(), x.getIMax());
                assertEquals(0, x.getHeight());
            }

            _testStructureRecursive(x.getLeft());
            _testStructureRecursive(x.getRight());
        }
    }

    /**
     * BeforeClass ensures this runs before everything else only once.
     * Constructs all the structures for each input size by reading in
     * the files.
     * @throws FileNotFoundException because file may not be found
     */
    @BeforeClass
    public static void construct() throws FileNotFoundException
    {
        // construct small treap
        Scanner input = new Scanner(new File(smallPath));
        smallPositives = readPositives(input);
        smallNegatives = readNegatives(input);
        smallNodes = readNodes(input);

        for (Node n : smallNodes)
            smallTreap.intervalInsert(n);

        // construct medium treap
        input = new Scanner(new File(mediumPath));
        mediumPositives = readPositives(input);
        mediumNegatives = readNegatives(input);
        mediumNodes = readNodes(input);

        for (Node n : mediumNodes)
            mediumTreap.intervalInsert(n);

        // construct large treap
        input = new Scanner(new File(largePath));
        largePositives = readPositives(input);
        largeNegatives = readNegatives(input);
        largeNodes = readNodes(input);

        for (Node n : largeNodes)
            largeTreap.intervalInsert(n);
    }

    //
    // TEST POSITIVE AND NEGATIVE TEST CASES FOR intervalSearch()
    //

    @Test
    public void _testSmallInput()
    {
        assertEquals(smallNodes.size(), smallTreap.getSize());

        for (Interval i : smallPositives)
        {
            Node n = smallTreap.intervalSearch(i);
            assertNotNull(n);
            assertTrue(smallNodes.contains(n));
            assertTrue(n.getInterv().overlaps(i));
        }

        for (Interval i : smallNegatives)
        {
            Node n = smallTreap.intervalSearch(i);
            assertNull(smallTreap.intervalSearch(i));
        }

        _testStructureRecursive(smallTreap.getRoot());
    }

    @Test
    public void _testMediumInput()
    {
        assertEquals(mediumNodes.size(), mediumTreap.getSize());

        for (Interval i : mediumPositives)
        {
            Node n = mediumTreap.intervalSearch(i);
            assertNotNull(n);
            assertTrue(mediumNodes.contains(n));
            assertTrue(n.getInterv().overlaps(i));
        }

        for (Interval i : mediumNegatives)
        {
            Node n = mediumTreap.intervalSearch(i);
            assertNull(mediumTreap.intervalSearch(i));
        }

        _testStructureRecursive(mediumTreap.getRoot());
    }

    @Test
    public void _testLargeInput()
    {
        assertEquals(largeNodes.size(), largeTreap.getSize());

        for (Interval i : largePositives)
        {
            Node n = largeTreap.intervalSearch(i);
            assertNotNull(n);
            assertTrue(largeNodes.contains(n));
            assertTrue(n.getInterv().overlaps(i));
        }

        for (Interval i : largeNegatives)
        {
            Node n = largeTreap.intervalSearch(i);
            assertNull(largeTreap.intervalSearch(i));
        }

        _testStructureRecursive(largeTreap.getRoot());
    }

    //
    // TEST IN-ORDER
    //

    @Test
    public void _testSmallInOrder()
    {
        List<Node> inOrderNodes = smallTreap.inOrder();
        assertEquals(inOrderNodes.size(), smallNodes.size());

        for (int i = 1; i < inOrderNodes.size(); i++)
        {
            assertTrue(inOrderNodes.get(i - 1).key() <= inOrderNodes.get(i).key());
        }
    }

    @Test
    public void _testMediumInOrder()
    {
        List<Node> inOrderNodes = mediumTreap.inOrder();
        assertEquals(inOrderNodes.size(), mediumNodes.size());

        for (int i = 1; i < inOrderNodes.size(); i++)
        {
            assertTrue(inOrderNodes.get(i - 1).key() <= inOrderNodes.get(i).key());
        }
    }

    @Test
    public void _testLargeInOrder()
    {
        List<Node> inOrderNodes = largeTreap.inOrder();
        assertEquals(inOrderNodes.size(), largeNodes.size());

        for (int i = 1; i < inOrderNodes.size(); i++)
        {
            assertTrue(inOrderNodes.get(i - 1).key() <= inOrderNodes.get(i).key());
        }
    }

    //
    // TEST THE STRUCTURE OF EACH TREAP
    //

    @Test
    public void _testSmallStructure()
    {
        _testStructureRecursive(smallTreap.getRoot());
    }

    @Test
    public void _testMediumStructure()
    {
        _testStructureRecursive(mediumTreap.getRoot());
    }

    @Test
    public void _testLargeStructure()
    {
        _testStructureRecursive(largeTreap.getRoot());
    }

    //
    // TEST overlappingIntervals() EXTRA CREDIT
    //

    @Test
    public void _testSmallOverlappingIntervals()
    {
        for (Interval interval : smallPositives)
        {
            List<Interval> intervals = smallTreap.overlappingIntervals(interval);
            assertTrue(intervals.size() >= 1);

            for (Interval i : intervals)
            {
                assertTrue(i.overlaps(interval));
            }
        }

        for (Interval interval : smallNegatives)
        {
            List<Interval> intervals = smallTreap.overlappingIntervals(interval);
            assertNull(intervals);
        }
    }

    @Test
    public void _testMediumOverlappingIntervals()
    {
        for (Interval interval : mediumPositives)
        {
            List<Interval> intervals = mediumTreap.overlappingIntervals(interval);
            assertTrue(intervals.size() >= 1);

            for (Interval i : intervals)
            {
                assertTrue(i.overlaps(interval));
            }
        }

        for (Interval interval : mediumNegatives)
        {
            List<Interval> intervals = mediumTreap.overlappingIntervals(interval);
            assertNull(intervals);
        }
    }

    @Test
    public void _testLargeOverlappingIntervals()
    {
        for (Interval interval : largePositives)
        {
            List<Interval> intervals = largeTreap.overlappingIntervals(interval);
            assertTrue(intervals.size() >= 1);

            for (Interval i : intervals)
            {
                assertTrue(i.overlaps(interval));
            }
        }

        for (Interval interval : largeNegatives)
        {
            List<Interval> intervals = largeTreap.overlappingIntervals(interval);
            assertNull(intervals);
        }
    }

    //
    // TEST DELETION
    //

    @Test
    public void testSmallDelete()
    {
        int size = smallNodes.size();

        for (Node node : smallNodes)
        {
            smallTreap.intervalDelete(node);
            assertEquals(--size, smallTreap.getSize()); // size is decremented properly
            _testStructureRecursive(smallTreap.getRoot()); // validate structure after delete
        }

        assertNull(smallTreap.getRoot());
        assertEquals(0, smallTreap.getHeight());
        assertEquals(0, smallTreap.getSize());
    }

    @Test
    public void testMediumDelete()
    {
        int size = mediumNodes.size();

        for (Node node : mediumNodes)
        {
            mediumTreap.intervalDelete(node);
            assertEquals(--size, mediumTreap.getSize());
            _testStructureRecursive(mediumTreap.getRoot());
        }

        assertNull(mediumTreap.getRoot());
        assertEquals(0, mediumTreap.getHeight());
        assertEquals(0, mediumTreap.getSize());
    }

    @Test
    public void testLargeDelete()
    {
        int size = largeNodes.size();

        for (Node node : largeNodes)
        {
            largeTreap.intervalDelete(node);
            assertEquals(--size, largeTreap.getSize());
            _testStructureRecursive(largeTreap.getRoot());
        }

        assertNull(largeTreap.getRoot());
        assertEquals(0, largeTreap.getHeight());
        assertEquals(0, largeTreap.getSize());
    }

    //
    // TEST intervalSearchExactly() EXTRA CREDIT
    //

    @Test
    public void _testSmallExactly()
    {
        //216 236
        Interval exactPositive = new Interval(216, 236);
        Interval exactNegative = new Interval(216, 237);

        Node p = smallTreap.intervalSearchExactly(exactPositive);
        Node n = smallTreap.intervalSearchExactly(exactNegative);

        assertNotNull(p);
        assertTrue(p.getInterv().equals(exactPositive));

        assertNull(n);

        for (Node node : smallNodes)
        {
            Node exactNode = smallTreap.intervalSearchExactly(node.getInterv());
            assertNotNull(exactNode);
            assertTrue(exactNode.getInterv().equals(node.getInterv()));
        }

        for (Interval i : smallNegatives)
        {
            Node exactNode = smallTreap.intervalSearchExactly(i);
            assertNull(exactNode);
        }
    }

    @Test
    public void _testMediumExactly()
    {
        // 38 81
        Interval exactPositive = new Interval(38, 81);
        Interval exactNegative = new Interval(38, 82);

        Node p = mediumTreap.intervalSearchExactly(exactPositive);
        Node n = mediumTreap.intervalSearchExactly(exactNegative);

        assertNotNull(p);
        assertTrue(p.getInterv().equals(exactPositive));

        assertNull(n);

        for (Node node : mediumNodes)
        {
            Node exactNode = mediumTreap.intervalSearchExactly(node.getInterv());
            assertNotNull(exactNode);
            assertTrue(exactNode.getInterv().equals(node.getInterv()));
        }

        for (Interval i : mediumNegatives)
        {
            Node exactNode = mediumTreap.intervalSearchExactly(i);
            assertNull(exactNode);
        }
    }

    @Test
    public void _testLargeExactly()
    {
        // 69771 69792
        Interval exactPositive = new Interval(69771, 69792);
        Interval exactNegative = new Interval(69771, 69793);

        Node p = largeTreap.intervalSearchExactly(exactPositive);
        Node n = largeTreap.intervalSearchExactly(exactNegative);

        assertNotNull(p);
        assertTrue(p.getInterv().equals(exactPositive));

        assertNull(n);

        for (Node node : largeNodes)
        {
            Node exactNode = largeTreap.intervalSearchExactly(node.getInterv());
            assertNotNull(exactNode);
            assertTrue(exactNode.getInterv().equals(node.getInterv()));
        }

        for (Interval i : largeNegatives)
        {
            Node exactNode = largeTreap.intervalSearchExactly(i);
            assertNull(exactNode);
        }
    }

    //
    // TEST CLASSES & METHODS
    //

    /**
     * Tests minimum()
     */
    @Test
    public void _testSmallIntervalTreapMinimum()
    {
        Node minimum = smallTreap.minimum();
        int min = smallTreap.getRoot().key();
        for (Node n : smallNodes)
        {
            assertTrue(n.key() >= minimum.key());
            min = Math.min(min, n.key());
        }

        assertEquals(minimum.key(), min);
    }

    @Test
    public void _testInterval()
    {
        boolean thrown = false;
        Interval interval = new Interval(47, 138);
        Interval i1 = new Interval(9, 46);
        Interval i2 = new Interval(15, 47);
        Interval i3 = new Interval(130, 150);
        Interval i4 = new Interval(139, 155);
        Interval i5 = new Interval(139, 155);

        try
        {
            Interval invalidInterval = new Interval(2, 1);
        }
        catch (Error e)
        {
            thrown = true;
        }

        assertTrue(thrown);

        assertTrue(i4.equals(i5));

        assertEquals(47, interval.getLow());
        assertEquals(138, interval.getHigh());

        assertFalse(interval.overlaps(i1));
        assertTrue(interval.overlaps(i2));
        assertTrue(interval.overlaps(i3));
        assertFalse(interval.overlaps(i4));
    }

    @Test
    public void _testNode()
    {
        Interval i1 = new Interval(4, 5);
        Interval i2 = new Interval(3, 4);
        Interval i3 = new Interval(5, 6);

        Node n1 = new Node(i1);
        Node n2 = new Node(i2);
        Node n3 = new Node(i3);

        n1.setLeft(n2);
        n2.setParent(n1);
        n1.setRight(n3);
        n3.setParent(n1);

        assertNull(n1.getParent());
        assertEquals(n1, n2.getParent());
        assertEquals(n1, n3.getParent());
        assertEquals(n2, n1.getLeft());
        assertEquals(n3, n1.getRight());
        assertEquals(4, n1.key());
    }

    @Test
    public void _testIntervalTreap()
    {
        Interval i1 = new Interval(4, 5);
        Interval i2 = new Interval(3, 4);
        Interval i3 = new Interval(5, 6);

        Node n1 = new Node(i1);
        Node n2 = new Node(i2);
        Node n3 = new Node(i3);

        IntervalTreap treap = new IntervalTreap();
        assertNull(treap.getRoot());

        treap.intervalInsert(n1);
        assertEquals(n1, treap.getRoot());
    }
}
