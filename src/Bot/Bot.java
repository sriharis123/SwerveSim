package Bot;

import UI.Display;
import Utils.ProgramLoop;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

//kinematics need to be in this class
public class Bot {

    private Rectangle rect;
    private double maxVel = 15, maxAccel = 25, frict = 0.40;
    private Wheel[] actingVels, trueVels, accels;

    private double currentThetaVel = 0, maxThetaVel = 4, maxThetaAccel = 15;
    Rotate rotate = new Rotate();
    Translate t = new Translate(-Display.botWidth / 2, -Display.botLength / 2),
            tinv = new Translate(Display.botWidth / 2, Display.botLength / 2);

    private int posX, posY;
    private double heading = 0, currentVelocityX = 0, currentVelocityY = 0;

    public Bot(int startX, int startY, int width, int height) {
        rect = new Rectangle(startX, startY, width, height);
        posX = startX;
        posY = startY;
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.TRANSPARENT);

        actingVels = new Wheel[] {Wheel.FRONT_LEFT, Wheel.FRONT_RIGHT, Wheel.BACK_LEFT, Wheel.BACK_RIGHT};
        trueVels = new Wheel[] {Wheel.FRONT_LEFT_TRUE, Wheel.FRONT_RIGHT_TRUE, Wheel.BACK_LEFT_TRUE, Wheel.BACK_RIGHT_TRUE};
    }

    public Rectangle getRect() {
        return rect;
    }

    public void updateBot() {
        Platform.runLater(() -> {
            //Initial Velo Calcs
            if (Display.keyLeftPressed()) {
                currentVelocityX = Math.max(-maxVel, Math.min(maxVel, currentVelocityX - maxAccel * ProgramLoop.getTimeStep()));
            }
            if (Display.keyRightPressed()) {
                currentVelocityX = Math.max(-maxVel, Math.min(maxVel, currentVelocityX + maxAccel * ProgramLoop.getTimeStep()));
            }
            if (Display.keyForwardPressed()) {
                currentVelocityY = Math.max(-maxVel, Math.min(maxVel, currentVelocityY - maxAccel * ProgramLoop.getTimeStep()));
            }
            if (Display.keyBackwardPressed()) {
                currentVelocityY = Math.max(-maxVel, Math.min(maxVel, currentVelocityY + maxAccel * ProgramLoop.getTimeStep()));
            }
            if (Display.keyAPressed()) {
                currentThetaVel = Math.max(-maxThetaVel, Math.min(maxThetaVel, currentThetaVel - maxThetaAccel * ProgramLoop.getTimeStep()));
            }
            if (Display.keyDPressed()) {
                currentThetaVel = Math.max(-maxThetaVel, Math.min(maxThetaVel, currentThetaVel + maxThetaAccel * ProgramLoop.getTimeStep()));
            }

            //Friction
            if (currentVelocityX < 0) {
                currentVelocityX += frict * maxAccel * ProgramLoop.getTimeStep();
            } else if (currentVelocityX > 0) {
                currentVelocityX -= frict * maxAccel * ProgramLoop.getTimeStep();
            }
            if (currentVelocityY < 0) {
                currentVelocityY += frict * maxAccel * ProgramLoop.getTimeStep();
            } else if (currentVelocityY > 0) {
                currentVelocityY -= frict * maxAccel * ProgramLoop.getTimeStep();
            }
            if (currentThetaVel < 0) {
                currentThetaVel += frict * maxThetaAccel * ProgramLoop.getTimeStep();
            } else if (currentVelocityY > 0) {
                currentThetaVel -= frict * maxThetaAccel * ProgramLoop.getTimeStep();
            }

            posX = Math.max(0, Math.min(Display.screenDims[0], posX + (int)(currentVelocityX)));
            posY = Math.max(0, Math.min(Display.screenDims[1], posY + (int)(currentVelocityY)));
            heading = heading + (int)(currentThetaVel);

            for (Wheel w : actingVels) {

                if (Display.keyLeftPressed() && Display.keyForwardPressed()) {
                    w.getVector().setMagnitude(Math.sqrt(Math.pow(currentVelocityX, 2) + Math.pow(currentVelocityY, 2)));
                    w.getVector().setAngle(-135 + heading);
                } else if (Display.keyRightPressed() && Display.keyForwardPressed()) {
                    w.getVector().setMagnitude(Math.sqrt(Math.pow(currentVelocityX, 2) + Math.pow(currentVelocityY, 2)));
                    w.getVector().setAngle(135 + heading);
                } else if (Display.keyLeftPressed() && Display.keyBackwardPressed()) {
                    w.getVector().setMagnitude(Math.sqrt(Math.pow(currentVelocityX, 2) + Math.pow(currentVelocityY, 2)));
                    w.getVector().setAngle(-45 + heading);
                } else if (Display.keyRightPressed() && Display.keyBackwardPressed()) {
                    w.getVector().setMagnitude(Math.sqrt(Math.pow(currentVelocityX, 2) + Math.pow(currentVelocityY, 2)));
                    w.getVector().setAngle(45 + heading);
                } else if (Display.keyLeftPressed()) {
                    w.getVector().setMagnitude(currentVelocityX);
                    w.getVector().setAngle(90);
                } else if (Display.keyRightPressed()) {
                    w.getVector().setMagnitude(currentVelocityX);
                    w.getVector().setAngle(90);
                } else if (Display.keyForwardPressed()) {
                    w.getVector().setMagnitude(currentVelocityY);
                    w.getVector().setAngle(0);
                } else if (Display.keyBackwardPressed()) {
                    w.getVector().setMagnitude(currentVelocityY);
                    w.getVector().setAngle(0);
                }

                w.getVector().redraw(posX, posY, Color.BLACK);
            }

            for (Wheel w : trueVels) {
                w.getVector().setMagnitude(Math.sqrt(Math.pow(currentVelocityX, 2) + Math.pow(currentVelocityY, 2)));

                w.getVector().redraw(posX, posY, currentVelocityX, currentVelocityY, Color.RED);
            }

            if (rect.getTransforms().size() > 0)
                rect.getTransforms().remove(0, 3);

            rotate.setAngle(heading);
            rotate.setPivotX(360);
            rotate.setPivotY(0);
            rect.getTransforms().add(tinv);
            rect.getTransforms().add(rotate);
            rect.getTransforms().add(t);

            rect.relocate(posX, posY);
        });
    }

    public Wheel[] getActingVels() {
        return actingVels;
    }

    public Wheel[] getTrueVels() {
        return trueVels;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public double getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
    }

    public double getMaxAccel() {
        return maxAccel;
    }

    public void setMaxAccel(double maxAccel) {
        this.maxAccel = maxAccel;
    }

    public double getFrict() {
        return frict;
    }

    public void setFrict(double frict) {
        this.frict = frict;
    }

    public double getHeading() {
        return heading;
    }

    public double getCurrentVelocityX() {
        return Math.abs(currentVelocityX) < 0.01 ? 0 : currentVelocityX;
    }

    public double getCurrentVelocityY() {
        return Math.abs(currentVelocityY) < 0.01 ? 0 : currentVelocityY;
    }

    public double getCurrentThetaVel() {
        return Math.abs(currentThetaVel) < 0.01 ? 0 : currentThetaVel;
    }

    public double getMaxThetaVel() {
        return maxThetaVel;
    }

    public void setMaxThetaVel(double maxThetaVel) {
        this.maxThetaVel = maxThetaVel;
    }

    public double getMaxThetaAccel() {
        return maxThetaAccel;
    }

    public void setMaxThetaAccel(double maxThetaAccel) {
        this.maxThetaAccel = maxThetaAccel;
    }
}
