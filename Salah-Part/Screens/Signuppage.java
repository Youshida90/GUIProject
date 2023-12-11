package Screens;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Signuppage extends Application {
	@SuppressWarnings("unused")
	private Statement stmt;
	private Connection connection;
	MessageDigest md;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver loaded");
		// Establish a connection
		connection = DriverManager.getConnection("jdbc:mysql://localhost/db_user", "root", "");
		System.out.println("Database connected");
		// Make a statement
		stmt = connection.createStatement();

		GridPane gridpane = new GridPane();

		Text Registertext = new Text("Registration");

		Text email = new Text("Ente your email:");

		TextField emailtextfield = new TextField();

		Text username = new Text("Enter your username: ");

		TextField usernametextfield = new TextField();
		usernametextfield.setFocusTraversable(false);

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

		Button register = new Button("Register");
		register.setFocusTraversable(false);
		TextField textfields[] = { emailtextfield, usernametextfield, passwordtextfield };
		register.setOnAction(e -> {
			boolean missingDetails = false;
			for (TextField tf : textfields) {
				if (tf.getText().equals("")) {
					missingDetails = true;
					break;
				}
			}
			if (missingDetails) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText("Please enter the email,username,password");
				alert.showAndWait();
			} else if (!emailtextfield.getText().matches("^\\S+@\\S+$")
					|| !usernametextfield.getText().matches("^[a-zA-Z]+$")
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
			} else if (passwordtextfield.getText().length() < 8) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);

				// Set the title of the Alert
				alert.setTitle("Error Dialog");

				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");

				// Set the content text of the Alert
				alert.setContentText("Please enter a password at least of 8 characters");
				alert.showAndWait();
			} else {
				// Encrypt the password
				String enteredpassword = passwordtextfield.getText();

				try {
					md = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				byte[] hash = md.digest(enteredpassword.getBytes());
				StringBuilder sb = new StringBuilder();
				for (byte b : hash) {
					sb.append(String.format("%02x", b));
				}
				String encryptedPassword = sb.toString();

				try {
					boolean isUserAdded = addUser(emailtextfield.getText(), usernametextfield.getText(),
							encryptedPassword, emailtextfield, usernametextfield,
							passwordtextfield);
					if (isUserAdded) {
						// Show confirmation alert
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Confirmation");
						alert.setHeaderText("A Confirmation has occured");
						alert.setContentText("Welcome you registered successfully");
						alert.showAndWait();

						// Navigate to the login page
						LoginScreen login = new LoginScreen();
						try {
							login.start(arg0);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						// Do nothing, stay on the registration page
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} finally {

				}
			}

		});

		gridpane.setVgap(10);
		gridpane.setHgap(10);
		gridpane.setPadding(new Insets(20));
		gridpane.add(Registertext, 0, 1);
		gridpane.add(email, 0, 2);
		gridpane.add(emailtextfield, 1, 2);
		gridpane.add(username, 0, 3);
		gridpane.add(usernametextfield, 1, 3);
		gridpane.add(password, 0, 4);
		gridpane.add(textField, 1, 4);
		textField.setVisible(false);
		gridpane.add(passwordtextfield, 1, 4);
		gridpane.add(toggleButton, 2, 4);
		gridpane.add(register, 0, 6);

		Scene scene = new Scene(gridpane);
		arg0.setTitle("Signup Page");
		arg0.setScene(scene);
		arg0.show();
	}

	public boolean addUser(String email, String username, String password,TextField emailField,
			TextField usernameField, TextField passwordField) throws SQLException {
		if (username == null || password == null || email == null) {
			return false;
		} else {
			String query = "SELECT * FROM signup WHERE Email = ? OR Username = ? OR Password = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				if (email.equals(rset.getString("Email"))) {
					emailField.setStyle("-fx-background-color: red;");
					showAlert("Email already exists");
				}
				if (username.equals(rset.getString("Username"))) {
					usernameField.setStyle("-fx-background-color: red;");
					showAlert("Username already exists");
				}
				if (password.equals(rset.getString("Password"))) {
					passwordField.setStyle("-fx-background-color: red;");
					showAlert("Password already exists");
				}
				return false;
			} else {
				query = "INSERT INTO signup (Email, Username, Password,isNewUser) VALUES (?, ?, ?, ?, ?)";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, email);
				pstmt.setString(2, username);
				pstmt.setString(3, password);
				pstmt.setBoolean(4, true); // Set isNewUser to true
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					emailField.setStyle("-fx-background-color: green;");
					usernameField.setStyle("-fx-background-color: green;");
					passwordField.setStyle("-fx-background-color: green;");
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
