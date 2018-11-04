/* *****************************************************************************
 *  Name: richard nakasato
 *  Date: 11/03/2018
 *  Description: random queue implementation for week 2
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 1;
    private static final int GROWTH_FACTOR = 2;
    private static final int SHRINK_FACTOR = 4;
    private static final int INIT_VAL = 0;

    private Item[] array = (Item[]) new Object[INIT_CAPACITY];
    private int top = INIT_VAL;

    public RandomizedQueue() {               // construct an empty random queue
    }

    public boolean isEmpty() {               // is the randomized queue empty?
        return top == 0;
    }

    private boolean isFull() {               // is the randomized queue full?
        return top == array.length;
    }

    public int size() {                      // return the number of items
        return top;
    }

    public void enqueue(Item item) {         // add the item
        if (item == null) {
            throw new IllegalArgumentException("item shouldn't be null!");
        }
        if (isFull()) {
            resizeUp(array.length * GROWTH_FACTOR);
        }
        array[top++] = item;
    }

    private void resizeUp(int newCapacity) {    // adjust size of internal array
        Item[] temp = (Item[]) new Object[newCapacity];
        for (int i = 0; i < array.length; ++i) {
            temp[i] = array[i];
        }
        array = temp;
    }

    private void resizeDown(int newCapacity) {  // adjust size of internal array
        if (newCapacity >= INIT_CAPACITY) {
            Item[] temp = (Item[]) new Object[newCapacity];
            for (int i = 0; i < size(); ++i) {
                temp[i] = array[i];
            }
            array = temp;
        }
    }

    public Item dequeue() {                  // remove and return a random item
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty!");
        }
        int random = StdRandom.uniform(INIT_VAL, top);
        Item temp = array[random];
        if (random != top - 1) {
            array[random] = array[top - 1];
        }
        top--;
        if (size() == array.length / SHRINK_FACTOR) {
            resizeDown(array.length / GROWTH_FACTOR);
        }
        return temp;
    }

    public Item sample() {                   // return an unremoved random item
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty!");
        }
        return array[StdRandom.uniform(INIT_VAL, top)];
    }

    public Iterator<Item> iterator() {       // return an iterator
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] order = StdRandom.permutation(top);
        private int curr = 0;

        public boolean hasNext() {
            return curr < top;
        }

        public Item next() {
            if (isEmpty()) {
                throw new ArrayIndexOutOfBoundsException("queue is empty!");
            }
            return array[order[curr++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove is unsupported!");
        }
    }

    public static void main(String[] args) { // unit testing (optional)
        int n = 25;
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < n; ++i) {
            queue.enqueue(i + 1);
        }
        int sum = n * (n + 1) / 2;
        for (int i = 0; i < n; ++i) {
            sum -= queue.dequeue();
        }
        if (sum != 0) {
            throw new RuntimeException("sum is not 0!");
        }
        for (int i = 0; i < n; ++i) {
            queue.enqueue(i + 1);
        }
        for (int item : queue) {
            System.out.println(item);
        }
        queue.isEmpty();
        queue.size();
        queue.isEmpty();
        queue.isEmpty();
        queue.enqueue(174);
        queue.dequeue();
        queue.size();
        queue.enqueue(674);
        System.out.println("done");
    }
}
