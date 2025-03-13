package Midterm;

import practice.ListNode;
import java.util.function.Predicate;

public class OrderedListImpl<E extends Comparable<E>> implements OrderedList<E> {

    private final ListNode<E> head;
    private final ListNode<E> tail;
    private int size;

    public OrderedListImpl() {
        this.size = 0;
        this.head = new ListNode<>(null);
        this.tail = new ListNode<>(null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    @Override
    public void add(E val) {

        if(val == null) {
            throw new IllegalArgumentException("Cannot add a null value!");
        }

        ListNode<E> newNode = new ListNode<>(val);
        ListNode<E> prev = getInsertPosition(val);
        ListNode<E> nextNode = prev.next;

        prev.next = newNode;
        newNode.prev = prev;

        newNode.next = nextNode;
        nextNode.prev = newNode;

        this.size++;
    }

    private ListNode<E> getInsertPosition(E val) {

        if(this.size == 0) {
            return this.head;
        }

        ListNode<E> newPosition = this.head;

        while(newPosition.next != this.tail && val.compareTo(newPosition.next.val) > 0) {
            newPosition = newPosition.next;
        }

        return newPosition;
    }

    @Override
    public E get(int index) {

        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        ListNode<E> node = this.head.next;
        int position = 0;

        while(position++ != index) {
            node = node.next;
        }

        return node.val;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public OrderedList<E> subList(Predicate<E> pred) {
        OrderedList<E> subList = new OrderedListImpl<>();
        ListNode<E> current = this.head.next;

        while(current != this.tail) {
            if(pred.test(current.val)) {
                subList.add(current.val);
            }
            current = current.next;
        }

        return subList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode<E> listNode = this.head.next;
        while(listNode != this.tail) {
            sb.append(listNode.val);
            sb.append(" ");
            listNode = listNode.next;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
