import java.util.Random;

/**
 * COM S 311 Programming Project 1
 * Due: 2020.03.29 23:59
 * 100 points + 15 extra credit
 * @author Ashton Nelson
 * https://github.com/ASTRELION/COMS311-Project1
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("COM S 311 // Programming Project 1");
        System.out.println("---");

        IntervalTreap treap = new IntervalTreap();

        Interval i1 = new Interval(4, 8);
        Interval i2 = new Interval(2, 7);
        Interval i3 = new Interval(8, 42);
        Interval i4 = new Interval(1, 9);
        Interval i5 = new Interval(3, 10);
        Interval i6 = new Interval(6, 7);
        Interval i7 = new Interval(10, 14);

        Node n1 = new Node(i1);
        Node n2 = new Node(i2);
        Node n3 = new Node(i3);
        Node n4 = new Node(i4);
        Node n5 = new Node(i5);
        Node n6 = new Node(i6);
        Node n7 = new Node(i7);

        treap.intervalInsert(n1);
        treap.intervalInsert(n2);
        treap.intervalInsert(n3);
        treap.intervalInsert(n4);
        treap.intervalInsert(n5);
        treap.intervalInsert(n6);
        treap.intervalInsert(n7);

        System.out.println(treap);

        IntervalTreap treap2 = generateRandomTreap(15);
        System.out.println(treap2);
    }

    private static IntervalTreap generateRandomTreap(int n)
    {
        IntervalTreap treap = new IntervalTreap();
        Random random = new Random();

        int x = n;
        while (x >= 0)
        {
            Interval interval = new Interval(
                random.nextInt(n), random.nextInt(n) + n
            );

            Node node = new Node(interval);
            treap.intervalInsert(node);

            x--;
        }

        return treap;
    }
}
