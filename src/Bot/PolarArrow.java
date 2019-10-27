package Bot;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PolarArrow {

    Line arrow = new Line();

    private double magnitude, angle;
    private int initX, initY;

    public PolarArrow(int initX, int initY){
        super();
        this.initX = initX;
        this.initY = initY;

        setMagnitude(0);
        setAngle(0);
    }

    public void redraw(int posX, int posY, Color color) {
        double endX = magnitude * Math.sin(Math.toRadians(angle)) * 3;
        double endY = magnitude * Math.cos(Math.toRadians(angle)) * 3;

        arrow.setStartX(posX + initX);
        arrow.setStartY(posY + initY);
        arrow.setEndX(posX + initX + endX);
        arrow.setEndY(posY + initY + endY);

        arrow.setStroke(color);

    }

    public void redraw(int posX, int posY, double endX, double endY, Color color) {
        arrow.setStartX(posX + initX);
        arrow.setStartY(posY + initY);
        arrow.setEndX(posX + initX + (magnitude * (int)endX) / 6);
        arrow.setEndY(posY + initY + (magnitude * (int)endY) / 6);

        arrow.setStroke(color);
    }

    public void setMagnitude(double mag) {
        magnitude = mag;
    }

    public void setAngle(double ang) {
        angle = ang;
    }

    public Line getArrow() {
        return arrow;
    }
}