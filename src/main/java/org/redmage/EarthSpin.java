package org.redmage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class EarthSpin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        Scene scene = new Scene(root, 2048, 1024, true, SceneAntialiasing.BALANCED);
//        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Earth Spin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
