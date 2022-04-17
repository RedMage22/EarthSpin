package org.redmage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class EarthSpin extends Application {

    Logger logger = Logger.getLogger(getClass().getName());
    private Rectangle2D screenBounds;
    private double windowWidth;
    private double windowHeight;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("scene.fxml"));
            root = loader.load();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        screenBounds = Screen.getPrimary().getBounds();
        windowHeight = screenBounds.getHeight();
        windowWidth = screenBounds.getWidth();
        logger.info("Screen height = " + windowHeight);
        logger.info("Screen width = " + windowWidth);

        Scene scene = new Scene(root, windowWidth/2, windowHeight/2, true, SceneAntialiasing.BALANCED);
        primaryStage.setTitle("Earth Spin");
        primaryStage.setScene(scene);

        FXMLController controller = loader.getController();
        controller.initializeStage(primaryStage, screenBounds);

        primaryStage.show();
    }
}
