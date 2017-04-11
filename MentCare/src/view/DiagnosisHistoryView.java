package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import application.MainFXApp;
import controller.PatientDAO;
import controller.PatientRecordsController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;

public class DiagnosisHistoryView {

	static Button backbutton = new Button("Back");
	static Button deleteTemp = new Button("Delete Temporary Diagnoses");
	static Button deleteExpired = new Button("Delete Expired Diagnoses");
	static String deleteTempDiagn = "DELETE FROM mentcare.Diagnosis_History WHERE mentcare.Diagnosis_History.Diagnosis_is_temp = 1";
	static String deleteExpiredDiagn = "DELETE FROM mentcare.Diagnosis_History WHERE Diagnosis_is_temp = 1 AND DATEDIFF(CURDATE(), Date_of_diag) > 14";
	static String mostRecentDiagnQuery = "SELECT Diagnosis FROM mentcare.Diagnosis_History WHERE PNum = ?";
	static String mostRecentDiagnosis = "";
	static String resetCurrentDiagn = "UPDATE mentcare.Patient_Info SET mentcare.Patient_Info.Diagnosis = ? WHERE mentcare.Patient_Info.PNumber = ? ";

	public static void DiagnosisHistory(Patient a, Stage window){

		backbutton.setFont(Font.font("Georgia", 15));
		deleteTemp.setFont(Font.font("Georgia", 15));
		deleteExpired.setFont(Font.font("Georgia", 15));


		//This method controls the view for the Diagnosis History view.
		//Since the view is dynamic and the number of labels is not known in advance,
		//it is not an FXML file
		GridPane DiagHistLayout = new GridPane();
		VBox Diagnosis = new VBox();
		VBox DocWhoDiagnosed = new VBox();
		VBox DateOfDiagnosis = new VBox();
		VBox DiagnIsTemp = new VBox();


		//label for Diagnosis column
		Diagnosis.setPadding(new Insets(15, 12, 15, 12));
		Diagnosis.setSpacing(10);
		Text t1 = new Text("Diagnosis: ");
		t1.setFont(Font.font("Georgia", 15));
		Diagnosis.getChildren().add(t1);

		//label for Doctor Who Diagnosed column
		DocWhoDiagnosed.setPadding(new Insets(15, 12, 15, 12));
		DocWhoDiagnosed.setSpacing(10);
		Text t2 = new Text("Doctor Who Diagnosed: ");
		t2.setFont(Font.font("Georgia", 15));
		DocWhoDiagnosed.getChildren().add(t2);


		//Label for Date of Diagnosis column
		DateOfDiagnosis.setPadding(new Insets(15, 12, 15, 12));
		DateOfDiagnosis.setSpacing(10);
		Text t3 = new Text("Date of Diagnosis: ");
		t3.setFont(Font.font("Georgia", 15));
		DateOfDiagnosis.getChildren().add(t3);

		DiagnIsTemp.setPadding(new Insets(15, 12, 15, 12));
		DiagnIsTemp.setSpacing(10);;
		Text t4 = new Text("Diagnosis is Temporary: ");
		t4.setFont(Font.font("Georgia", 15));
		DiagnIsTemp.getChildren().add(t4);

		//Sets up layout for columns
		GridPane.setRowIndex(Diagnosis, 0);
		GridPane.setColumnIndex(Diagnosis, 0);
		GridPane.setRowIndex(DocWhoDiagnosed, 0);
		GridPane.setColumnIndex(DocWhoDiagnosed, 1);
		GridPane.setRowIndex(DateOfDiagnosis, 0);
		GridPane.setColumnIndex(DateOfDiagnosis, 2);
		GridPane.setRowIndex(DiagnIsTemp, 0);
		GridPane.setColumnIndex(DiagnIsTemp, 3);


		//Query to get the diagnosis history from the database
		String selhistory = "SELECT * FROM mentcare.Diagnosis_History WHERE ? = mentcare.Diagnosis_History.PNum";

		PreparedStatement pstmt;
		//Array Lists for holding the lists of info for each column
		Collection<String> Diagnoses = new ArrayList<>();
		Collection<String> DoctorNames = new ArrayList<>();
		Collection<String> DatesofD = new ArrayList<>();
		Collection<Integer> TemporaryStatus = new ArrayList<>();
		try {
			pstmt = MainFXApp.con.prepareStatement(selhistory);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Diagnoses.add(rs.getString("Diagnosis"));
				DoctorNames.add(rs.getString("Name_of_doctor"));
				DatesofD.add(rs.getDate("Date_of_diag").toString());
				TemporaryStatus.add(rs.getInt("Diagnosis_is_temp"));
			}
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(String s : Diagnoses){
			//adds labels for each diagnosis entry in the database
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			Diagnosis.getChildren().add(l);
		}

		for(String s: DoctorNames){
			//adds labels for each doctor who diagnosed entry in the database
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			DocWhoDiagnosed.getChildren().add(l);
		}

		for(String s: DatesofD){
			//adds labels for each date of diagnosis entry in the database
			Label l = new Label(s);
			l.setFont(Font.font("Georgia", 15));
			DateOfDiagnosis.getChildren().add(l);
		}

		for(Integer i: TemporaryStatus){
			//adds labels to indicate if a diagnosis is temporary
			if(i == 0){
				Label l = new Label("No");
				l.setFont(Font.font("Georgia", 15));
				DiagnIsTemp.getChildren().add(l);
			}
			else if(i == 1){
				Label l = new Label("Yes");
				l.setFont(Font.font("Georgia", 15));
				DiagnIsTemp.getChildren().add(l);
			}
		}

	    backbutton.setOnAction(e-> {
	    	//Back button returns to the patient records view for a doctor.
	    	PatientRecordsController.ViewPatientRecordsDoc(a, window);

	    });

	    //Setting a minimum width to ensure readability
	    deleteTemp.setMinWidth(250);

	    deleteTemp.setOnAction(e ->{
	    	PreparedStatement prepstmt;
	    	try {
	    		//Executes the query that deletes all temporary diagnoses for the current patient
				prepstmt = MainFXApp.con.prepareStatement(deleteTempDiagn);
				prepstmt.execute();
				//Resets the diagnosis for the patient to the last non-temporary diagnosis
				//Gets the last diagnosis
				prepstmt = MainFXApp.con.prepareStatement(mostRecentDiagnQuery);
				prepstmt.setInt(1, a.getPatientnum());
				ResultSet result = prepstmt.executeQuery();
				while(result.next()){
					mostRecentDiagnosis = result.getString("Diagnosis");
				}

				//Resets the diagnosis for the patient to the last non-temp diagnosis
				prepstmt = MainFXApp.con.prepareStatement(resetCurrentDiagn);
				prepstmt.setString(1, mostRecentDiagnosis);
				prepstmt.setInt(2, a.getPatientnum());
				prepstmt.execute();
				result.close();
				prepstmt.close();
				//Updates the current patient object to reflect the new state of the database
				a.setDiagnosis(mostRecentDiagnosis);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	PatientRecordsController.ViewPatientRecordsDoc(a, window);

	    });

	    deleteExpired.setMinWidth(250);

	    deleteExpired.setOnAction(e ->{
	    	PreparedStatement prepstmt;
	    	try{
	    		prepstmt = MainFXApp.con.prepareStatement(deleteExpiredDiagn);
	    		prepstmt.execute();
	    		prepstmt = MainFXApp.con.prepareStatement(mostRecentDiagnQuery);
				prepstmt.setInt(1, a.getPatientnum());
				ResultSet result = prepstmt.executeQuery();
				while(result.next()){
					mostRecentDiagnosis = result.getString("Diagnosis");
				}

				prepstmt = MainFXApp.con.prepareStatement(resetCurrentDiagn);
				prepstmt.setString(1, mostRecentDiagnosis);
				prepstmt.setInt(2, a.getPatientnum());
				prepstmt.execute();
				result.close();
				prepstmt.close();
				a.setDiagnosis(mostRecentDiagnosis);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	PatientRecordsController.ViewPatientRecordsDoc(a, window);
	    });

	    Diagnosis.getChildren().addAll(deleteTemp, deleteExpired, backbutton);

	    DiagHistLayout.getChildren().addAll(Diagnosis, DocWhoDiagnosed, DateOfDiagnosis, DiagnIsTemp);

		Scene diaghistview = new Scene(DiagHistLayout, 900, 520);

		window.setScene(diaghistview);
	}

}
