package mentcare;

import javafx.scene.layout.VBox;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Stage is the entire application window
 * Scene is the area inside stage that has buttons, etc
 * @author besmi
 *
 */
public class doctorview extends Application{

	String placeholder = "Placeholder for: ";
	
	String welcomestring = "Welcome Doctor, " + "xyz";
	static String exitconfirmation = "Are you sure you wanted to exit?";
	
	String createappointmentstring = "Create appointment";
	String patientrecordsstring = "View/Edit patient records";
	String patientperscriptionsstring = "Patients perscriptions";
	String patientsheldstring = "Institutionalized Patients";
	String addpatientstring = "Click to add a patient";
	String logoutstring = "Log out";
	static String yes = "yes";
	static String no = "no";
	String ok = "ok";
	String cancel = "cancel";
	
	static Stage window;
	static Stage exitwindow;
	Scene mainmenu;
	Scene addpatient;
	Label welcome;
	static Label exitconfirmationlabel;
	Button createappointmentbutton;
	Button patientrecordsbutton;
	Button patientperscriptionsbutton;
	Button patientsheldbutton;
	Button addpatientbutton;
	Button logoutbutton;
	static Button yesbutton;
	static Button nobutton;
	Button okbutton;
	Button cancelbutton;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args); //sets up program as java fx, and calls start method
	}
	
	public void start(Stage primaryStage) throws Exception {
		//Adds buttons and labels
		window = primaryStage;
		welcome = new Label(welcomestring);
		window.setTitle("Program title");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});
		createappointmentbutton = new Button(createappointmentstring);
		patientrecordsbutton = new Button(patientrecordsstring);
		patientsheldbutton = new Button(patientsheldstring);
		addpatientbutton = new Button(addpatientstring);
		logoutbutton = new Button(logoutstring);
		
		
		//Configure button actions
		addpatientbutton.setOnAction(e -> window.setScene(addpatient));
		logoutbutton.setOnAction(e -> {
			logout();
			//return to main menu interface
		});
		
		
		//Configures layout
		VBox layout = new VBox(20);
		layout.getChildren().addAll(welcome, createappointmentbutton, addpatientbutton, patientrecordsbutton, patientsheldbutton, logoutbutton);
		
		//Display stage
		mainmenu = new Scene(layout, 640, 480);
		
		
		//Scene 2
		okbutton = new Button(ok);
		
		okbutton.setOnAction(e -> {
			if (addPatient()) {
				window.setScene(mainmenu);
				
			}
		});
		
		cancelbutton = new Button(cancel);
		
		cancelbutton.setOnAction(e -> window.setScene(mainmenu));
		
		HBox layout2 = new HBox(20);
		layout2.getChildren().addAll(okbutton, cancelbutton);
		addpatient = new Scene(layout2, 640, 480);
		
		window.setScene(mainmenu);
		window.show();
		
	}
	/**
	 * Called when user wants to add a patient to the system
	 * @return true if the patient was successfully added
	 */
	private static boolean addPatient() {
		//call interface to add patient
		return true;
	}
	/**
	 * Called when the user clicks the logout button
	 */
	private static void logout() {
		//log out of system here
		
		//Currently switches back to the viewMenu, this is only temporary
		try {
			viewMenu vMenu = new viewMenu();
			vMenu.start(window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Called when user has clicked upper right x button & confirms
	 */
	private static void confirmExit() {
		exitwindow = new Stage();
		
		exitwindow.initModality(Modality.APPLICATION_MODAL);
		exitwindow.setTitle(exitconfirmation);
		
		exitconfirmationlabel = new Label();
		exitconfirmationlabel.setText(exitconfirmation);
		yesbutton = new Button(yes);
		nobutton = new Button(no);
		
		yesbutton.setOnAction(e ->  {
			exitwindow.close();
			exitProgram();
		});
		nobutton.setOnAction(e -> exitwindow.close());
		
		VBox layout = new VBox(15);
		layout.getChildren().addAll(exitconfirmationlabel, yesbutton, nobutton);
		Scene exit = new Scene(layout, 200, 100);
		exitwindow.setScene(exit);
		exitwindow.show();
	}
	/**
	 * User confirms they want to close the program
	 */
	private static void exitProgram() {	
		window.close();
	}
}
