package bignumber;

public class BigNumberNode {

    int digit;
    BigNumberNode next;

    public BigNumberNode() {
        this.digit = 0;
        this.next = null;
    }

    public BigNumberNode(int digit) {
        this.digit = digit;
        this.next = null;
    }
}
