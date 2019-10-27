package UI;

import Bot.Bot;
import Bot.Wheel;
import Utils.SessionRecorder;
import Utils.ProgramLoop;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

    public class Display extends Application {
    private static boolean keyA, keyD, left, right, forward, backward;

    public static final int botWidth = 80, botLength = 80;

    private static Bot bot = new Bot(360, 0, botWidth, botLength);

    private static Stage primaryStage;

    private static Text txtHeading, txtXPos, txtYPos, txtXVel, txtYVel, txtThetaVel;

    public static int[] screenDims = {1280, 720};

    @Override
    public void start(Stage primaryStage) throws Exception {
        Display.primaryStage = primaryStage;

        Display.primaryStage.setResizable(true);
        Display.primaryStage.setFullScreen(true);
        Display.primaryStage.setTitle("Swerve");

        Group root = new Group();

        Group drawings = new Group();

        Wheel[] arrows = bot.getActingVels();
        Wheel[] arrows2 = bot.getTrueVels();
        drawings.getChildren().addAll(bot.getRect(), arrows[0].getVector().getArrow(), arrows[1].getVector().getArrow(),
                arrows[2].getVector().getArrow(), arrows[3].getVector().getArrow(), arrows2[0].getVector().getArrow(),
                arrows2[1].getVector().getArrow(), arrows2[2].getVector().getArrow(), arrows2[3].getVector().getArrow());

        txtHeading = new Text("Current Heading: " + bot.getHeading());

        txtXPos = new Text("Current X-Position: " + bot.getPosX());

        txtYPos = new Text("Current Y-Position: " + bot.getPosY());

        txtXVel = new Text("Current X-Velocity: " + bot.getCurrentVelocityX());

        txtYVel = new Text("Current Y-Velocity: " + bot.getCurrentVelocityY());

        txtThetaVel = new Text("Current Theta-Velocity: " + bot.getCurrentThetaVel());

        ProgramLoop.init();

        Button btnTimeStep = new Button("Time Step");
        btnTimeStep.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnTimeStep.getText());
            tid.setHeaderText("Enter the " + btnTimeStep.getText() + "; " + ProgramLoop.getTimeStep());
            tid.showAndWait();
            ProgramLoop.setTimeStep(Double.parseDouble(tid.getEditor().getText()));
        });

        Button btnMaxVel = new Button("Max Velocity");
        btnMaxVel.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnMaxVel.getText());
            tid.setHeaderText("Enter the " + btnMaxVel.getText() + "; " + bot.getMaxVel());
            tid.showAndWait();
            bot.setMaxVel(Double.parseDouble(tid.getEditor().getText()));
        });

        Button btnMaxAccel = new Button("Max Acceleration");
        btnMaxAccel.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnMaxAccel.getText());
            tid.setHeaderText("Enter the " + btnMaxAccel.getText() + "; " + bot.getMaxAccel());
            tid.showAndWait();
            bot.setMaxAccel(Double.parseDouble(tid.getEditor().getText()));
        });

        Button btnFrict = new Button("Friction");
        btnFrict.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnFrict.getText());
            tid.setHeaderText("Enter the " + btnFrict.getText() + "; " + bot.getFrict());
            tid.showAndWait();
            bot.setFrict(Double.parseDouble(tid.getEditor().getText()));
        });

        Button btnThetaMaxVel = new Button("Max Turning Velocity");
        btnFrict.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnThetaMaxVel.getText());
            tid.setHeaderText("Enter the " + btnThetaMaxVel.getText() + "; " + bot.getMaxThetaVel());
            tid.showAndWait();
            bot.setMaxThetaVel(Double.parseDouble(tid.getEditor().getText()));
        });

        Button btnThetaMaxAccel = new Button("Max Turning Acceleration");
        btnFrict.setOnAction(action -> {
            TextInputDialog tid = new TextInputDialog();
            tid.setTitle(btnThetaMaxAccel.getText());
            tid.setHeaderText("Enter the " + btnThetaMaxAccel.getText() + "; " + bot.getMaxThetaAccel());
            tid.showAndWait();
            bot.setMaxThetaAccel(Double.parseDouble(tid.getEditor().getText()));
        });

        VBox vbox = new VBox(5);

        vbox.getChildren().addAll(btnTimeStep, btnMaxVel, btnMaxAccel, btnThetaMaxVel, btnThetaMaxAccel, btnFrict, txtHeading, txtXPos, txtYPos, txtXVel, txtYVel, txtThetaVel);

        root.getChildren().add(vbox);
        root.getChildren().add(drawings);

        Scene scene = new Scene(root, screenDims[0], screenDims[1]);

        Display.primaryStage.setScene(scene);
        Display.primaryStage.show();

        scene.setOnKeyPressed(event -> {
            System.out.println("hi");
            switch (event.getCode()) {
                case UP:    forward = true; break;
                case DOWN:  backward = true; break;
                case LEFT:  left  = true; break;
                case RIGHT: right  = true; break;
                case A:     keyA = true; break;
                case D:     keyD = true; break;
            }
        });

        scene.setOnKeyReleased(event -> {
            System.out.println("hey");
            switch (event.getCode()) {
                case UP:    forward = false; break;
                case DOWN:  backward = false; break;
                case LEFT:  left  = false; break;
                case RIGHT: right  = false; break;
                case A:     keyA = false; break;
                case D:     keyD = false; break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ProgramLoop.stop();
    }

    public static void updateStage() {
        Platform.runLater(() -> {
            screenDims[0] = (int)primaryStage.getWidth();
            screenDims[1] = (int)primaryStage.getHeight();

            txtHeading.setText("Current Heading: " + bot.getHeading());

            txtXPos.setText("Current X-Position: " + bot.getPosX());

            txtYPos.setText("Current Y-Position: " + bot.getPosY());

            txtXVel.setText("Current X-Velocity: " + bot.getCurrentVelocityX());

            txtYVel.setText("Current Y-Velocity: " + bot.getCurrentVelocityY());

            txtThetaVel.setText("Current Theta-Velocity: " + bot.getCurrentThetaVel());
        });
    }

    public static int[] getStagePos() {
        return new int[] {(int)primaryStage.getX(), (int)primaryStage.getY()};
    }

    public static Bot getBot() {
        return bot;
    }

    public static boolean keyAPressed() {
        return keyA;
    }

    public static boolean keyDPressed() {
        return keyD;
    }

    public static boolean keyLeftPressed() {
        return left;
    }

    public static boolean keyRightPressed() {
        return right;
    }

    public static boolean keyForwardPressed() {
        return forward;
    }

    public static boolean keyBackwardPressed() {
        return backward;
    }

}
