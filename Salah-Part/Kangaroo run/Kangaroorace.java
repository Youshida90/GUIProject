package application;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Kangaroorace extends Application {
	private double Y = 0;
	private int score = 0;
	private int speed = 20;
	private int gameDuration = 30; // in seconds
	private int startupCountdown = 5;
	private Circle user = new Circle(10, Color.GREEN); // Use a circle to represent the user
	private Pane parkour = new Pane();
	private Label scoreLabel = new Label("Score: 0");
	private Label timerLabel = new Label("Time: " + gameDuration);
	private Label startupCountdownLabel = new Label("Starting in: " + startupCountdown);
	private Timeline timeline;
	private Image grassImage = new Image("file:///C:\\Users\\salah\\Downloads\\WhatsApp Image 2023-12-14 at 11.03.33_2017629c.jpg");
	private Image skyImage = new Image("file:///C:\\Users\\salah\\Downloads\\WhatsApp Image 2023-12-14 at 11.03.32_02d5ab1a.jpg");

	private boolean isSliding = false, isJumping = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		user.setTranslateX(250);
		user.setTranslateY(210);
		parkour.getChildren().add(user);

		ImageView grassImageView = new ImageView(grassImage);
		grassImageView.setFitWidth(1300);
		grassImageView.setFitHeight(500);
		grassImageView.setTranslateY(150);

		ImageView skyImageView = new ImageView(skyImage);
		skyImageView.setFitWidth(1300);
		skyImageView.setFitHeight(350);
		skyImageView.setTranslateY(-200);

		StackPane.setAlignment(scoreLabel, Pos.TOP_LEFT);
		scoreLabel.setStyle("-fx-font-weight: bold");

		StackPane.setAlignment(timerLabel, Pos.TOP_CENTER);

		StackPane startupCountdownPane = new StackPane(startupCountdownLabel);
		StackPane.setAlignment(startupCountdownPane, Pos.CENTER);

		StackPane imagesPane = new StackPane(skyImageView, grassImageView);
		StackPane.setAlignment(imagesPane, Pos.BOTTOM_LEFT);

		StackPane root = new StackPane(imagesPane, parkour, scoreLabel, timerLabel, startupCountdownPane);
		Scene scenetry = new Scene(root, 500, 200);
		scenetry.setFill(Color.CHOCOLATE);

		scenetry.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE && !isJumping) {
				jump();
				isJumping = true;
			} else if (event.getCode() == KeyCode.E && !isSliding) {
				slide();
				isSliding = true;
			}
		});

		scenetry.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.SPACE) {
				isJumping = false;
				user.setTranslateY(210);
			} else if (event.getCode() == KeyCode.E) {
				isSliding = false;
				user.setTranslateY(210);
			}
		});

		Image image = new Image("file:///C:\\Users\\salah\\Downloads\\_35fb06bb-e975-4968-8be5-78b58fcb3ecc.jpeg");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(800);
		imageView.setFitHeight(750);
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #2f4f4f;");
		BorderPane p = new BorderPane();
		Label Title = new Label("Kangaroo race");
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
		Label discription = new Label(" The Game Kangaroo game is a game\n" + " that depands on your consantration.\n "
				+ "Here is the construction you should do to win the game\n "
				+ "The ball should avoid the obsticles \n"
				+ " to do that you should press space and that will make you jump and E\n "
				+ "if the ball didnot avoid the obstecles you loose 5 points \n" + " else you can 10 points");

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
	
			startGameCountdown(primaryStage);
			primaryStage.setScene(scenetry);
		});

		pane.getChildren().addAll(imageView, p);
		Scene scene = new Scene(pane, 800, 750);
		primaryStage.setTitle("Kangroo race Start Up Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void startGameCountdown(Stage primaryStage) {
		Stage countdownStage = new Stage();
		countdownStage.initOwner(primaryStage);
		countdownStage.initModality(Modality.APPLICATION_MODAL);

		Timeline countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			startupCountdown--;
			startupCountdownLabel.setText("Starting in: " + startupCountdown);

			if (startupCountdown == 0) {
				countdownStage.close(); // Close the countdown pop-up
				startGame();
			}
		}));
		countdownTimeline.setCycleCount(startupCountdown);
		countdownTimeline.setOnFinished(event -> {
			countdownStage.close(); // Close the countdown pop-up after the timeline finishes
			primaryStage.setMaximized(true);
		});
		countdownTimeline.play();

		StackPane countdownPane = new StackPane(startupCountdownLabel);
		Scene countdownScene = new Scene(countdownPane, 200, 100);
		countdownStage.setScene(countdownScene);

		countdownStage.show();

		// Set the pop-up to close automatically after 5 seconds
		PauseTransition delay = new PauseTransition(Duration.seconds(5));
		delay.setOnFinished(event -> countdownStage.close());
		delay.play();
	}

	private void startGame() {
		Timeline gameDurationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			gameDuration--;
			timerLabel.setText("Time: " + gameDuration);

			if (gameDuration == 0) {
				timeline.stop(); // Stop the obstacle generation after game duration
				showResultPopup();

			}
		}));
		gameDurationTimeline.setCycleCount(gameDuration);
		gameDurationTimeline.play();

		timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
			int[] sizeOfJumpBox = { 20, 30, 40, 50 };
			int[] sizeOfSlideBox = { 10, 15, 20, 25 };

			Rectangle obstacle;
			int randomIndex = (int) (Math.random() * 2);

			if (randomIndex == 0) {
				int selectedSize = sizeOfJumpBox[(int) (Math.random() * sizeOfJumpBox.length)];
				obstacle = new Rectangle(100, 50, selectedSize, 100);
				obstacle.setFill(Color.BLUE);
				parkour.getChildren().add(1, obstacle);
			} else {
				int selectedSize = sizeOfSlideBox[(int) (Math.random() * sizeOfSlideBox.length)];
				obstacle = new Rectangle(100, 120 - selectedSize, selectedSize, 100);
				obstacle.setFill(Color.RED);
				parkour.getChildren().add(obstacle);
			}

			obstacle.setTranslateX(parkour.getWidth());
			obstacle.setTranslateY(80);

			TranslateTransition transition = new TranslateTransition(Duration.seconds(2), obstacle);
			transition.setToY(100);
			transition.setToX(-parkour.getWidth());
			transition.setOnFinished(e -> {

				if (obstacle.getFill() == Color.RED && getY() < 210) {
					// User collided with a red obstacle
					score += 10;
				} else if (obstacle.getFill() == Color.BLUE && getY() > 210) {
					// User successfully jumped over a blue obstacle
					score += 10;
				} else {
					score -= 5;
				}

				parkour.getChildren().remove(obstacle);
				updateScoreLabel();
			});

			transition.play();
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void showResultPopup() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Game Over");
			alert.setHeaderText(null);
			//-----------------------------------------------------------------
			alert.setContentText("Your final score: " + score);
			alert.showAndWait();
		});
	}

	private void jump() {
		TranslateTransition jumpTransition = new TranslateTransition(Duration.millis(200), user);
		jumpTransition.setToY(user.getTranslateY() - 50);
		jumpTransition.play();
		setY(jumpTransition.getToY());
		jumpTransition.setOnFinished(e -> user.setTranslateY(210));
	}

	private void setY(double y) {
		this.Y = y;
	}

	private double getY() {
		return Y;
	}

	private void slide() {
		TranslateTransition slideTransition = new TranslateTransition(Duration.millis(200), user);
		slideTransition.setToY(user.getTranslateY() + 50);
		slideTransition.play();
		setY(slideTransition.getToY());
		slideTransition.setOnFinished(e -> user.setTranslateY(210));
	}

	private void updateScoreLabel() {
		scoreLabel.setText("Score: " + score);

		if (score % 40 == 0) {
			speed *= 2;
			timeline.setRate(speed / 20.0);
		}
	}
}