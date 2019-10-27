package Bot;

public class PolarVector {
    private double magnitude, angle;

    protected void setMagnitude(double mag) {
        magnitude = mag;
    }

    protected void setAngle(double ang) {
        angle = ang;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getAngle() {
        return angle;
    }
}
