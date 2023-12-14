package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class FocusFreak extends Application {

	private int score = 0;
	private Random random = new Random();
	private boolean isCircleVisible = true;
	int timeSeconds = 30;
	Button nextButton;

	@Override
	public void start(Stage primaryStage) {
		

		GridPane root = new GridPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setVgap(10);
		root.setHgap(10);

		Circle circle = new Circle(75, Color.DEEPSKYBLUE);
		circle.setId("circle");
		circle.setVisible(isCircleVisible);

		Rectangle rectangle = new Rectangle(150, 150, Color.DARKORANGE);
		rectangle.setId("rectangle");
		rectangle.setVisible(!isCircleVisible);

		Label circleBox = new Label("Drop Circle Here");
		circleBox.setId("circle");
		circleBox.setPrefSize(200, 200);
		circleBox.setStyle("-fx-border-color: black; -fx-background-color: lightgrey; -fx-alignment: center;");

		Label rectangleBox = new Label("Drop Rectangle Here");
		rectangleBox.setId("rectangle");
		rectangleBox.setPrefSize(200, 200);
		rectangleBox.setStyle("-fx-border-color: black; -fx-background-color: lightgrey; -fx-alignment: center;");

		// For the upper Bar

				Label timerLabel = new Label("Time left: " + timeSeconds);
				timerLabel.setFont(new Font("Arial", 15));
				timerLabel.setStyle(
						"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");

				Label scoreLabel = new Label("Score: " + score + " / 120");
				scoreLabel.setFont(new Font("Arial", 15));
				scoreLabel.setStyle(
						"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");

				Button quitButton = new Button("Quit");
				quitButton.setFont(new Font("Arial", 15));
				quitButton.setStyle(
						"-fx-background-color: #f00; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
				quitButton.setOnAction(e -> primaryStage.close());
				nextButton = new Button("Next");
				updateButtonColor();
				Group group = new Group();
				HBox hbox = new HBox(20);
				hbox.setPadding(new Insets(15, 12, 15, 12));
				hbox.setStyle("-fx-background-color: #336699;");
				hbox.getChildren().addAll(timerLabel, scoreLabel, quitButton, nextButton);

				hbox.setTranslateX(170);
				hbox.setStyle(
						"-fx-background-color: #00FFFF; -fx-text-fill: #ffff; -fx-padding: 10px; -fx-border-color: #000000; -fx-border-radius: 20px; -fx-background-radius: 20px;");
				hbox.setTranslateY(5);
				group.getChildren().add(hbox);
				Timeline countdownTimeline = new Timeline();
				countdownTimeline.setCycleCount(Timeline.INDEFINITE);

				countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
					timeSeconds--;
					timerLabel.setText("Time left: " + timeSeconds);
					if (timeSeconds <= 0) {
						countdownTimeline.stop();
					}
				}));
				countdownTimeline.playFromStart();
		
		circle.setOnDragDetected(event -> {
			Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(circle.getId());
			db.setContent(content);
			event.consume();
		});

		rectangle.setOnDragDetected(event -> {
			Dragboard db = rectangle.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString(rectangle.getId());
			db.setContent(content);
			event.consume();
		});

		circleBox.setOnDragOver(event -> {
			if (event.getGestureSource() != circleBox && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		rectangleBox.setOnDragOver(event -> {
			if (event.getGestureSource() != rectangleBox && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		circleBox.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString() && timeSeconds > 0) {
				if (db.getString().equals(circleBox.getId())) {
					score += 10;
				} else {
					score -= 5;
				}
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
			scoreLabel.setText("Score: " + score + " / 120"); // Update score label
			updateButtonColor(); // Update button color based on new score

		});

		rectangleBox.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString() && timeSeconds > 0) {
				if (db.getString().equals(rectangleBox.getId())) {
					score += 10;
				} else {
					score -= 5;
				}
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
			scoreLabel.setText("Score: " + score + " / 120"); // Update score label
			updateButtonColor(); // Update button color based on new score
		});

		root.add(circle, 0, 0);
		root.add(rectangle, 1, 0);
		root.add(circleBox, 0, 1);
		root.add(rectangleBox, 1, 1);
		root.add(group, 0, 2, 2, 1);

		Scene sceneentry = new Scene(root, 800, 750, Color.LIGHTBLUE);
		primaryStage.setScene(sceneentry);
		primaryStage.show();

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
		    if (timeSeconds > 0) { // Check if timeSeconds is greater than 0
		        isCircleVisible = !isCircleVisible;
		        circle.setVisible(isCircleVisible);
		        rectangle.setVisible(!isCircleVisible);
		        circle.setFill(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0));
		        rectangle.setFill(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0));
		    }
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
//--------------------------------------------------------------------------------------------------------------------------------------------------------		
		Image image = new Image("file:///C:\\Users\\salah\\Downloads\\_144f0a42-b647-4f11-a5e1-e56505e3f2ef.jpeg");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(800);
		imageView.setFitHeight(750);
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #2f4f4f;");
		BorderPane p = new BorderPane();
		Label Title = new Label("Focus Freek");
		Title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50));

		// Apply CSS styles to the title
		Title.setStyle("-fx-background-color: #8b0000; " + // Dark red background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 50px; " + // Font size 50px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 5px; " + // Border width 5px
				"-fx-border-radius: 20px; " + // Rounded border corners
				"-fx-background-radius: 20px;"); // Rounded background corners

		HBox hbtop = new HBox();
		hbtop.getChildren().add(Title);
		hbtop.setTranslateX(250);
		hbtop.setTranslateY(20);
		p.setTop(hbtop);
		HBox hbcenter = new HBox();
		Label discription = new Label(" The Game Focus Freak is a game\n" + " that depands on your consantration.\n "
				+ "Here is the construction you should do to win the game\n "
				+ "First you should get a score of 120 points in 30 seconds\n "
				+ "Second when the ball is shown you should drag it \n" + " to the circle box and also the same for the rectangle 10 points\n "
				+ "if you drag the shap to the wrong box then you loose 5 points\n "
				+ "the shape change every 2 second ");

		// Apply CSS styles to the label
		discription.setStyle("-fx-background-color: #a5ffd6; " + // Light green background color
				"-fx-text-fill: black; " + // Black text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		hbcenter.getChildren().add(discription);
		hbcenter.setTranslateX(60);
		hbcenter.setTranslateY(50);
		p.setCenter(hbcenter);
		HBox hbbutton = new HBox();
		Button start = new Button("Start");

		// Apply CSS styles to the button
		start.setStyle("-fx-background-color: #ff7f50; " + // Coral background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		hbbutton.getChildren().add(start);
		p.setBottom(hbbutton);
		hbbutton.setTranslateX(380);
		hbbutton.setTranslateY(250);

		start.setOnAction(e -> {
			primaryStage.setScene(sceneentry);
		});

		pane.getChildren().addAll(imageView, p);
		Scene scene = new Scene(pane, 800, 750);
		primaryStage.setTitle("Stress Out Start Up Page");
		primaryStage.setTitle("Stress Out start Up Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// Method to update the color of the button based on the score
	public void updateButtonColor() {
		if (score >= 120) {
			nextButton.setFont(new Font("Arial", 15));
			nextButton.setStyle(
					"-fx-background-color: #0f0; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
			nextButton.setOnAction(e -> System.out.println("Hello"));
		} else {
			nextButton.setFont(new Font("Arial", 20));
			nextButton.setStyle("-fx-background-color: #d3d3d3; " + // Light gray background color
					"-fx-text-fill: #000; " + // Black text
					"-fx-padding: 10px; " + // Padding around text
					"-fx-border-color: #000; " + // Black border
					"-fx-border-radius: 20px; " + // Rounded border corners
					"-fx-background-radius: 20px;" // Rounded background corners
			);
		}
	}
}