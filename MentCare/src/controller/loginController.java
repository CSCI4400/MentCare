package controller;
/*
 * @author Danni
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.DBConfig;
import application.MainFXApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {
	MainFXApp main = new MainFXApp();
	//needed for changing secne views
	Parent root;
	Stage stage;
	Scene scene;
	//connects to fxml file
	@FXML private Button btnLogIn;
	@FXML private TextField tfUserID;
	@FXML private PasswordField pfPassword;
	@FXML private Label lblErrUserID, lblErrPassword, lblErrLogIn;
	//sets main in Main.java 
	public void setMain(MainFXApp mainIn)
	{
		main = mainIn;
	}

	/*
	 * Listens for when logIn button is clicked. Checks entered user data with db data.
	 * Logs the user in if data entered matches the db data. Takes user to different GUIs depending on 
	 * their identication number -> who they are (receptionist, nurse, doctor, business manager)
	 */
	@FXML
	public void logIn (ActionEvent event) throws Exception{
		System.out.println("Attempting to log in...");
		//catches null pointer exception thrown when clearing labels
		try{
			lblErrUserID.setText(null);
			lblErrPassword.setText(null);
			lblErrLogIn.setText(null);
		}catch(NullPointerException ex){
			ex.getMessage();
		}
		//stores textfield data in variables
		String idNum = tfUserID.getText(), password = pfPassword.getText();
		try{
			//checks to see if the user is in the db
			if(checkLogIn(idNum, password)){
				//stores identifying number for a specific type in a variable
				String type = idNum.substring(0, 3);
				//determines which GUI to take the user to depending on their identification number
				if(type.equals("111")){//for receptionists
					stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
					scene = new Scene(root);
					stage.setScene(scene);
				}else if(type.equals("333") || type.equals("555")){//for nurses (333) and doctors (555)
					stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
					scene = new Scene(root);
					stage.setScene(scene);
				}else if(type.equals("777")){//for administrators
					stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
					scene = new Scene(root);
					stage.setScene(scene);
				}else{
					//wrong login type - does not exist
					lblErrLogIn.setText("Identification type not recognized.");
				}
			}else{
				//checks to see if an error has been thrown somewhere else already. If not, throws this error.
				if(lblErrUserID.getText().equals("") && lblErrPassword.getText().equals("") && 
						lblErrLogIn.getText().equals("")){
					//failed to log user into system - wrong credentials
					lblErrLogIn.setText("Invalid user ID or password.");
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
	}
	/*
	 * Checks entered user data identification number and password against the data for those 
	 * columns in the db. Returns true if the entered identification number and matching password
	 * are in the db in the same row. Returns false for everything else - includes error checking
	 */
	public boolean checkLogIn(String idNum, String password) throws SQLException{
		boolean logIn = false;
		boolean missing_credentials = false;
		//checks to see if both fields are empty. Throws an error if this is true.
		if(idNum.equals("") && password.equals("")){
			missing_credentials = true;
			lblErrLogIn.setText("Please fill in both fields.");
		}else if(idNum.equals("")){//checks for input in idenification number textfield. Returns an error if null
			missing_credentials = true;
			lblErrUserID.setText("User identification number required.");
		}else if(password.equals("")){//checks for input in password passwordfield. Returns an error if null
			missing_credentials = true;
			lblErrPassword.setText("Password is required.");
		}
		//breaks out of this method if the identification number, password, or both are missing
		if(missing_credentials){
			System.out.println("..failed.");
			return false;
		}
		try{
			System.out.println("..passed!");//passed error checking
			//connects to db
			Connection conn = DBConfig.getConnection();
			//query to pull data from idNum and password columns
			String check = "SELECT idNum, password FROM Users WHERE idNum = ? AND password = ?";
			//sends request
			PreparedStatement ps = conn.prepareStatement(check);
			ps.setString(1, idNum);
			ps.setString(2, password);
			//stores result from query
			ResultSet rs = ps.executeQuery();
			System.out.println("..connecting to db..");
			while(rs.next()){
				System.out.println("..pulling from db..");
				//sets db data into variables
				String user = rs.getString("idNum");
				String pass = rs.getString("password");
				System.out.println("User: " + user);
				System.out.println("Password: " + pass);
				//makes sure the variables are not empty before checking them against db content -> avoids NullPointerException
				if(!user.isEmpty() && !pass.isEmpty()){
					//checks identification number and password from db against local variables
					if(user.equals(idNum) && pass.equals(password)){
						logIn = true;//rs.next now equals false
						System.out.println("..successul!");
					}else{
						logIn = false;
					}
				}else{
					//displays an error
					lblErrLogIn.setText("User ID or password is empty.");
				}
			}
			if(logIn != true && rs.next() == false){//catches wrong input that is entered but not when login is found
				lblErrLogIn.setText("Invalid user ID or password.");
				logIn = false;
			}
		}catch(SQLException ex){
			DBConfig.displayException(ex);
			ex.printStackTrace();
		}
		return logIn;
	}
}
