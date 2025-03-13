package questions;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class MultipleSelectTest {

    @Test
    public void testValidAnswer() {
        MultipleSelect ms = new MultipleSelect("Which are valid?", "1 3", "Option1", "Option2", "Option3");
        assertEquals("Correct", ms.answer("1 3"));
    }

    @Test
    public void testInvalidAnswerExtraOption() {
        MultipleSelect ms = new MultipleSelect("Which are valid?", "1 3", "Option 1", "Option2", "Option3");
        assertEquals("Incorrect", ms.answer("1 2 3"));  // Includes invalid option
    }

    @Test
    public void testInvalidAnswerMissingOption() {
        MultipleSelect ms = new MultipleSelect("Which are valid?", "1 3", "Option1", "Option2", "Option3");
        assertEquals("Incorrect", ms.answer("1"));  // Missing one correct answer
    }

    @Test
    public void testInvalidNonNumericAnswer() {
        MultipleSelect ms = new MultipleSelect("Which are valid?", "1 3", "Option1", "Option2", "Option3");
        assertEquals("Incorrect", ms.answer("One Three"));  // Non-numeric input
    }

    @Test
    public void testValidOptionBoundaries() {
        // Test with maximum number of 8 options
        MultipleSelect ms = new MultipleSelect("Choose correct options", "1 8", "Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6", "Option 7", "Option 8");
        assertEquals("Correct", ms.answer("1 8"));
    }
}