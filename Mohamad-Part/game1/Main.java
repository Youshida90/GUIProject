package game1;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	Connection conn;
	Statement stmnt;
	
	@Override
	public void start(Stage primaryStage) {
		//Start Screen
		//Start screen titles
		Text startScreenTitle = new Text("GEEK Gamer");
		startScreenTitle.setTextAlignment(TextAlignment.LEFT);
		startScreenTitle.setFont(Font.font("Calibri",FontWeight.BOLD,FontPosture.ITALIC,70));
		startScreenTitle.setFill(Color.WHITE);
		startScreenTitle.setStroke(Color.BLACK);
		startScreenTitle.setStrokeWidth(3);
		Label startScreenInfo = new Label("This game tests your general knowledge on multiple subjects! "
										+ "\nYou need to get 50 points using 5 questions only to win this game!"
										+ "\nEach question rewards you 20 points if you answer correctly on the first try! "
										+ "\nEach incorrect answer decreaes the total points by 5!"
										+ "\nYou have 15 seconds to answer each question!"
										+ "\nAre you ready to become the best GEEK Gamer?");
		startScreenInfo.setFont(Font.font("Arial",FontWeight.BOLD,20));
		startScreenInfo.setTextFill(Color.LIGHTBLUE);
		startScreenInfo.setAlignment(Pos.CENTER);
		startScreenInfo.setTextAlignment(TextAlignment.CENTER);
		startScreenInfo.setWrapText(true);
		startScreenInfo.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		startScreenInfo.setPadding(new Insets(10,10,10,10));
		startScreenInfo.setPrefSize(700, 200);
		//Button that starts the game
		Button start = new Button("Start"); 
		start.setStyle("-fx-background-color: transparent;");
		start.setFont(Font.font("Arial",FontWeight.BOLD,20));
		//Background of start button
		Rectangle startBtnBack = new Rectangle(0,0,120,60);
		startBtnBack.setFill(Color.LIGHTBLUE);
		startBtnBack.setArcWidth(20);
		startBtnBack.setArcHeight(20);
		FillTransition startBtnTrans = new FillTransition();
		startBtnTrans.setShape(startBtnBack);
		startBtnTrans.setFromValue(Color.LIGHTBLUE);
		startBtnTrans.setToValue(Color.PINK);
		startBtnTrans.setCycleCount(Timeline.INDEFINITE);
		startBtnTrans.setDelay(Duration.millis(6000));
		startBtnTrans.setAutoReverse(true);
		startBtnTrans.setDuration(Duration.millis(700));
		startBtnTrans.play();
		//Pane that contains the start button and rectangle
		StackPane buttonWithTrans = new StackPane();
		buttonWithTrans.getChildren().addAll(startBtnBack,start);
		//Start screen pane
		BorderPane startScreenPane = new BorderPane();
		startScreenPane.setTop(startScreenTitle);
		startScreenPane.setCenter(startScreenInfo);
		startScreenPane.setBottom(buttonWithTrans);
		startScreenPane.setPadding(new Insets(10,10,10,10));
		ImageView background = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/geekGamefiles/startScreenBack-1.jpeg"));
		StackPane startScreenFinalPane = new StackPane(background,startScreenPane);
		//Start screen scene
		Scene startScreenScene = new Scene(startScreenFinalPane,900,700);
		background.fitHeightProperty().bind(startScreenScene.heightProperty());
		background.fitWidthProperty().bind(startScreenPane.widthProperty());
		primaryStage.setScene(startScreenScene);
		primaryStage.setTitle("Geek Gamer");
		primaryStage.show();
		
		//Database connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb","root","");
			System.out.println("Database connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not loaded");;
		} catch (SQLException e) {
			System.out.println("Database not connected");
		}
		
		//Selecting 5 random questions from DB
		String query = "select * from mcqs order by rand() limit 5";
		String[] questions = new String[5];
		Button[][] choices = new Button[5][4];
		try {
			stmnt = conn.createStatement();
			ResultSet mcqs = stmnt.executeQuery(query);
			int i = 0;
			while(mcqs.next()) {
				questions[i] = mcqs.getString(2);
				choices[i][0] = new Button(mcqs.getString(3));
				choices[i][1] = new Button(mcqs.getString(4));
				choices[i][2] = new Button(mcqs.getString(5));
				choices[i][3] = new Button(mcqs.getString(6));
				i++;
			}
		}
		catch(SQLException e) {
			System.out.println("Cannot load questions!");
		}
		
		//Main pane of game 1
		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10,10,10,10));
		
		//Right pane of mainPane. Shows nb of questions remaining and current score
		VBox rightPane = new VBox();
		rightPane.setSpacing(30);
		rightPane.setAlignment(Pos.CENTER);
		Label scoreText = new Label("Your score: 0/50");
		scoreText.setBackground(new Background(new BackgroundFill(Color.rgb(165, 255, 214, 0.8), new CornerRadii(20), Insets.EMPTY)));
		scoreText.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		scoreText.setTextFill(Color.BLACK);
		scoreText.setPrefSize(255,120);
		scoreText.setAlignment(Pos.CENTER);
		scoreText.setPadding(new Insets(10,10,10,10));
		Label qtsRemaining = new Label("Questions Remaining: 4");
		qtsRemaining.setWrapText(true);
		qtsRemaining.setBackground(new Background(new BackgroundFill(Color.rgb(165, 255, 214, 0.8), new CornerRadii(20), Insets.EMPTY)));
		qtsRemaining.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		qtsRemaining.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		qtsRemaining.setTextFill(Color.BLACK);
		qtsRemaining.setPrefSize(255,120);
		qtsRemaining.setAlignment(Pos.CENTER);
		qtsRemaining.setPadding(new Insets(10,10,10,10));
		rightPane.getChildren().addAll(scoreText,qtsRemaining);
		
		//Center pane of mainPane. Contains questions and choices
		GridPane centerPane = new GridPane();
		centerPane.setHgap(20);
		centerPane.setVgap(20);
		centerPane.setAlignment(Pos.CENTER);
		//Random question selection
		String[] selected = new String[3];
		selected[0] = "";
		int[] currentScore = new int[1];
		Label question = new Label();
		question.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		question.setWrapText(true);
		question.setTextFill(Color.WHITE);
		question.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.7), new CornerRadii(15), Insets.EMPTY)));
		question.setMaxWidth(420);
		question.setMinHeight(75);
		question.setPadding(new Insets(10,10,10,10));
		GridPane.setHalignment(question, HPos.CENTER);
		question.setTextAlignment(TextAlignment.CENTER);
		setCenterPane(centerPane, selected, questions, choices, question, currentScore, scoreText);
		
		//Time pane
		Pane timePane = new Pane();
		Circle path = new Circle(0,0,270);
		path.centerXProperty().bind(centerPane.widthProperty().divide(2));
		path.centerYProperty().bind(centerPane.heightProperty().divide(2));
		path.setFill(Color.TRANSPARENT);
		path.setStroke(Color.BLACK);
		path.setStrokeWidth(5);
		path.setOpacity(0.5);
		Circle indicator = new Circle(0,0,20);
		indicator.centerXProperty().bind(path.centerXProperty().add(path.getRadius()));
		indicator.centerYProperty().bind(centerPane.heightProperty().divide(2));
		indicator.setFill(Color.LAVENDER);
		indicator.setStroke(Color.BLACK);
		indicator.setStrokeWidth(5);
		timePane.getChildren().addAll(path,indicator);
		PathTransition indicatorPath = new PathTransition();
		indicatorPath.setNode(indicator);
		indicatorPath.setPath(path);
		indicatorPath.setDuration(Duration.seconds(15));
		indicatorPath.setDelay(Duration.millis(500));
		indicatorPath.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		StackPane combined = new StackPane(timePane,centerPane); //Combining mainPane and timePane
		
		//Bottom pane of mainPane. Contains next and quit button
		Button quit = new Button("Quit");
		quit.setFocusTraversable(false);
		quit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		quit.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		quit.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		Button next = new Button("Next");
		next.setFocusTraversable(false);
		next.setDisable(true);
		next.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		next.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		next.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		HBox buttons = new HBox(quit,next);
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		
		//Adding elements to mainPane
		mainPane.setCenter(combined);
		mainPane.setRight(rightPane);
		mainPane.setBottom(buttons);
		
		//Adding background image
		ImageView gameBackground = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/geekGamefiles/startScreenBack-2.jpeg"));
		StackPane mainFinalPane = new StackPane(gameBackground,mainPane);
		
		//Main Scene of game 1
		Scene mainScene = new Scene(mainFinalPane, 900, 700);
		gameBackground.fitHeightProperty().bind(mainScene.heightProperty());
		gameBackground.fitWidthProperty().bind(mainScene.widthProperty());
		
		//End screen
		BorderPane endScreenPane = new BorderPane();
		endScreenPane.setPadding(new Insets(10,10,10,10));
		Text endScreenTitle = new Text("Game Over");
		endScreenTitle.setTextAlignment(TextAlignment.LEFT);
		endScreenTitle.setFont(Font.font("Calibri",FontWeight.BOLD,FontPosture.ITALIC,70));
		endScreenTitle.setFill(Color.WHITE);
		endScreenTitle.setStroke(Color.BLACK);
		endScreenTitle.setStrokeWidth(3);
		Label endScreenInfo = new Label();
		endScreenInfo.setFont(Font.font("Arial",FontWeight.BOLD,50));
		endScreenInfo.setTextFill(Color.LIGHTBLUE);
		endScreenInfo.setAlignment(Pos.CENTER);
		endScreenInfo.setTextAlignment(TextAlignment.CENTER);
		endScreenInfo.setWrapText(true);
		endScreenInfo.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		endScreenInfo.setPadding(new Insets(10,10,10,10));
		endScreenInfo.setPrefSize(700, 200);
		Button done = new Button("Done");
		done.setFocusTraversable(false);
		done.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		done.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		done.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		BorderPane.setAlignment(done, Pos.CENTER);
		endScreenPane.setTop(endScreenTitle);
		endScreenPane.setCenter(endScreenInfo);
		endScreenPane.setBottom(done);
		ImageView endScreenBack = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/geekGamefiles/startScreenBack-1.jpeg"));
		StackPane endScreenFinalPane = new StackPane(endScreenBack,endScreenPane);
		Scene endScreenScene = new Scene(endScreenFinalPane,900,700);
		endScreenBack.fitHeightProperty().bind(endScreenScene.heightProperty());
		endScreenBack.fitWidthProperty().bind(endScreenScene.widthProperty());
		
		//Event handlers and listeners
		start.setOnAction(e ->{
			primaryStage.setScene(mainScene);
			indicatorPath.play();
		});
		
		quit.setOnAction(e ->{
			//add code to go back to homescreen
		});
		
		scoreText.textProperty().addListener(e ->{
			next.setDisable(false);
			indicatorPath.stop();
		});
		
		indicatorPath.setOnFinished(e ->{
			if(selected[0].length() < 5) {
				String currentQuestionString = qtsRemaining.getText();
				currentQuestionString = currentQuestionString.substring(currentQuestionString.indexOf(": ") + 2, currentQuestionString.length());
				int currentQuestion = Integer.parseInt(currentQuestionString) - 1;
				qtsRemaining.setText("Questions Remaining: " + currentQuestion);
				setCenterPane(centerPane, selected, questions, choices, question, currentScore, scoreText);
				next.setDisable(true);
				indicatorPath.play();
			}
			else {
				primaryStage.setScene(endScreenScene);
				String currentScoreString = scoreText.getText();
				currentScoreString = currentScoreString.substring(currentScoreString.indexOf(": ") + 2, currentScoreString.indexOf("/"));
				int score = Integer.parseInt(currentScoreString);
				if(score >= 50) {
					endScreenInfo.setText(scoreText.getText() + "\nYou Won!");
				}
				else {
					endScreenInfo.setText(scoreText.getText() + "\nYou Lost! Better luck next time ;(");
				}
				//add code to get score from db
				//if dbscore < score, update db and add text "New High Score!"
			}
		});
		
		next.setOnAction(e ->{
			if(selected[0].length() < 5) {
				String currentQuestionString = qtsRemaining.getText();
				currentQuestionString = currentQuestionString.substring(currentQuestionString.indexOf(": ") + 2, currentQuestionString.length());
				int currentQuestion = Integer.parseInt(currentQuestionString) - 1;
				qtsRemaining.setText("Questions Remaining: " + currentQuestion);
				setCenterPane(centerPane, selected, questions, choices, question, currentScore, scoreText);
				next.setDisable(true);
				indicatorPath.play();
			}
			else {
				endScreenInfo.setText(scoreText.getText());
				primaryStage.setScene(endScreenScene);
				String currentScoreString = scoreText.getText();
				currentScoreString = currentScoreString.substring(currentScoreString.indexOf(": ") + 2, currentScoreString.indexOf("/"));
				int score = Integer.parseInt(currentScoreString);
				if(score >= 50) {
					endScreenInfo.setText(scoreText.getText() + "\nYou Won!");
				}
				else {
					endScreenInfo.setText(scoreText.getText() + "\nYou Lost! Better luck next time ;(");
				}
				//add code to get score from db
				//if dbscore < score, update db and add text "New High Score!"
			}
		});
		
		done.setOnAction(e ->{
			//add code to go back to homescreen
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public int generateRandomQuestion(String selected, int limit) {
		int result = (int) (Math.random()*limit);
		while(selected.contains(Integer.toString(result))) {
			result = (int) (Math.random()*limit);
		}
		return result;
	}
	
	public void setCenterPane(GridPane centerPane, String[] selected, String[] questions, Button[][] choices, Label question, int[] nbs, Label score) {
		centerPane.getChildren().clear();
		int questionNb = generateRandomQuestion(selected[0], 5);
		selected[0] += questionNb;
		question.setText(questions[questionNb]);
		centerPane.add(question, 0, 0, 2, 1);
		//The correct answer for this question
		selected[2] = choices[questionNb][3].getText();
		//Randomizing the buttons
		selected[1] = "";
		nbs[0] = 20;
		int btnNb;
		int j = 0;
		for(int i = 1; i <= 4; i++) {
			btnNb = generateRandomQuestion(selected[1], 4);
			selected[1] += btnNb;
			Button option = choices[questionNb][btnNb];
			option.setFocusTraversable(false);
			option.setMinSize(200, 75);
			option.setMaxSize(200, 75);
			option.setFont(Font.font("Arial",FontWeight.BOLD,15));
			option.setWrapText(true);
			option.setTextAlignment(TextAlignment.CENTER);
			option.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
			option.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,new CornerRadii(20), Insets.EMPTY)));
			option.setOnAction(e ->{
				if(!option.getText().equals(selected[2])) {
					option.setBackground(new Background(new BackgroundFill(Color.PINK,new CornerRadii(20), Insets.EMPTY)));
					nbs[0] -= 5;
				}
				else {
					option.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(20), Insets.EMPTY)));;
					String currentScoreString = score.getText();
					currentScoreString = currentScoreString.substring(currentScoreString.indexOf(": ") + 2, currentScoreString.indexOf("/"));
					int currentScore = Integer.parseInt(currentScoreString) + nbs[0];
					score.setText("Your score: " + currentScore + "/50");
				}
			});
			centerPane.add(option, i%2, j%2 + 1);
			j = i%2 == 0? j+1:j;
		}
	}
}
