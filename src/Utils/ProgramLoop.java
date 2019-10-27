package Utils;

import UI.Display;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class ProgramLoop implements Runnable {

    private static boolean record = true;
    private static String filename = "example_sim.csv";
    private static SessionRecorder recorder;
    private static double timeStep = 0.020;

    private static Point p;
    private static int[] screenPos = Display.getStagePos();

    private static Thread t;
    private static ProgramLoop u;

    private ProgramLoop() {}

    public static void init() {
        u = new ProgramLoop();
        recorder = new SessionRecorder(filename);
        t = new Thread(u);
        t.setDaemon(true);
        t.start();
    }

    public static int getCursorX() {
        return Math.max(0, Math.min(Display.screenDims[0], p.x - screenPos[0]));
    }

    public static int getCursorY() {
        return Math.max(0, Math.min(Display.screenDims[1], p.y - screenPos[1]));
    }

    /**
     * Run the loop (20 ms delay)
     */
    @Override
    public void run() {
        while(true) {
            p = MouseInfo.getPointerInfo().getLocation();
            screenPos = Display.getStagePos();
            Display.updateStage();
            Display.getBot().updateBot();

            ArrayList<Number> numbers = new ArrayList<>();

            recorder.append(Arrays.asList(Display.getBot().getHeading(), Display.getBot().getPosX(), Display.getBot().getPosY(),
                    Display.getBot().getCurrentThetaVel(), Display.getBot().getCurrentVelocityX(), Display.getBot().getCurrentVelocityY()));

            try {
                Thread.sleep((int)(timeStep * 1000));
            } catch (InterruptedException e) {
                System.out.println("interrupt");
            }
        }
    }

    public static void stop() {
        t.interrupt();
    }

    public static double getTimeStep() {
        return timeStep;
    }

    public static void setTimeStep(double step) {
        timeStep = step;
    }
}
