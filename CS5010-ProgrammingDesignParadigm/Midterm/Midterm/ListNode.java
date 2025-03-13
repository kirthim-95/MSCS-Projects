package Midterm;

public class ListNode<E> {

    public ListNode<E> next;
    public ListNode<E> prev;
    public E val;

    public ListNode(E val) {
        this.val = val;
    }
}
