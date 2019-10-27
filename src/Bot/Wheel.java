package Bot;

import UI.Display;

public enum Wheel {
    FRONT_LEFT(new PolarArrow(0, 0)),
    FRONT_RIGHT(new PolarArrow(Display.botWidth, 0)),
    BACK_LEFT(new PolarArrow(0, Display.botLength)),
    BACK_RIGHT(new PolarArrow(Display.botWidth, Display.botLength)),
    FRONT_LEFT_TRUE(new PolarArrow(0, 0)),
    FRONT_RIGHT_TRUE(new PolarArrow(Display.botWidth, 0)),
    BACK_LEFT_TRUE(new PolarArrow(0, Display.botLength)),
    BACK_RIGHT_TRUE(new PolarArrow(Display.botWidth, Display.botLength));

    private PolarArrow vector;

    Wheel(PolarArrow v) {
        vector = v;
    }

    public PolarArrow getVector() {
        return vector;
    }
}
