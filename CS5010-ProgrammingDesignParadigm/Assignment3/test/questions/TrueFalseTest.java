package questions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrueFalseTest {
    @Test
    public void testTrueAnswer() {
        TrueFalse tf = new TrueFalse("Fall intake is in September.", "true");
        assertEquals("Correct", tf.answer("True"));
        assertEquals("Incorrect", tf.answer("False"));
    }

    @Test
    public void testFalseAnswer() {
        TrueFalse tf = new TrueFalse("Fall intake is in November.", "false");
        assertEquals("Correct", tf.answer("False"));
        assertEquals("Incorrect", tf.answer("True"));
    }

    @Test
    public void testInvalidAnswer() {
        TrueFalse tf = new TrueFalse("Fall intake is in September.", "true");
        assertEquals("Incorrect", tf.answer("Yes"));  // Invalid answer
        assertEquals("Incorrect", tf.answer("123"));  // Non-boolean answer
    }

    @Test
    public void testCaseInsensitiveAnswer() {
        TrueFalse tf = new TrueFalse("Fall intake is in September.", "true");
        assertEquals("Correct", tf.answer("true"));  // Case-insensitive check
        assertEquals("Correct", tf.answer("TRUE"));  // Case-insensitive check
    }
}
