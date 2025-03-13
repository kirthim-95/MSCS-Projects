package bignumber;

public class BigNumberImpl implements BigNumber {

    private BigNumberNode head;
    private int length;

    public BigNumberImpl() {
        this.head = new BigNumberNode(0);
        this.length = 1;
    }

    public BigNumberImpl(String number) {
        BigNumber bigNumber = new BigNumberImpl();
        int length = 0;
        char[] chars = number.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int n = Character.getNumericValue(chars[i]);
            if (n < 0) {
                throw new IllegalArgumentException("Invalid number string!");
            }
            bigNumber.addDigit(n);
            if (i != (number.length() - 1)) {
                bigNumber.shiftLeft(1);
            }
            length++;
        }
        this.head = bigNumber.getHead();
        this.length = length;
    }

    @Override
    public BigNumberNode getHead() {
        return this.head;
    }

    @Override
    public int compareTo(BigNumber o) {
        if(this.length() > o.length()) {
            return 1;
        }
        else if(this.length() < o.length()) {
            return -1;
        }
        String first = this.toString();
        String second = o.toString();
        for (int i = 0; i < first.length(); i++) {
            if(Character.getNumericValue(first.charAt(i)) > Character.getNumericValue(second.charAt(i))) {
                return 1;
            } else if(Character.getNumericValue(first.charAt(i)) < Character.getNumericValue(second.charAt(i))) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public BigNumber copy() {
        BigNumberImpl copy = new BigNumberImpl();

        BigNumberNode current = this.getHead();
        BigNumberNode copyHead = copy.getHead();

        while(current != null) {
            copyHead.digit = current.digit;
            current = current.next;
            if(current != null) {
                copyHead.next = new BigNumberNode(0);
                copyHead = copyHead.next;
            }
        }
        copy.length = this.length;
        return copy;
    }

    @Override
    public BigNumber add(BigNumber other) {
        BigNumberImpl result = new BigNumberImpl();
        BigNumberNode currentHead = this.head;
        BigNumberNode otherHead = ((BigNumberImpl) other).head;
        BigNumberNode resultHead = result.head;

        int val = 0, carry = 0, rem = 0;

        int max = Math.max(this.length(), other.length());
        while(currentHead != null || otherHead != null) {
            val = resultHead.digit;
            if(currentHead != null) {
                val += currentHead.digit;
                currentHead = currentHead.next;
            }
            if(otherHead != null) {
                val += otherHead.digit;
                otherHead = otherHead.next;
            }
            rem = val % 10;
            carry = val / 10;
            resultHead.digit = rem;
            if(currentHead != null || otherHead != null) {
                resultHead.next = new BigNumberNode(carry);
                resultHead = resultHead.next;
            }
            else if(carry > 0) {
                max++;
                resultHead.next = new BigNumberNode(carry);
            }
        }
        result.length = max;
        return result;
    }

    @Override
    public int getDigitAt(int position) throws IllegalArgumentException {
        int length = this.length();
        if(position < 0 || position >= this.length()) {
            throw new IllegalArgumentException("Invalid position!");
        }
        int counter = 0;
        BigNumberNode current = this.head;
        while (counter != position) {
            current = current.next;
            counter++;
        }
        return current.digit;
    }

    @Override
    public void addDigit(int digit) throws IllegalArgumentException {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Invalid digit!");
        }
        BigNumberNode current = this.head;
        int val = 0, rem = 0, carry = digit;
        while (carry > 0) {
            val = current.digit + carry;
            rem = val % 10;
            carry = val / 10;

            current.digit = rem;

            if (current.next == null && carry > 0) {
                current.next = new BigNumberNode();
                current = current.next;
                this.length += 1;
            } else if (carry > 0) {
                current = current.next;
            }
        }
    }

    @Override
    public void shiftLeft(int noOfShifts) {

        if (noOfShifts < 0) {
            shiftRight(Math.abs(noOfShifts));
            return;
        }

        if ((this.length() == 1 && this.head.digit == 0) || noOfShifts == 0) {
            return;
        }

        BigNumberNode newNode = new BigNumberNode();
        BigNumberNode current = newNode;

        for (int i = 1; i < noOfShifts; i++) {
            current.next = new BigNumberNode();
            current = current.next;
        }

        current.next = this.head;
        this.head = newNode;
        this.length += noOfShifts;
    }

    @Override
    public void shiftRight(int noOfShifts) {

        if (noOfShifts < 0) {
            shiftLeft(Math.abs(noOfShifts));
        }
        else {
            if (noOfShifts >= this.length()) {
                this.head = new BigNumberNode(0);
                this.length = 1;
            } else {
                BigNumberNode current = this.head;
                for (int i = 0; i < noOfShifts; i++) {
                    current = current.next;
                }
                this.head = current;
                this.length -= noOfShifts;
            }
        }
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        BigNumberNode current = this.head;
        while (current != null) {
            sb.append(current.digit);
            current = current.next;
        }
        return sb.reverse().toString();
    }
}