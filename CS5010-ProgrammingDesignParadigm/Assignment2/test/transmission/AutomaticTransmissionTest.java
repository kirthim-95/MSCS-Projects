package transmission;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutomaticTransmissionTest {

    @Test
    public void testValidAutomaticTransmission() {
        Transmission transmission = new AutomaticTransmission(10, 20, 30, 40, 50);
        assertEquals(0, transmission.getSpeed());
        assertEquals(0, transmission.getGear());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAutomaticTransmission() {
        Transmission transmission = new AutomaticTransmission(-1, -5, 30, 40, 50);
    }

    @Test
    public void testIncreaseSpeed() {
        Transmission transmission = new AutomaticTransmission(10, 20, 30, 40, 50);
        assertEquals(0, transmission.getSpeed());
        assertEquals(0, transmission.getGear());
    }

    @Test
    public void testDecreaseSpeed() {
        Transmission transmission = new AutomaticTransmission(10, 20, 30, 40, 50);
        assertEquals(0, transmission.getSpeed());
        assertEquals(0, transmission.getGear());
    }

    @Test(expected = IllegalStateException.class)
    public void testDecreaseSpeedNegative() {
        Transmission transmission = new AutomaticTransmission(10, 20, 30, 40, 50);
        transmission.decreaseSpeed();
    }
}
