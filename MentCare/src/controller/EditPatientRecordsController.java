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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;

public class EditPatientRecordsController {

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
	static CheckBox tempDiagnosis = new CheckBox("Diagnosis is temporary");
	//Query to check if the patient is dead
	static String deathCheck = "SELECT mentcare2.Personal_Info.Dead FROM mentcare2.Personal_Info WHERE mentcare2.Personal_Info.PNumber = ?";
	//Query to set a patient as dead in the database
	static String setDead = "UPDATE mentcare2.Personal_Info SET mentcare2.Personal_Info.Dead = 'yes' WHERE PNumber = ? ";

	public static void DocEditPatientRecords(Patient a, Stage window){//This method controls
		//editing a patient as a Doctor. This means that medical info is editable.
		VBox layout3 = new VBox(10);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());

		//Setting font for buttons
		backbutton.setFont(Font.font("Georgia", 13));
		updatebutton.setFont(Font.font("Georgia", 13));
		deathlabel.setFont(Font.font("Georgia", 13));
		yesdead.setFont(Font.font("Georgia", 13));
		nodead.setFont(Font.font("Georgia", 13));

		//Setting up fonts for labels
		firstnamel.setFont(Font.font("Georgia", 13));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 13));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 13));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 13));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 13));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 13));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 13));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 13));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 13));
		lastvisitl.setStyle("-fx-font-weight: bold");


		//Setting up fonts for patient information
		fname.setFont(Font.font("Georgia", 13));
		lname.setFont(Font.font("Georgia", 13));
		birthdate.setFont(Font.font("Georgia", 13));
		addr.setFont(Font.font("Georgia", 13));
		sex.setFont(Font.font("Georgia", 13));
		phonenum.setFont(Font.font("Georgia", 13));
		social.setFont(Font.font("Georgia", 13));
		lastapt.setFont(Font.font("Georgia", 13));
		diago.setFont(Font.font("Georgia", 13));

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
				//Validates the forms to ensure that proper formatting has been used
				if (validateForms(fname.getText(), lname.getText(), birthdate.getText(), sex.getText(), phonenum.getText(), lastapt.getText()))
					a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());
				else
					System.out.println("Invalid form input!");
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
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, diagnosisl, diago , tempDiagnosis, ssnl, social, lastvisitl, lastapt, deathlabel, yesdead, nodead, updatebutton, backbutton);


		ScrollPane scrollPane = new ScrollPane(layout3);
		scrollPane.setFitToHeight(true);

		Scene recordeditor = new Scene(scrollPane, 400, 500);

		recordeditor.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());


		window.setScene(recordeditor);
	}

	public static void RecepEditPatientRecords(Patient a, Stage window){
		//Editing a patient as a receptionist. This means no medical info is changed.
		VBox layout3 = new VBox(10);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField lastapt = new TextField((a.getLastVisit()).toString());


		//Setting font for buttons
		backbutton.setFont(Font.font("Georgia", 13));
		updatebutton.setFont(Font.font("Georgia", 13));
		deathlabel.setFont(Font.font("Georgia", 13));
		yesdead.setFont(Font.font("Georgia", 13));
		nodead.setFont(Font.font("Georgia", 13));

		//Setting up fonts for labels
		firstnamel.setFont(Font.font("Georgia", 13));
		firstnamel.setStyle("-fx-font-weight: bold");
		lastnamel.setFont(Font.font("Georgia", 13));
		lastnamel.setStyle("-fx-font-weight: bold");
		birthdatel.setFont(Font.font("Georgia", 13));
		birthdatel.setStyle("-fx-font-weight: bold");
		homeaddressl.setFont(Font.font("Georgia", 13));
		homeaddressl.setStyle("-fx-font-weight: bold");
		genderl.setFont(Font.font("Georgia", 13));
		genderl.setStyle("-fx-font-weight: bold");
		phonenumberl.setFont(Font.font("Georgia", 13));
		phonenumberl.setStyle("-fx-font-weight: bold");
		diagnosisl.setFont(Font.font("Georgia", 13));
		diagnosisl.setStyle("-fx-font-weight: bold");
		ssnl.setFont(Font.font("Georgia", 13));
		ssnl.setStyle("-fx-font-weight: bold");
		lastvisitl.setFont(Font.font("Georgia", 13));
		lastvisitl.setStyle("-fx-font-weight: bold");

		//Setting up fonts for patient information
				fname.setFont(Font.font("Georgia", 13));
				lname.setFont(Font.font("Georgia", 13));
				birthdate.setFont(Font.font("Georgia", 13));
				addr.setFont(Font.font("Georgia", 13));
				sex.setFont(Font.font("Georgia", 13));
				phonenum.setFont(Font.font("Georgia", 13));

				lastapt.setFont(Font.font("Georgia", 13));
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
			if (validateForms(fname.getText(), lname.getText(), birthdate.getText(), sex.getText(), phonenum.getText(), lastapt.getText()))
				a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), a.getSsn(), LocalDate.parse(lastapt.getText()), a.getDiagnosis(), a.getPatientnum());
			else
				System.out.println("Invalid form input!");

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
		layout3.getChildren().addAll(firstnamel, fname, lastnamel, lname, birthdatel, birthdate, homeaddressl, addr, genderl, sex, phonenumberl, phonenum, lastvisitl, lastapt, updatebutton, backbutton);


		ScrollPane scrollPane = new ScrollPane(layout3);
		scrollPane.setFitToHeight(true);

		Scene recordeditor = new Scene(scrollPane, 400, 500);

		recordeditor.getStylesheets().add(mainViewController.class.getResource("/application/application.css").toExternalForm());

		window.setScene(recordeditor);

	}
	//This method checks each form to determine whether the information is valid or not (Ex. putting a number in a person's name)
	static boolean validateForms(String fname, String lname, String bdate, String sex, String pnum, String lastAppt) {
		if (fname.matches("[a-zA-Z]+") && lname.matches("[a-zA-Z]+") && bdate.matches("[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-9][0-9]") && sex.matches("[M,F]") && pnum.matches("[0-9][0-9][0-9]-[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]") && lastAppt.matches("[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-9][0-9]"))
			return true;
		else
			return false;
	}

}
