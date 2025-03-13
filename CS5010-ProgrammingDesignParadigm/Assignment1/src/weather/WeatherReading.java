package weather;

public class WeatherReading {
    private int temperature; //Degree Celsius
    private int dewPoint; //Degree Celsius
    private int windSpeed; //Miles per hour
    private int totalRain; //Millimeters

    public WeatherReading(int temperature, int dewPoint, int windSpeed, int totalRain) {
        //Assign values to current object
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.windSpeed = windSpeed;
        this.totalRain = totalRain;
    }
    //Accessor Methods or Getters
    public int getTemperature() {
        return temperature;
    }
    public int getDewPoint() {
        return dewPoint;
    }
    public int getWindSpeed() {
        return windSpeed;
    }
    public int getTotalRain() {
        return totalRain;
    }

    //Calculate Relative Humidity
    public int getRelativeHumidity() {
        return (int) 100 - 5 * (temperature - dewPoint);
    }

    //Calculate Heat Index
    public double getHeatIndex() {
        double T = temperature;
        double R = getRelativeHumidity();
        double c1 = -8.78469475556, c2 = 1.61139411, c3 = 2.33854883889, c4 = -0.14611605, c5 = -0.012308094, c6 = -0.0164248277778, c7 = 0.002211732, c8 = 0.00072546, c9 = -0.000003582;
        return c1 + (c2 * T) + (c3 * R) + (c4 * T * R) + (c5 * T * T) + (c6 * R * R) + (c7 * T * T * R) + (c8 * T * R * R) + (c9 * T * T * R * R);
    }

    //Calculate Wind Chill
    public double getWindChill() {
        double T = temperature * 9/5.0 + 32; //Converting celsius to Farenheit
        double v = windSpeed;
        return 35.74 + (0.6215 * T) - (35.75 * Math.pow(v, 0.16)) + (0.4275 * T * Math.pow(v, 0.16));
    }

    //Override String method to generate the expected output
    @Override
    public String toString() {
        return String.format("Reading: T = %d, D = %d, v = %d, rain = %d", temperature, dewPoint, windSpeed, totalRain);
    }

    public static void main(String[] args) {
        WeatherReading weather = new WeatherReading(23, 12, 3, 12);
        System.out.println(weather.toString());
        System.out.println("Relative Humidity: " + weather.getRelativeHumidity());
        System.out.println("Heat Index: " + weather.getHeatIndex());
        System.out.println("Wind Chill: " + weather.getWindChill());
    }
}
