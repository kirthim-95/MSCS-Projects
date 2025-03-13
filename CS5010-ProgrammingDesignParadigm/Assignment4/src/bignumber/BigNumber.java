package bignumber;

public interface BigNumber extends Comparable<BigNumber> {

    BigNumberNode getHead();
    void addDigit(int digit) throws IllegalArgumentException;
    void shiftLeft(int noOfShifts);
    void shiftRight(int noOfShifts);
    String toString();
    int length();
    int compareTo(BigNumber other);
    BigNumber copy();
    BigNumber add(BigNumber other);
    int getDigitAt(int position) throws IllegalArgumentException;
}
