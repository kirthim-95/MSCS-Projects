package bignumber;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BigNumberTest {

    private BigNumber bigNumber1;
    private BigNumber bigNumber2;

    @Before
    public void setUp() throws Exception {
        bigNumber1 = new BigNumberImpl("456789");
        bigNumber2 = new BigNumberImpl("123");
    }

    @Test
    public void testIsBigNumber() {
        BigNumber one = new BigNumberImpl();
        assertEquals("0", one.toString());
        assertEquals("456789", bigNumber1.toString());
        try {
            new BigNumberImpl("abcd");
            fail("Exception not thrown for invalid string");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Invalid digit!", e.getMessage());
        }
    }

    @Test
    public void testLength() {
        assertEquals(6, bigNumber1.length());
        assertEquals(3, bigNumber2.length());
    }

    @Test
    public void testAddDigit() {
        bigNumber1.addDigit(2);
        assertEquals("456791", bigNumber1.toString());
        bigNumber2.addDigit(5);
        assertEquals("128", bigNumber2.toString());
    }

    @Test
    public void testShiftLeft() {
        bigNumber1.shiftLeft(2);
        assertEquals("45678900", bigNumber1.toString());
        bigNumber2.shiftLeft(5);
        assertEquals("12300000", bigNumber2.toString());
        bigNumber1.shiftLeft(-2);
        assertEquals("456789", bigNumber1.toString());
        bigNumber2.shiftLeft(-5);
        assertEquals("123", bigNumber2.toString());
    }

    @Test
    public void testShiftRight() {
        bigNumber1.shiftRight(2);
        assertEquals("4567", bigNumber1.toString());
        bigNumber2.shiftRight(5);
        assertEquals("0", bigNumber2.toString());
        bigNumber1.shiftRight(-2);
        assertEquals("456700", bigNumber1.toString());
        bigNumber2.shiftRight(-5);
        assertEquals("0", bigNumber2.toString());
    }

    @Test
    public void testCompareTo() {
        assertEquals(1, bigNumber1.compareTo(bigNumber2));
        assertEquals(-1, bigNumber2.compareTo(bigNumber1));
        assertEquals(0, bigNumber1.compareTo(new BigNumberImpl("456789")));
    }

    @Test
    public void testCopy() {
        BigNumber copy = bigNumber1.copy();
        assertEquals("456789", copy.toString());
    }

    @Test
    public void testAdd() {
        BigNumber result = bigNumber1.add(bigNumber2);
        assertEquals("456912", result.toString());
    }

    @Test
    public void testGetDigitAt() {
        System.out.println("Big number1: "+bigNumber1.toString());
        System.out.println("Big number2: "+bigNumber2.toString());
        assertEquals(9, bigNumber1.getDigitAt(0));
        assertEquals(3, bigNumber2.getDigitAt(0));
        try {
            assertEquals(-1, bigNumber1.getDigitAt(7));
            fail("Exception not thrown for invalid position!");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Invalid position!", e.getMessage());
        }
    }
}
