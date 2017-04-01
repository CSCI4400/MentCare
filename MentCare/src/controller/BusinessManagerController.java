package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.MissedAppointmentReportView;

public class BusinessManagerController extends Application {
	
	@FXML
    private Button LogOutButton;

    @FXML
    private Button MissedAppointmentButton;

    @FXML
    private Button StatButton;

    @FXML
    private Button ForecastButton;

    @FXML
    void LogOut(ActionEvent event) {
    	Node node = (Node) event.getSource();
    	Stage primaryStage = (Stage) node.getScene().getWindow();
    	ViewMenuController vmc = new ViewMenuController();
    	try {
			vmc.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void viewMissedReport(ActionEvent event) {
    	Node node = (Node) event.getSource();
    	Stage primaryStage = (Stage) node.getScene().getWindow();
    	try {
			MissedAppointmentReportView.MissedAppointmentReport(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void viewStatReport(ActionEvent event) {

    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try{
			GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("/view/BusinessManagerView.fxml"));
			Scene scene = new Scene(root,600,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}

}
