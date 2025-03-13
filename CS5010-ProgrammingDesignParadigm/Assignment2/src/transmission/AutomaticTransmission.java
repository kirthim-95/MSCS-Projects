package transmission;

public class AutomaticTransmission implements Transmission {
    private int speed = 0;
    private int gear = 0;
    private final int[] thresholds;

    public AutomaticTransmission (int t1, int t2, int t3, int t4, int t5) {
        if(t1 <= 0 || t2 < t1 || t3 < t2 || t4 < t3 || t5 < t4) {
            throw new IllegalArgumentException("Thresholds are invalid.");
        }
        this.thresholds = new int[] {t1, t2, t3, t4, t5};
    }

    @Override
    public Transmission increaseSpeed() {
        this.speed += 2;
        updateGear();
        return this;
    }

    private void updateGear() {
        if(speed == 0) {
            this.gear = 0;
        }
        else if(speed < thresholds[0]) {
            this.gear = 1;
        }
        else if(speed < thresholds[1]) {
            this.gear = 2;
        }
        else if(speed < thresholds[2]) {
            this.gear = 3;
        }
        else if(speed < thresholds[3]) {
            this.gear = 4;
        }
        else if(speed < thresholds[4]) {
            this.gear = 5;
        }
        else {
            this.gear = 6;
        }
    }

    @Override
    public Transmission decreaseSpeed() {
        if(this.speed - 2 < 0) {
            throw new IllegalStateException("Speed cannot be negative.");
        }
        this.speed -= 2;
        updateGear();
        return this;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getGear() {
        return this.gear;
    }

    @Override
    public String toString() {
        return "Transmission (speed = " + this.speed + ", gear = " + this.gear + ")";
    }
}
