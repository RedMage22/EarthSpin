package org.redmage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.logging.Logger;


public class FXMLController {

    final Logger logger = Logger.getLogger(getClass().getName());
    @FXML
    private ImageView starField;
    @FXML
    private SubScene subScene;
    @FXML
    private Sphere earth;
    @FXML
    private Group earthGroup;
    @FXML
    private Sphere moon;
    @FXML
    private Group moonGroup;


    public void initialize() {
        String earthDayImageFile = "images/2k_earth_day_map.jpg";
        String earthBumpImageFile = "images/2k_earth_normal_map.jpg";
        String earthNightImageFile = "images/2k_earth_night_map.jpg";
        String moonImageFile = "images/2k_moon.jpg";
        Image earthDayImage = null;
        Image earthBumpImage = null;
        Image earthNightImage = null;
        Image moonImage = null;
        try {
            earthDayImage = new Image(getClass().getResourceAsStream(earthDayImageFile));
            logger.info("Earth day image " + (earthDayImage.isError() ? "did not load :-(" : "loaded successfully!!! :-)"));
            earthBumpImage = new Image(getClass().getResourceAsStream(earthBumpImageFile));
            logger.info("Earth bump image " + (earthBumpImage.isError() ? "did not load :-(" : "loaded successfully!!! :-)"));
            earthNightImage = new Image(getClass().getResourceAsStream(earthNightImageFile));
            logger.info("Earth night image " + (earthNightImage.isError() ? "did not load :-(" : "loaded successfully!!! :-)"));
            moonImage = new Image(getClass().getResourceAsStream(moonImageFile));
            logger.info("Moon image " + (moonImage.isError() ? "did not load :-(" : "loaded successfully!!! :-)"));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        PhongMaterial earthPhong = new PhongMaterial();
        earthPhong.setDiffuseMap(earthDayImage);
        earthPhong.setBumpMap(earthBumpImage);
        earthPhong.setSelfIlluminationMap(earthNightImage);
        earth.setMaterial(earthPhong);
        earth.setCullFace(CullFace.BACK);
        earth.setCacheHint(CacheHint.SCALE_AND_ROTATE);
        PhongMaterial moonPhong = new PhongMaterial();
        moonPhong.setDiffuseMap(moonImage);
        moon.setRadius(earth.getRadius() * 0.27);
        moon.setMaterial(moonPhong);
        moon.setCullFace(CullFace.BACK);
        moon.setCacheHint(CacheHint.SCALE_AND_ROTATE);
        earthGroup.setLayoutX(subScene.getWidth()/2);
        logger.info("SubScene width = " + subScene.getWidth());
        earthGroup.setLayoutY(subScene.getHeight()/2);
        logger.info("SubScene height = " + subScene.getHeight());


        Rotate earthRotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Translate earthTranslatePivot = new Translate(0, 0, 0);
        earth.getTransforms().addAll(earthTranslatePivot, earthRotateYPivot, new Rotate(0, Rotate.Y_AXIS), new Translate(0, 0, 0));

        Timeline earthTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(earthRotateYPivot.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(24),
                        new KeyValue(earthRotateYPivot.angleProperty(), -360))
        );

        earthTimeline.setCycleCount(Timeline.INDEFINITE);
        earthTimeline.play();

        Rotate moonRotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        Translate moonTranslatePivot = new Translate(0, 0, 0);
        moon.getTransforms().addAll(moonTranslatePivot, moonRotateYPivot, new Rotate(0, Rotate.Y_AXIS), new Translate(0, 0, 0));

        Double moonOrbitAndRotationTime = 648d;

        Timeline moonTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(moonRotateYPivot.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(moonOrbitAndRotationTime),
                        new KeyValue(moonRotateYPivot.angleProperty(), -360))
        );

        moonTimeline.setCycleCount(Timeline.INDEFINITE);
        moonTimeline.play();

        Rotate moonGroupRotateYPivot = new Rotate(0, Rotate.Y_AXIS);
        double moonOrbitZDistance = 800;
        moonGroup.setTranslateZ(moonOrbitZDistance);
        Translate moonGroupTranslatePivot = new Translate(0, 0, -moonOrbitZDistance);
        moonGroup.getTransforms().addAll(moonGroupTranslatePivot, moonGroupRotateYPivot, new Rotate(0, Rotate.Y_AXIS), new Translate(0, 0, moonOrbitZDistance));

        Timeline moonGroupTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(moonGroupRotateYPivot.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(moonOrbitAndRotationTime),
                        new KeyValue(moonGroupRotateYPivot.angleProperty(), -360))
        );

        moonGroupTimeline.setCycleCount(Timeline.INDEFINITE);
        moonGroupTimeline.play();


        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(35);
        camera.setNearClip(0.01);
        camera.setFarClip(100);
        camera.translateZProperty().set(-3000);
        earthGroup.getChildren().add(camera);
    }
}
