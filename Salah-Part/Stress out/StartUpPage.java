package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StartUpPage extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Image image = new Image("file:///C:\\Users\\salah\\Downloads\\_4cdd8c3e-8743-4713-9d73-0273d0b42616.jpeg");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(800);
		imageView.setFitHeight(750);
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #2f4f4f;");
		BorderPane p = new BorderPane();
		Label Title = new Label("Stress Out");
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
		Label discription = new Label(" The Game Stress Out is a game\n" + " that depands on your consantration.\n "
				+ "Here is the construction you should do to win the game\n "
				+ "First you should get a score of 50 points in 30 seconds\n "
				+ "Second when the ball color is Maganta\n" + " and you press on g you gane 10 points\n "
				+ "if you press on g and the color is AQUAMARINE then you loose 5 points\n "
				+ "the ball calor change every second \n " + "at the last 10 seconds you will \n "
				+ "see the big ball color changing meaning you entered the last 10 seconds");

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
			StressOut st = new StressOut();
			st.start(primaryStage);
		});

		pane.getChildren().addAll(imageView, p);
		Scene scene = new Scene(pane, 800, 750);
		primaryStage.setTitle("Stress Out Start Up Page");
		primaryStage.setTitle("Stress Out start Up Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
