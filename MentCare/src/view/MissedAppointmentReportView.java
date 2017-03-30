package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


import controller.BusinessManagerController;
import controller.ViewMenuController;
import javafx.application.Application;
import javafx.geometry.Insets;
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

	static private String countmissedquery = "SELECT COUNT(*) FROM mentcare.Missed_Appointment WHERE missed = 1 AND mentcare.Missed_Appointment.apdate = CURDATE()";
	static private String getabsenteeinfo = "SELECT Fname, LNAME, Phone_Number FROM mentcare.Patient_Info, mentcare.Missed_Appointment WHERE mentcare.Missed_Appointment.PNum = mentcare.Patient_Info.PNumber AND mentcare.Missed_Appointment.missed = 1 AND mentcare.Missed_Appointment.apDate = CURDATE();";
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
		
		report.setStyle("-fx-font-weight: bold");
		report.setPadding(new Insets(0, 0, 0, 220));
		
		tMissed.setStyle("-fx-font-weight: bold");
		tMissed.setPadding(new Insets(0, 10, 10, 10));
		
		fnames.setStyle("-fx-font-weight: bold");
		fnames.setPadding(new Insets(0, 10, 10, 10));
		
		lnames.setStyle("-fx-font-weight: bold");
		lnames.setPadding(new Insets(0, 10, 10, 10));
		
		phones.setStyle("-fx-font-weight: bold");
		phones.setPadding(new Insets(0, 10, 10, 10));
		
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
		
		//Fetch info from Database
		Collection<String> FirstNames = new ArrayList<>();
		Collection<String> LastNames = new ArrayList<>();
		Collection<String> PhoneNumbers = new ArrayList<>();
		
		
		PreparedStatement pstmt;
		int totalabsent = 0;
		
		try {
			pstmt = ViewMenuController.con.prepareStatement(countmissedquery);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				totalabsent = rs.getInt(1);
			}
			
			pstmt = ViewMenuController.con.prepareStatement(getabsenteeinfo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				FirstNames.add(rs.getString("Fname"));
				LastNames.add(rs.getString("LNAME"));
				PhoneNumbers.add(rs.getString("Phone_Number"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Add info from database to report
		for(String s : FirstNames){
			Label l = new Label(s);
			l.setPadding(new Insets(0, 0, 0, 60));
			Fnames.getChildren().add(l);
		}
		
		for(String s : LastNames){
			Label l = new Label(s);
			l.setPadding(new Insets(0, 0, 0, 60));
			Lnames.getChildren().add(l);
		}
		
		for(String s : PhoneNumbers){
			Label l = new Label(s);
			l.setPadding(new Insets(0, 0, 0, 30));
			PhoneNums.getChildren().add(l);
		}
		
		
		
		Label absentcount = new Label(Integer.toString(totalabsent));
		absentcount.setPadding(new Insets(0, 0, 0, 70));
		
		totalMissed.getChildren().add(absentcount);
		
		
		
		MissedAppointmentLayout.getChildren().addAll(totalMissed, Fnames, Lnames, PhoneNums);
		
		mainLayout.setPadding(new Insets(10, 10, 10, 10));
		mainLayout.setTop(report);
		mainLayout.setCenter(MissedAppointmentLayout);
		mainLayout.setBottom(backbutton);
		
		Scene MissedReport = new Scene(mainLayout, 700, 600);
		
		window.setScene(MissedReport);
		

	}


}
