package view;

import controller.BusinessManagerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;

public class MissedAppointmentReportView{

	static Button backbutton = new Button("Back");

	static private String countmissedquery = "SELECT COUNT * FROM mentcare.Missed_Appointments WHERE missed = 1 AND date";
	static private String getnamesofabsentees = "SELECT FName, LNAME from";
	static Label tMissed = new Label("Total Missed Appointments: ");
	static Label fnames = new Label("Absentee First Name: ");
	static Label lnames = new Label("Absentee Last Name: ");
	static Label phones = new Label("Absentee Phone Number: ");
	static Label report = new Label("Today's Missed Appointments");

	public static void MissedAppointmentReport(Stage window){
		
		backbutton.setOnAction(e -> {
			BusinessManagerController bmc = new BusinessManagerController();
			try {
				bmc.start(window);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		BorderPane mainLayout = new BorderPane();
		GridPane MissedAppointmentLayout = new GridPane();
		VBox totalMissed = new VBox();
		VBox Fnames = new VBox();
		VBox Lnames = new VBox();
		VBox PhoneNums = new VBox();
		
		totalMissed.getChildren().add(tMissed);
		Fnames.getChildren().add(fnames);
		Lnames.getChildren().add(lnames);
		PhoneNums.getChildren().add(phones);
		
		GridPane.setRowIndex(totalMissed, 0);
		GridPane.setColumnIndex(totalMissed, 0);
		GridPane.setRowIndex(Fnames, 0);
		GridPane.setColumnIndex(Fnames, 1);
		GridPane.setRowIndex(Lnames, 0);
		GridPane.setColumnIndex(Lnames, 2);
		GridPane.setRowIndex(PhoneNums, 0);
		GridPane.setColumnIndex(PhoneNums, 3);
		
		MissedAppointmentLayout.getChildren().addAll(totalMissed, Fnames, Lnames, PhoneNums);
		
		mainLayout.setTop(report);
		mainLayout.setCenter(MissedAppointmentLayout);
		mainLayout.setBottom(backbutton);
		
		Scene MissedReport = new Scene(mainLayout, 600, 600);
		
		window.setScene(MissedReport);
		

	}


}
