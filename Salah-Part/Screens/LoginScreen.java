package Screens;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends Application {
	@SuppressWarnings("unused")
	private Statement stmt;
	private Connection connection;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception, SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver loaded");
		// Establish a connection
		connection = DriverManager.getConnection("jdbc:mysql://localhost/db_user", "root", "");
		System.out.println("Database connected");
		// Make a statement
		stmt = connection.createStatement();

		GridPane gridpane = new GridPane();

		Text login = new Text("Login");

		Text username = new Text("Enter your username: ");

		TextField usernametextfield = new TextField();

		Text password = new Text("Enter your password:");

		PasswordField passwordtextfield = new PasswordField();
		passwordtextfield.setFocusTraversable(false);

		TextField textField = new TextField();

		textField.textProperty().bindBidirectional(passwordtextfield.textProperty());

		Button toggleButton = new Button("Show");
		toggleButton.setOnAction(event -> {
			toggleButton.setText(toggleButton.getText().equals("Show") ? "Hide" : "Show");
			if (textField.isVisible()) {
				textField.setVisible(false);
				passwordtextfield.setVisible(true);
			} else {
				textField.setVisible(true);
				passwordtextfield.setVisible(false);
			}
		});

		Button loginbutton = new Button("Login");
		loginbutton.setFocusTraversable(false);
		loginbutton.setOnAction(e -> {
			String enteredPassword = passwordtextfield.getText();
			String encryptedEnteredPassword;
			try {
				encryptedEnteredPassword = encryptPassword(enteredPassword);
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
				return;
			}
			String[] result;
			try {
				result = findMyUser(usernametextfield.getText(), encryptedEnteredPassword);
			} catch (SQLException ex) {
				ex.printStackTrace();
				return;
			}
			
			
			int userId = Integer.parseInt(result[0]);
			String typeOfUser = result[1];
			String loggedInUsername = result[2];
			boolean isNewUser = Boolean.parseBoolean(result[3]);
			if (userId != -1) {
				// for the first page when the user enter the first game a
				String welcomeMessage = isNewUser ? "Welcome " : "Welcome back ";
					try {
						new Shopadminpage(welcomeMessage + loggedInUsername).start(arg0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			} else if (usernametextfield.getText() == null) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText("Please enter your username");
				alert.showAndWait();
			} else if (passwordtextfield.getText() == null) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText("Please enter a password");
				alert.showAndWait();
			} else if (!usernametextfield.getText().matches("^[a-zA-Z]+$")
					|| !passwordtextfield.getText().matches("^[a-zA-Z-1-9]+$")) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText("Please enter the correct pattern");
				alert.showAndWait();

			} else {

				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText(
						"Login Fiald the username or password doesnot exist? please try agin or signup for a new account");
				alert.showAndWait();
			}
		});

		Button register = new Button("no Account? Register");
		register.setFocusTraversable(false);
		register.setOnAction(event -> {
			// Create a new Signup object
			Signuppage signup = new Signuppage();
			try {
				// Start the Signup scene
				signup.start(arg0);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		gridpane.setVgap(10);
		gridpane.setHgap(10);
		gridpane.setPadding(new Insets(20));
		gridpane.add(login, 0, 1);
		gridpane.add(username, 0, 2);
		gridpane.add(usernametextfield, 1, 2);
		gridpane.add(password, 0, 3);
		gridpane.add(textField, 1, 3);
		textField.setVisible(false);
		gridpane.add(passwordtextfield, 1, 3);
		gridpane.add(toggleButton, 2, 3);
		gridpane.add(loginbutton, 0, 4);
		gridpane.add(register, 1, 4);

		Scene scene = new Scene(gridpane);
		arg0.setTitle("Login Page");
		arg0.setScene(scene);
		arg0.show();
	}

	public String[] findMyUser(String username, String password) throws SQLException {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return new String[] { "-1", null };
		}
		String query = "SELECT Userid, Username, isNewUser FROM signup WHERE Username = ? AND Password = ?";
		PreparedStatement pstmt = connection.prepareStatement(query);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rset = pstmt.executeQuery();
		if (rset.next()) {
			String userId = rset.getString(1);
			String typeOfUser = rset.getString(2);
			String loggedInUsername = rset.getString(3);
			boolean isNewUser = rset.getBoolean(4);

			// If the user is new, update isNewUser to false
			if (isNewUser) {
				String updateQuery = "UPDATE signup SET isNewUser = ? WHERE Userid = ?";
				PreparedStatement updatePstmt = connection.prepareStatement(updateQuery);
				updatePstmt.setBoolean(1, false);
				updatePstmt.setString(2, userId);
				updatePstmt.executeUpdate();
			}

			return new String[] { userId, typeOfUser, loggedInUsername, String.valueOf(isNewUser) };
		} else {
			return new String[] { "-1", null, null, null };
		}
	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}