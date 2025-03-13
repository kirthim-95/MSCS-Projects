package lookandsay;

import java.math.BigInteger;

public class LookAndSayIterator implements RIterator<BigInteger> {

    private final BigInteger end;
    private BigInteger current;

    public LookAndSayIterator(BigInteger seed, BigInteger end) {

        if (seed.compareTo(BigInteger.ZERO) <= 0 || seed.toString().contains("0") ||
                seed.compareTo(end) >= 0) {
            throw new IllegalArgumentException("Invalid starting seed or end value!");
        }

        this.current = seed;
        this.end = end;
    }

    public LookAndSayIterator(BigInteger seed) {
        this(seed, new BigInteger("9".repeat(100)));
    }

    public LookAndSayIterator() {
        this(BigInteger.ONE, new BigInteger("9".repeat(100)));
    }

    @Override
    public boolean hasPrevious() {
        return this.current.toString().length() % 2 == 0;
    }

    @Override
    public boolean hasNext() {
        return this.current.compareTo(this.end) < 0;
    }

    @Override
    public BigInteger prev() {

        if (!hasPrevious()) {
            return this.current;
        }

        BigInteger prev = this.current;
        this.current = getPrevLookAndSaySequence(this.current);
        return prev;
    }

    @Override
    public BigInteger next() {

        if (!hasNext()) {
            return this.current;
        }

        BigInteger next = this.current;
        this.current = getNextLookAndSaySequence(this.current);
        return next;
    }

    //Generate the next look-and-say sequence
    private BigInteger getNextLookAndSaySequence(BigInteger number) {

        StringBuilder result = new StringBuilder();
        String numberString = number.toString();
        int count = 0;
        int length = numberString.length();

        for (int i = 0; i < length; i++) {
            if (i == 0 || numberString.charAt(i) == numberString.charAt(i - 1)) {
                count++;
            } else {
                result.append(count).append(numberString.charAt(i - 1));
                count = 1;
            }
        }

        result.append(count).append(numberString.charAt(length - 1));
        return new BigInteger(result.toString());
    }

    //Generate the previous look-and-say sequence
    private BigInteger getPrevLookAndSaySequence(BigInteger number) {

        StringBuilder result = new StringBuilder();
        String numberString = number.toString();

        for (int i = 0; i < numberString.length(); i += 2) {
            int count = numberString.charAt(i) - '0';
            int digit = numberString.charAt(i + 1) - '0';
            while(count != 0) {
                result.append(digit);
                count--;
            }
        }

        return new BigInteger(result.toString());
    }
}