package MainScreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainScreen extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
		Pane p = new Pane();

		// Create an Image object
		Image geekgamserim = new Image(
				"https://raw.githubusercontent.com/Youshida90/GUIProject/main/Mohamad-Part/Icons/answer.png");

		// Create an ImageView object
		ImageView geekgameriv = new ImageView(geekgamserim);

		// Set the ImageView properties
		geekgameriv.setFitHeight(60); // or the desired height
		geekgameriv.setFitWidth(60); // or the desired width

		Button geekgamer = new Button();
		geekgamer.setTranslateX(50);
		geekgamer.setTranslateY(260);
		geekgamer.setMinSize(120, 120);
		// Set the button color and text
		geekgamer.setText("Geek Gamer");
		geekgamer.setTextFill(Color.WHITE);
		geekgamer.setStyle("-fx-background-color: #146F0C; -fx-font-size: 18px; -fx-border-color: black;Black border;-fx-border-width: 5px; Border width 5px; -fx-border-radius: 20px; Rounded border corners;-fx-background-radius: 20px;");
		geekgamer.setGraphic(geekgameriv);
		geekgamer.setContentDisplay(ContentDisplay.TOP);

		// Create an Image object
		Image ballonim = new Image("https://raw.githubusercontent.com/Youshida90/GUIProject/main/Mohamad-Part/Icons/balloons.png");
		// Create an ImageView object
		ImageView balloniv = new ImageView(ballonim);

		// Set the ImageView properties
		balloniv.setFitHeight(60); // or the desired height
		balloniv.setFitWidth(60); // or the desired width

		Button BalloonAssassinator = new Button();
		BalloonAssassinator.setTranslateX(250);
		BalloonAssassinator.setTranslateY(260);
		BalloonAssassinator.setMinSize(120, 120);

		// Set the button color and text
		BalloonAssassinator.setText("Balloon Assassinator");
		BalloonAssassinator.setTextFill(Color.WHITE);
		BalloonAssassinator.setStyle("-fx-background-color:  #4E1818; -fx-font-size: 18px;-fx-border-color: black;Black border;-fx-border-width: 5px; Border width 5px; -fx-border-radius: 20px; Rounded border corners;-fx-background-radius: 20px;");

		// Set the graphic (image) and position the text under the image
		BalloonAssassinator.setGraphic(balloniv);
		BalloonAssassinator.setContentDisplay(ContentDisplay.TOP);

		// Create an Image object
		Image Focusfreakim = new Image("https://raw.githubusercontent.com/Youshida90/GUIProject/main/Mohamad-Part/Icons/target.png");

		// Create an ImageView object
		ImageView Focusfreakiv = new ImageView(Focusfreakim);

		// Set the ImageView properties
		Focusfreakiv.setFitHeight(60); // or the desired height
		Focusfreakiv.setFitWidth(60); // or the desired width

		Button FocusFreak = new Button();
		FocusFreak.setTranslateX(550);
		FocusFreak.setTranslateY(260);
		FocusFreak.setMinSize(120, 120);

		// Set the button color and text
		FocusFreak.setText("Focus Freak");
		FocusFreak.setTextFill(Color.WHITE);
		FocusFreak.setStyle("-fx-background-color: #8F2100; -fx-font-size: 18px;-fx-border-color: black;Black border;-fx-border-width: 5px; Border width 5px; -fx-border-radius: 20px; Rounded border corners;-fx-background-radius: 20px;");

		// Set the graphic (image) and position the text under the image
		FocusFreak.setGraphic(Focusfreakiv);
		FocusFreak.setContentDisplay(ContentDisplay.TOP);

		
		
		
		// Create an Image object
		Image streesoutim = new Image("https://raw.githubusercontent.com/Youshida90/GUIProject/main/Mohamad-Part/Icons/brain.png");

		// Create an ImageView object
		ImageView streesoutiv = new ImageView(streesoutim);

		// Set the ImageView properties
		streesoutiv.setFitHeight(60); // or the desired height
		streesoutiv.setFitWidth(60); // or the desired width

		Button Stressout = new Button();
		Stressout.setTranslateX(180);
		Stressout.setTranslateY(450);
		Stressout.setMinSize(120, 120);

		// Set the button color and text
		Stressout.setText("Stress Out");
		Stressout.setTextFill(Color.WHITE);
		Stressout.setStyle("-fx-background-color: #351D5D; -fx-font-size: 18px;-fx-border-color: black;Black border;-fx-border-width: 5px; Border width 5px; -fx-border-radius: 20px; Rounded border corners;-fx-background-radius: 20px;");

		// Set the graphic (image) and position the text under the image
		Stressout.setGraphic(streesoutiv);
		Stressout.setContentDisplay(ContentDisplay.TOP);

		
		
		// Create an Image object
		Image Kangarooraceim = new Image("https://raw.githubusercontent.com/Youshida90/GUIProject/main/Mohamad-Part/Icons/kangaroo.png");

		// Create an ImageView object
		ImageView Kangarooiv = new ImageView(Kangarooraceim);

		// Set the ImageView properties
		Kangarooiv.setFitHeight(60); // or the desired height
		Kangarooiv.setFitWidth(60); // or the desired width

		Button Kangaroorace = new Button();
		Kangaroorace.setTranslateX(420);
		Kangaroorace.setTranslateY(450);
		Kangaroorace.setMinSize(120, 120);

		// Set the button color and text
		Kangaroorace.setText("Kangaroo Race");
		Kangaroorace.setTextFill(Color.WHITE);
		Kangaroorace.setStyle("-fx-background-color: #CC9900; -fx-font-size: 18px;-fx-border-color: black;Black border;-fx-border-width: 5px; Border width 5px; -fx-border-radius: 20px; Rounded border corners;-fx-background-radius: 20px;");

		// Set the graphic (image) and position the text under the image
		Kangaroorace.setGraphic(Kangarooiv);
		Kangaroorace.setContentDisplay(ContentDisplay.TOP);

		p.setStyle(
				"-fx-background-color: linear-gradient(from 25% 25% to 100% 100%,#5D3FD3,#0000FF); -fx-padding: 10; -fx-border-style: solid inside;");
		p.getChildren().addAll(geekgamer, BalloonAssassinator, FocusFreak, Stressout, Kangaroorace);
		Scene scence = new Scene(p, 800, 750);
		primaryStage.setScene(scence);
		primaryStage.setTitle("Main Page");
		primaryStage.show();
	}

}
