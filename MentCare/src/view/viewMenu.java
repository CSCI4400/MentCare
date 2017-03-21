package view;

import javafx.application.*;
import javafx.stage.*;
import model.InitialDBConnection;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.geometry.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class viewMenu extends Application {
	Stage window;
	
	Button okbutton;
	Button cancelbutton;
	Button docView, recepView, bizView;
	public static Connection con;
	
	public static void main(String args[]){
		
		//Connect to the database
		InitialDBConnection idb = new InitialDBConnection();
		Thread t = new Thread(idb);
		t.start();
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception{
		
		window = primaryStage;
		window.setTitle("Mentcare");
		docView = new Button("Doctor View");
		recepView = new Button("Receptionist View");
		bizView = new Button("Business Manager View");
		doctorview dView = new doctorview(); //declare an instance of the Doctor View
		ReceptionistView rView = new ReceptionistView();
		BusinessManagerView bView = new BusinessManagerView();
		docView.setOnAction(e -> {
			try {
				dView.start(window);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}); //switches to the Doctor View
		
		recepView.setOnAction(e -> {
				try {
					rView.start(window);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		});
		
		bizView.setOnAction(e -> {
			try {
				bView.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
		StackPane layout = new StackPane();
		layout.setPadding(new Insets(5));
		layout.getChildren().addAll(docView, recepView, bizView);
		layout.setAlignment(docView, Pos.TOP_CENTER);
		layout.setAlignment(recepView, Pos.CENTER);
		layout.setAlignment(bizView, Pos.BOTTOM_CENTER);
		Scene scene = new Scene(layout, 250, 250);
		window.setScene(scene);
		window.show();
		
	}


}
