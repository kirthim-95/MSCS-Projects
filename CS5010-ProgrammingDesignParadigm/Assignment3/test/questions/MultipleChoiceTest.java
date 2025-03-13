package questions;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class MultipleChoiceTest {

    @Test
    public void testValidAnswer() {
        MultipleChoice mc = new MultipleChoice("What is the capital of India?", "1", "New Delhi", "Chennai", "Bangalore");
        assertEquals("Correct", mc.answer("1"));
    }

    @Test
    public void testInvalidAnswer() {
        MultipleChoice mc = new MultipleChoice("What is the capital of India?", "2", "Berlin", "Paris", "Madrid");
        assertEquals("Incorrect", mc.answer("3"));
        assertEquals("Incorrect", mc.answer("invalid"));
        assertEquals("Incorrect", mc.answer("4"));  // Out of bounds answer
    }

    @Test
    public void testValidOptionBoundaries() {
        // Test with maximum number of 8 options
        MultipleChoice mc = new MultipleChoice("Choose the correct answer", "8", "Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6", "Option 7", "Option 8");
        assertEquals("Correct", mc.answer("8"));
    }
}