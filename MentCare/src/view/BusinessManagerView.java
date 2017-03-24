package view;

import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import controller.ViewMenuController;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.TimeoutTimer;
import javafx.scene.control.*;
import javafx.scene.text.*;

import javafx.animation.PauseTransition;

public class BusinessManagerView extends Application {
	
	static Stage window;
	static Button logoutButton = new Button("Log Out");
	
	static Stage exitwindow;
	static Scene mainmenu;
	static Scene addpatient;
	Label welcome;
	static Label exitconfirmationlabel;
	static Button logoutbutton = new Button("Log out");
	static Button yesbutton = new Button("yes");
	static Button nobutton = new Button("no");
	static Button backbutton = new Button("Back");
	static Button okbutton = new Button("ok");
	static Button cancelbutton = new Button("cancel");
	static Button viewReportsButton = new Button("View Current Reports");
	static Button viewClinicStatsButton = new Button("View Clinic Statistics");
	
	static String exitconfirmation = "Are you sure you wanted to exit?";

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		window = primaryStage;
		window.setTitle("Business Manager View");
		welcome = new Label("Welcome, Business Manager");
		window.setOnCloseRequest(e -> {
			e.consume();
			confirmExit();
		});
		
		logoutButton.setOnAction(e -> {
			ViewMenuController vm = new ViewMenuController();
			try {
				//vm.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(logoutButton, 0, 3);
		grid.add(welcome, 2, 0);
		grid.add(viewReportsButton, 2, 1);
		grid.add(viewClinicStatsButton, 2, 2);
		
		Scene scene1 = new Scene(grid, 400, 400);
		window.setScene(scene1);
		window.show();
		
		//Set page to time out after 10 seconds
		TimeoutTimer timeout = new TimeoutTimer(grid, window, 10); //This method is overloaded; if you only use two arguments the time defaults
		timeout.start();                                           //to 120 seconds. I'm using 10 seconds to make it easier to demo.
		
	}
	
	private static void confirmExit() {
		exitwindow = new Stage();
		
		exitwindow.initModality(Modality.APPLICATION_MODAL);
		exitwindow.setTitle(exitconfirmation);
		
		exitconfirmationlabel = new Label();
		exitconfirmationlabel.setText(exitconfirmation);
		
		yesbutton.setOnAction(e ->  {
			exitwindow.close();
			exitProgram();
		});
		nobutton.setOnAction(e -> exitwindow.close());
		BorderPane layout = new BorderPane();
		exitconfirmationlabel.setPadding(new Insets(0, 0, 10, 0));
		yesbutton.setPadding(new Insets(5, 30, 5, 30)); nobutton.setPadding(new Insets(5, 30, 5, 30));
		layout.setPadding(new Insets(0, 10, 10, 10));
		layout.setTop(exitconfirmationlabel);
		layout.setLeft(yesbutton);
		layout.setRight(nobutton);
		Scene exit = new Scene(layout, 250, 70);
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
