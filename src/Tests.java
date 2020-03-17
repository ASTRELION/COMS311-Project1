import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests would usually be in another package but
 * since the project uses the default Java package
 * as requested we must include tests here
 */
public class Tests
{
    @Test
    public void testInterval()
    {
        boolean thrown = false;
        Interval interval = new Interval(47, 138);
        Interval i1 = new Interval(9, 46);
        Interval i2 = new Interval(15, 47);
        Interval i3 = new Interval(130, 150);
        Interval i4 = new Interval(139, 155);

        try
        {
            Interval invalidInterval = new Interval(2, 1);
        }
        catch (Error e)
        {
            thrown = true;
        }

        assertTrue(thrown);

        assertEquals(47, interval.getLow());
        assertEquals(138, interval.getHigh());

        assertFalse(interval.overlaps(i1));
        assertTrue(interval.overlaps(i2));
        assertTrue(interval.overlaps(i3));
        assertFalse(interval.overlaps(i4));
    }

    @Test
    public void testNode()
    {
        Interval i1 = new Interval(4, 5);
        Interval i2 = new Interval(3, 4);
        Interval i3 = new Interval(5, 6);

        Node n1 = new Node(i1);
        Node n2 = new Node(i2);
        Node n3 = new Node(i3);

        n1.setLeft(n2);
        n1.setRight(n3);

        assertNull(n1.getParent());
        assertEquals(n1, n2.getParent());
        assertEquals(n1, n3.getParent());
        assertEquals(n2, n1.getLeft());
        assertEquals(n3, n1.getRight());
        assertEquals(4, n1.key());
    }

    @Test
    public void testIntervalTreap()
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
