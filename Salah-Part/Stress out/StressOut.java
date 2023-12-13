package application;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class StressOut extends Application {
	int score = 0;
	int timeSeconds = 30;
	Button nextButton;
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void start(Stage primaryStage) {
		Sphere largeSphere = new Sphere(200);
		PhongMaterial largeSphereMaterial = new PhongMaterial();
		largeSphereMaterial.setDiffuseColor(Color.BEIGE);
		largeSphere.setMaterial(largeSphereMaterial);
		largeSphere.setTranslateX(400);
		largeSphere.setTranslateY(330);
		largeSphere.setTranslateZ(0);

		Sphere fourthSphere = new Sphere(50);
		PhongMaterial fourthSphereMaterial = new PhongMaterial();
		fourthSphereMaterial.setDiffuseColor(Color.MAGENTA);
		fourthSphere.setMaterial(fourthSphereMaterial);
		fourthSphere.setTranslateX(400);
		fourthSphere.setTranslateY(330);
		fourthSphere.setTranslateZ(0);

		Circle path = new Circle();
		path.setCenterX(400);
		path.setCenterY(330);
		path.setRadius(250);

		Group group = new Group();
		group.getChildren().addAll(largeSphere, fourthSphere);

		Scene scene = new Scene(group, 800, 750);
		scene.setCamera(new PerspectiveCamera());

		primaryStage.setTitle("Stress Out");
		scene.setFill(Color.BEIGE);
		primaryStage.setTitle("Stress Out");
		primaryStage.setScene(scene);
		primaryStage.show();

		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(3));
		pathTransition.setPath(path);
		pathTransition.setNode(fourthSphere);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		pathTransition.play();

		Timeline colorChangeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			if (fourthSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
				fourthSphereMaterial.setDiffuseColor(Color.AQUAMARINE);
			} else {
				fourthSphereMaterial.setDiffuseColor(Color.MAGENTA);
			}
		}));
		colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
		colorChangeTimeline.play();

		
		Label timerLabel = new Label("Time left: " + timeSeconds);
		timerLabel.setFont(new Font("Arial", 20));
		timerLabel.setStyle(
				"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");

		

		Label scoreLabel = new Label("Score: " + score + " / 120");
		scoreLabel.setFont(new Font("Arial", 20));
		scoreLabel.setStyle(
				"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		
		Button quitButton = new Button("Quit");
		quitButton.setFont(new Font("Arial", 20));
		quitButton.setStyle(
				"-fx-background-color: #f00; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		quitButton.setOnAction(e -> {

			primaryStage.close();
		});
		nextButton =  new Button("Next");
		updateButtonColor();
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.getChildren().addAll(timerLabel, scoreLabel, quitButton, nextButton);

		hbox.setAlignment(Pos.CENTER);
		hbox.setTranslateX(200);
		hbox.setStyle(
				"-fx-background-color: #0fsdf5; -fx-text-fill: #ffff; -fx-padding: 10px; -fx-border-color: #000000; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		hbox.setTranslateY(largeSphere.getTranslateY() + largeSphere.getRadius() + 130);

		group.getChildren().add(hbox);

		Timeline countdownTimeline = new Timeline();
		countdownTimeline.setCycleCount(Timeline.INDEFINITE);

		Timeline flashTimeline = new Timeline();

		countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
			timeSeconds--;
			timerLabel.setText("Time left: " + timeSeconds);
			if (timeSeconds <= 10) {
				flashTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), ev -> {
					if (largeSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
						largeSphereMaterial.setDiffuseColor(Color.WHITE);
					} else {
						largeSphereMaterial.setDiffuseColor(Color.MAGENTA);
					}
				}));
				flashTimeline.setCycleCount(Timeline.INDEFINITE);
				flashTimeline.play();
			}
			if (timeSeconds <= 0) {
				countdownTimeline.stop();
				flashTimeline.stop();
				pathTransition.stop();
			}
		}));
		countdownTimeline.playFromStart();
		 
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case G:
				if (timeSeconds > 0) {
					if (fourthSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
						score += 10;
					} else {
						if (score >= 5) {
							score -= 5;
						}
					}
					scoreLabel.setText("Score: " + score + " / 120");
					updateButtonColor();
				}
				break;
			}
		});
	}
	
    // Method to update the color of the button based on the score
    public void updateButtonColor() {
        if(score >= 120) {
            nextButton.setFont(new Font("Arial", 20));
            nextButton.setStyle(
                    "-fx-background-color: #0f0; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
            nextButton.setOnAction(e -> {
                System.out.println("Hello");
            });
        } else {
            nextButton.setFont(new Font("Arial", 20));
            nextButton.setStyle(
                "-fx-background-color: #d3d3d3; " + // Light gray background color
                "-fx-text-fill: #000; " + // Black text
                "-fx-padding: 10px; " + // Padding around text
                "-fx-border-color: #000; " + // Black border
                "-fx-border-radius: 20px; " + // Rounded border corners
                "-fx-background-radius: 20px;" // Rounded background corners
            );
        }
    }
}