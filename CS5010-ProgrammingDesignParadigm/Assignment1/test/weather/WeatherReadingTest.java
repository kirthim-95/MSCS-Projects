package weather;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherReadingTest {

    @Test
    public void testWeatherReading() {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        assertEquals(23, weather.getTemperature());
        assertEquals(12, weather.getDewPoint());
        assertEquals(3, weather.getWindSpeed());
        assertEquals(12, weather.getTotalRain());
    }

    @Test
    public void testRelativeHumidity() {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        assertEquals(45, (int) weather.getRelativeHumidity());
    }

    @Test
    public void testHeatIndex() {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        assertEquals(25.112149608445005, weather.getHeatIndex(), 0.001);
    }

    @Test
    public void testWindChill() {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        assertEquals(76.14651430332569, weather.getWindChill(), 0.001);
    }

    @Test
    public void testToString() {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        assertEquals("Reading: T = 23, D = 12, v = 3, rain = 12", weather.toString());
    }
}