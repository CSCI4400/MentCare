package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import application.MainFXApp;
import controller.mainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Patient;

public class MissedAppointmentReportView{

	static Button backbutton = new Button("Back");

	static private String countmissedquery = "SELECT COUNT(*) FROM mentcare2.Missed_Appointment WHERE missed = 1 AND mentcare2.Missed_Appointment.apdate = CURDATE()";
	static private String getabsenteeinfo = "SELECT Fname, LNAME, Phone_Number FROM mentcare2.Personal_Info, mentcare2.Missed_Appointment WHERE mentcare2.Missed_Appointment.PNum = mentcare2.Personal_Info.PNumber AND mentcare2.Missed_Appointment.missed = 1 AND mentcare2.Missed_Appointment.apDate = CURDATE();";
	static Label tMissed = new Label("Total Missed Appointments: ");
	static Label fnames = new Label("Absentee First Name: ");
	static Label lnames = new Label("Absentee Last Name: ");
	static Label phones = new Label("Absentee Phone Number: ");
	static Label report = new Label("Today's Missed Appointments");

	public static void MissedAppointmentReport(Stage window){
		//This is a report that counts the missed appointments for the day
		//It also lists the first name, last name, and phone number for the patients that missed

		backbutton.setFont(Font.font("Georgia", 15));
		tMissed.setFont(Font.font("Georgia", 15));
		fnames.setFont(Font.font("Georgia", 15));
		lnames.setFont(Font.font("Georgia", 15));
		phones.setFont(Font.font("Georgia", 15));
		report.setFont(Font.font("Georgia", 15));

		backbutton.setOnAction(e -> {
			try {
				AnchorPane mainv = (AnchorPane) FXMLLoader.load(mainViewController.class.getResource("/view/mainView.fxml"));
				Scene scene = new Scene(mainv,600,400);
				window.setScene(scene);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		//Setting up style for the column headers
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
			//Queries the database to count the number of missed appointments for today
			pstmt = MainFXApp.con.prepareStatement(countmissedquery);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				totalabsent = rs.getInt(1);
			}


			//Queries the database to get the first name, last name, and phone number of the
			//patients who had missed appointments today
			pstmt = MainFXApp.con.prepareStatement(getabsenteeinfo);
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
		//adds labels for the first name values
		for(String s : FirstNames){
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			l.setPadding(new Insets(0, 0, 0, 60));
			Fnames.getChildren().add(l);
		}

		//adds labels for the last name values
		for(String s : LastNames){
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			l.setPadding(new Insets(0, 0, 0, 60));
			Lnames.getChildren().add(l);
		}

		//adds labels for the phone number values
		for(String s : PhoneNumbers){
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			l.setPadding(new Insets(0, 0, 0, 30));
			PhoneNums.getChildren().add(l);
		}



		//Adds a label with the total number of absences
		Label absentcount = new Label(Integer.toString(totalabsent));
		absentcount.setFont(Font.font("Georgia", 15));
		absentcount.setPadding(new Insets(0, 0, 0, 70));

		totalMissed.getChildren().add(absentcount);



		MissedAppointmentLayout.getChildren().addAll(totalMissed, Fnames, Lnames, PhoneNums);

		mainLayout.setPadding(new Insets(10, 10, 10, 10));
		mainLayout.setTop(report);
		mainLayout.setCenter(MissedAppointmentLayout);
		mainLayout.setBottom(backbutton);

		Scene MissedReport = new Scene(mainLayout, 900, 600);
		
		MissedReport.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());

		window.setScene(MissedReport);


	}


}
