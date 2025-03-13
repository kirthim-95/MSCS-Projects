package priority;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinMaxPriorityQueueTest {

    MinMaxPriorityQueue<Integer> minMaxPriorityQueue;

    @Before
    public void setUp() {
        minMaxPriorityQueue = new MinMaxPriorityQueueImpl<>();
        minMaxPriorityQueue.add(1, 3);
        minMaxPriorityQueue.add(2, 4);
        minMaxPriorityQueue.add(3, 5);
        minMaxPriorityQueue.add(4, 3);
        minMaxPriorityQueue.add(5, 5);
        minMaxPriorityQueue.add(6, 4);
    }

    @Test
    public void testAdd() {
        assertEquals("3 : [1, 4]\n" + "4 : [2, 6]\n" + "5 : [3, 5]\n", minMaxPriorityQueue.toString());
    }

    @Test
    public void testMinPriority() {
        assertEquals(Integer.valueOf(1), minMaxPriorityQueue.minPriorityItem());
    }

    @Test
    public void testMaxPriority() {
        assertEquals(Integer.valueOf(3), minMaxPriorityQueue.maxPriorityItem());
    }
}
