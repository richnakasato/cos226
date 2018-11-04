/* *****************************************************************************
 *  Name: richard nakasato
 *  Date: 11/03/2018
 *  Description: deque implementation for week 2
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;                      // head of deque
    private Node tail;                      // end of deque
    private int size;                       // size of the deque

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    public Deque() {                        // construct an empty deque
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {              // is the deque empty?
        return size == 0;
    }

    public int size() {                     // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {       // add the item to the front
        Node temp = new Node();
        temp.item = item;
        temp.prev = null;
        temp.next = head;
        if (isEmpty()) {
            tail = temp;
        }
        else {
            head.prev = temp;
        }
        head = temp;
        ++size;
    }

    public void addLast(Item item) {        // add the item to the end
        Node temp = new Node();
        temp.item = item;
        if (isEmpty()) {
            head = temp;
        }
        else {
            temp.prev = tail;
            tail.next = temp;
        }
        tail = temp;
        ++size;
    }

    public Item removeFirst() {             // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty!");
        }
        Node temp = head;
        head = head.next;
        --size;
        if (!isEmpty()) {
            head.prev = null;
        }
        else {
            tail = head;
        }
        return temp.item;
    }

    public Item removeLast() {              // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty!");
        }
        Item item = tail.item;
        tail = tail.prev;
        --size;
        if (!isEmpty()) {
            tail.next = null;
        }
        else {
            head = tail;
        }
        return item;
    }

    public Iterator<Item> iterator() {      // return an iterator over items
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node curr = head;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (curr == null) {
                throw new NoSuchElementException("deque is empty!");
            }
            Item item = curr.item;
            curr = curr.next;
            return item;
        }

        public void remove() {
        }
    }

    public static void main(String[] args) { // unit testing (optional)
        int n = 10;
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < n; ++i) {
            if (i < 5)
                deque.addFirst(i);
            else
                deque.addLast(i);
        }
        for (int i : deque) {
            System.out.println(i);
        }
        for (int i = 0; i < n; ++i) {
            int j = deque.removeLast();
            System.out.println(j);
        }
    }
}
