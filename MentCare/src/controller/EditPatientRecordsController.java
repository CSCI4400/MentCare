package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import application.MainFXApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;

public class EditPatientRecordsController {

    final static ScrollPane sp = new ScrollPane();

	static Button backbutton = new Button("Back");
	static Button updatebutton = new Button("Update");
	static Label deathlabel = new Label("Is the patient deceased?");
	static RadioButton yesdead = new RadioButton("Yes");
	static RadioButton nodead = new RadioButton("No");

	public static Text firstnamel = new Text("First Name:");
	public static Text lastnamel = new Text("Last name:");
	public static Text birthdatel = new Text("Birthdate:");
	public static Text homeaddressl = new Text("Home Address");
	public static Text genderl = new Text("Gender:");
	public static Text phonenumberl = new Text("Phone Number:");
	public static Text diagnosisl = new Text("Diagnosis:");
	public static Text ssnl = new Text("SSN: ");
	public static Text lastvisitl = new Text("Last Visit Was: ");
	public static Text patient_iss = new Text ("Patient Issue Reported: ");
	public static Text threat_level = new Text ("Threat Issue: ");


	static CheckBox tempDiagnosis = new CheckBox("Diagnosis is temporary");
	//Query to check if the patient is dead
	static String deathCheck = "SELECT mentcare2.Personal_Info.Dead FROM mentcare2.Personal_Info WHERE mentcare2.Personal_Info.PNumber = ?";
	//Query to set a patient as dead in the database
	static String setDead = "UPDATE mentcare2.Personal_Info SET mentcare2.Personal_Info.Dead = 'yes' WHERE PNumber = ? ";

	public static void DocEditPatientRecords(Patient a, Stage window){//This method controls
		//editing a patient as a Doctor. This means that medical info is editable.
		VBox layout4 = new VBox (12);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());
		TextField patient_issue = new TextField(a.getPatient_issue());
		TextField threat_lvl = new TextField(a.getThreat_level());


		//Setting font for buttons
		backbutton.setFont(Font.font("Georgia", 12));
		updatebutton.setFont(Font.font("Georgia", 12));
		deathlabel.setFont(Font.font("Georgia", 12));
		yesdead.setFont(Font.font("Georgia", 12));
		nodead.setFont(Font.font("Georgia", 12));

		//Setting up fonts for labels
		firstnamel.setFont(Font.font("Georgia", 12));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 12));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 12));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 12));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 12));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 12));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 12));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 12));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 12));
		lastvisitl.setStyle("-fx-font-weight: bold");
		patient_iss.setFont(Font.font("Georgia", 12));
		patient_iss.setStyle("-fx-font-weight: bold");
		threat_level.setFont(Font.font("Georgia", 12));
		threat_level.setStyle("-fx-font-weight: bold");

		//Setting up fonts for patient information
		fname.setFont(Font.font("Georgia", 12));
		lname.setFont(Font.font("Georgia", 12));
		birthdate.setFont(Font.font("Georgia", 12));
		addr.setFont(Font.font("Georgia", 12));
		sex.setFont(Font.font("Georgia", 12));
		phonenum.setFont(Font.font("Georgia", 12));
		social.setFont(Font.font("Georgia", 12));
		lastapt.setFont(Font.font("Georgia", 12));
		diago.setFont(Font.font("Georgia", 12));
		patient_issue.setFont(Font.font("Georgia", 12));
		threat_lvl.setFont(Font.font("Georgia", 12));

		//Sets up a ToggleGroup so that the yes and no buttons for indicating if a patient is dead
		//are mutually exclusive
		final ToggleGroup deathSelect = new ToggleGroup();
		yesdead.setToggleGroup(deathSelect);
		nodead.setToggleGroup(deathSelect);
		nodead.setSelected(true);

		try {
			//Queries the database to see if a patient is dead
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					//disables update button for a dead patient
					updatebutton.setDisable(true);
				}
				else if(rs.getString("Dead").equals("no")){
					//enables update button if the patient is alive.
					//Necessary to re-enable update button in case last patient viewed was dead.
					updatebutton.setDisable(false);
				}
			}

			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		updatebutton.setOnAction( e -> {
			if(yesdead.isSelected()){
				try {
					//Executes the query to set a patient as dead in the database
					PreparedStatement pstmt = MainFXApp.con.prepareStatement(setDead);
					pstmt.setInt(1, a.getPatientnum());
					pstmt.execute();
					pstmt.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else if(nodead.isSelected()){
				//Performs a regular update indicating that the patient is still alive

				//Updates the patient object first
				a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum(), a.getPatient_issue(), a.getThreat_level());
				if(tempDiagnosis.isSelected()){
					//Updates the patient record in the database with a temporary diagnosis.
					//The second parameter is an int that controls this ( 1 = temp)
					PatientDAO.updatePatientInfo(a, 1);
			}
				else if(!tempDiagnosis.isSelected()){
					//Updates the patient record in the database with a non-temp diagnosis.
					//The second parameter is an int that controls this ( 0 = nontemp)
					PatientDAO.updatePatientInfo(a, 0);
			}
			}

			//Returns to the view patient screen for a doctor
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
			});

		backbutton.setOnAction(e-> {
			//The back button returns to the view patient screen for a doctor
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
		});



		//Adds labels to the scene
		layout4.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, diagnosisl, diago , tempDiagnosis, ssnl, social, lastvisitl, lastapt, patient_iss, patient_issue, threat_level, threat_lvl, deathlabel, yesdead, nodead, updatebutton, backbutton);


		Scene recordeditor = new Scene(layout4, 680, 950);

		recordeditor.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());

		window.setScene(recordeditor);
	}

	public static void RecepEditPatientRecords(Patient a, Stage window){
		//Editing a patient as a receptionist. This means no medical info is changed.
		HBox hb1 = new HBox(12);
		HBox hb2 = new HBox(12);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField patient_issue = new TextField(a.getPatient_issue());
		TextField threat_lvl = new TextField(a.getThreat_level());


		//Setting font for buttons
		backbutton.setFont(Font.font("Georgia", 12));
		updatebutton.setFont(Font.font("Georgia", 12));
		deathlabel.setFont(Font.font("Georgia", 12));
		yesdead.setFont(Font.font("Georgia", 12));
		nodead.setFont(Font.font("Georgia", 12));

		//Setting up fonts for labels
		firstnamel.setFont(Font.font("Georgia", 12));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 12));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 12));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 12));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 12));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 12));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 12));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 12));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 12));
		lastvisitl.setStyle("-fx-font-weight: bold");
		patient_iss.setFont(Font.font("Georgia", 12));
		patient_iss.setStyle("-fx-font-weight: bold");
		threat_level.setFont(Font.font("Georgia", 12));
		threat_level.setStyle("-fx-font-weight: bold");

		//Setting up fonts for patient information
				fname.setFont(Font.font("Georgia", 12));
				lname.setFont(Font.font("Georgia", 12));
				birthdate.setFont(Font.font("Georgia", 12));
				addr.setFont(Font.font("Georgia", 12));
				sex.setFont(Font.font("Georgia", 12));
				phonenum.setFont(Font.font("Georgia", 12));
				patient_issue.setFont(Font.font("Georgia", 12));
				threat_lvl.setFont(Font.font("Georgia", 12));
				lastapt.setFont(Font.font("Georgia", 12));
		try {
			//Check to see if the patient is dead
			PreparedStatement pstmt = MainFXApp.con.prepareStatement(deathCheck);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getString("Dead").equals("yes")){
					//disables update button for a dead patient
					updatebutton.setDisable(true);
				}
				else if(rs.getString("Dead").equals("no")){
					//enables update button if the patient is alive.
					//Necessary to re-enable update button in case last patient viewed was dead.
					updatebutton.setDisable(false);
				}
			}

			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		updatebutton.setOnAction( e -> {
			//Updates the patient object first
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), a.getSsn(), LocalDate.parse(lastapt.getText()), a.getDiagnosis(), a.getPatientnum(), a.getPatient_issue(), a.getThreat_level());

			//Calls the method to update a patient record in the database as a receptionist
			PatientDAO.updatePatientInfo(a, 0);
			//Returns to the view patient records screen for a receptionist
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
	});


		backbutton.setOnAction(e-> {
			//Back button returns to the view patient records screen for a receptionist
			PatientRecordsController.ViewPatientRecordsRecep(a, window);
		});

		//Adds labels to view
		hb1.getChildren().addAll(genderl, sex, phonenumberl, phonenum, lastvisitl, lastapt,patient_iss, patient_issue, threat_level, threat_lvl, updatebutton, backbutton);
		hb2.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr);
		ScrollPane pane = new ScrollPane();
		pane.getChildrenUnmodifiable().addAll(hb1,hb2);

		Scene recordeditor = new Scene(pane, 680, 750);

		recordeditor.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());

		window.setScene(recordeditor);

	}

}
