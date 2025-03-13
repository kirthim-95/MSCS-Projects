package questions;

import org.junit.Test;
import static org.junit.Assert.*;

public class LikertTest {

    @Test
    public void testValidAnswer() {
        Likert likert = new Likert("On a scale of 5, how much would you recommend a friend about this event?");
        assertEquals("Correct", likert.answer("1"));
        assertEquals("Correct", likert.answer("5"));
    }

    @Test
    public void testInvalidAnswer() {
        Likert likert = new Likert("Tell us how much you're empowered from our event on a scale of 5!");
        assertEquals("Incorrect", likert.answer("0"));
        assertEquals("Incorrect", likert.answer("6"));
        assertEquals("Incorrect", likert.answer("May be"));
    }
}