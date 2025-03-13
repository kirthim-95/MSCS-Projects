package lookandsay;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class LookAndSayIteratorTest {

    @Test
    public void testConstructorWithTwoArguments() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator(BigInteger.valueOf(1),
                BigInteger.valueOf(2113));
        lookAndSayIterator.next();
        assertTrue(lookAndSayIterator.hasPrevious());
    }

    @Test
    public void testConstructorWithOneArgument() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator(BigInteger.valueOf(11));
        assertTrue(lookAndSayIterator.hasNext());
        lookAndSayIterator.next();
        assertTrue(lookAndSayIterator.hasPrevious());
    }

    @Test
    public void testConstructorWithZeroArgument() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator();
        assertTrue(lookAndSayIterator.hasNext());
        lookAndSayIterator.next();
        assertTrue(lookAndSayIterator.hasPrevious());
    }

    @Test
    public void testInvalidStartingSeedGreaterThanEndValue() {
        assertThrows(IllegalArgumentException.class, () -> new LookAndSayIterator(BigInteger.valueOf(2113),
                BigInteger.valueOf(11)));
    }

    @Test
    public void testInvalidStartingSeedWithZero() {
        assertThrows(IllegalArgumentException.class, () -> new LookAndSayIterator(BigInteger.valueOf(1001),
                BigInteger.valueOf(2113)));
    }

    @Test
    public void testInvalidStartingSeedWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> new LookAndSayIterator(BigInteger.valueOf(-1)));
    }

    @Test
    public void testNext() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator();
        assertEquals(new BigInteger("1"), lookAndSayIterator.next());
        assertEquals(new BigInteger("11"), lookAndSayIterator.next());
        assertEquals(new BigInteger("21"), lookAndSayIterator.next());
    }

    @Test
    public void testPrev() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator();
        assertEquals(new BigInteger("1"), lookAndSayIterator.next());
        assertEquals(new BigInteger("11"), lookAndSayIterator.next());
        assertEquals(new BigInteger("21"), lookAndSayIterator.next());
        assertEquals(new BigInteger("1211"), lookAndSayIterator.prev());
        assertEquals(new BigInteger("21"), lookAndSayIterator.prev());
    }

    @Test
    public void testHasNext() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator();
        assertTrue(lookAndSayIterator.hasNext());
    }

    @Test
    public void testHasPrev() {
        LookAndSayIterator lookAndSayIterator = new LookAndSayIterator();
        lookAndSayIterator.next();
        assertTrue(lookAndSayIterator.hasPrevious());
    }
}