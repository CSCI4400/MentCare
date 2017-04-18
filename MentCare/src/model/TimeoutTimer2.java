package model;

import controller.loginController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.DBConfig;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

//-------------------------------------------------------------
//To use timer, insert the following code.
//
//		import model.TimeoutTimer;
//		
//		public void initialize(){
//			TimeoutTimer2 timer = new TimeoutTimer2("  Pane where you wish to track mouse movement  " , stage, 120);
//			timer.start();
//		}
//
//------------------------------------------------------------
public class TimeoutTimer2 {

	Pane pane;
	SplitPane splitPane;
	TabPane tabPane;
	Stage stage;
	Parent root;
	String pass = null;
	Connection conn = null;
	
	Point2D prevMousePos = new Point2D(0, 0);
	long timeOutDelay = 120 * 1000; //Default timeout - 120000 milliseconds or 120 seconds
	Timer timer;
	public TimeoutTimer2(Pane p, Stage s) {
		pane = p;
		stage = s;
	}
	
	public TimeoutTimer2(Pane p, Stage s, long TOD) {
		pane = p;
		stage = s;
		timeOutDelay = TOD * 1000; //Multiplies by 1000 to get milliseconds from seconds input
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public void start() {
		
		
		
		//-------------------------Uncomment this for use with login--------------------------------
	    //getPassword();
	    //------------------------------------------------------------------------------------------
	    
	    
	    
		ObjectProperty<Point2D> mouseLocation = new SimpleObjectProperty<>(new Point2D(0, 0)); //Create a container for the mouse position
		pane.setOnMouseMoved(e -> mouseLocation.set(new Point2D(e.getX(), e.getY()))); //Fill the container with the mouse position
		timer = new Timer(); //Create a new Timer 
		TimerTask task = new TimerTask() { //Creates a task to schedule after TOD
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(pane.isHover()){
							if (prevMousePos.getX() == mouseLocation.get().getX() &&
								prevMousePos.getY() == mouseLocation.get().getY()){ //If the previous mouse position is equal to the current and the pane is still visible, time out
									timer.cancel();
									enableBlur();
								} else { //Otherwise update the mouse location
									prevMousePos = mouseLocation.get();
								}
						}else if(!pane.isHover() && pane.isVisible()){
							timer.cancel();
							enableBlur();
						}
					}
				});
			}
		};
		timer.schedule(task ,timeOutDelay, timeOutDelay); //Schedules task after TOD and repeats it after TOD 
	}

	public void getPassword(){ //Checks the DB to retrieve the password of the currently logged in user
		try {
			conn = DBConfig.getConnection();
			
			String idNum = loginController.loggedOnUser.getID();
			String passwordCheck = "SELECT password FROM Users WHERE idNum = ?";
			
			PreparedStatement ps = conn.prepareStatement(passwordCheck);
			ps.setString(1, idNum);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				pass = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void enableBlur(){ //Blurs the pane to hide the content while user is timed out
		ColorAdjust adj = new ColorAdjust(0,0,0,0);
		GaussianBlur blur = new GaussianBlur(25);
		adj.setInput(blur);
		pane.setEffect(adj);
		timeoutInput();
	}
	
	int attempts = 0; //Attempt Counter
	public void timeoutInput(){ //Asks the user to reenter their password to stay logged in
		PasswordDialog pd = new PasswordDialog();
	    Optional<String> result = pd.showAndWait();
	    if(result.isPresent()){ //If user inputs correct password, timer is restarted and user remains logged in
	    	
	    	
	    	
	    	//-----------------------------------------------------
	    	pass = "";  //Remove to use with login
	    	//-----------------------------------------------------
	    	
	    	
	    	
	    	if(pd.getPasswordField().getText().equals(pass)){
	    		attempts = 0; //Resets the attempt count to 0
	    		pane.setEffect(null);
	    		start();  //Restarts timer
	    	}else{ //If user enters the wrong password
	    		if(attempts < 2){
	    			Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Incorrect Password!");
			    	alert.setHeaderText("You entered the Incorrect Password!");
			    	if(attempts != 1)
			    		alert.setContentText("Try Again. " + (2 - attempts) + " more attempts." );
			    	else
			    		alert.setContentText("Try Again. " + (2 - attempts) + " more attempt." );
			    	alert.showAndWait();
			    	attempts++;
			    	timeoutInput();
	    		} else{
	    			Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Incorrect Password!");
			    	alert.setHeaderText("You are being signed out.");
			    	alert.showAndWait();
			    	logOut();
	    		}
	    	}
	    }else{ //If user closes dialog or presses cancel, user is logged out
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Canceled");
	    	alert.setHeaderText("You closed the dialog!");
	    	alert.setContentText("You are being signed out.");
	    	alert.showAndWait();
	    	logOut();
	    }
	}
	
	public void logOut() { //Logs user out by returning to login
		try {
			root = (Parent) FXMLLoader.load(loginController.class.getResource("/view/loginView.fxml"));
			stage = (Stage) pane.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
