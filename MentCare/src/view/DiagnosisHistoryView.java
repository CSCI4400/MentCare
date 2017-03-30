package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import controller.PatientDAO;
import controller.PatientRecordsController;
import controller.ViewMenuController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;

public class DiagnosisHistoryView {
	
	static Button backbutton = new Button("Back");
	static Button deleteTemp = new Button("Delete Temporary Diagnoses");
	static String deleteTempDiagn = "DELETE FROM mentcare.Diagnosis_History WHERE mentcare.Diagnosis_History.Diagnosis_is_temp = 1";
	static String mostRecentDiagnQuery = "SELECT Diagnosis FROM mentcare.Diagnosis_History WHERE PNum = ?";
	static String mostRecentDiagnosis = "";
	static String resetCurrentDiagn = "UPDATE mentcare.Patient_Info SET mentcare.Patient_Info.Diagnosis = ? WHERE mentcare.Patient_Info.PNumber = ? ";
	
	public static void DiagnosisHistory(Patient a, Stage window){
		GridPane DiagHistLayout = new GridPane();
		VBox Diagnosis = new VBox();
		VBox DocWhoDiagnosed = new VBox();
		VBox DateOfDiagnosis = new VBox();
		VBox DiagnIsTemp = new VBox();
		
		Diagnosis.setPadding(new Insets(15, 12, 15, 12));
		Diagnosis.setSpacing(10);
		Text t1 = new Text("Diagnosis: ");
		Diagnosis.getChildren().add(t1);
		
		DocWhoDiagnosed.setPadding(new Insets(15, 12, 15, 12));
		DocWhoDiagnosed.setSpacing(10);
		Text t2 = new Text("Doctor Who Diagnosed: ");
		DocWhoDiagnosed.getChildren().add(t2);
		
		
		DateOfDiagnosis.setPadding(new Insets(15, 12, 15, 12));
		DateOfDiagnosis.setSpacing(10);
		Text t3 = new Text("Date of Diagnosis: ");
		DateOfDiagnosis.getChildren().add(t3);
		
		DiagnIsTemp.setPadding(new Insets(15, 12, 15, 12));
		DiagnIsTemp.setSpacing(10);;
		Text t4 = new Text("Diagnosis is Temporary: ");
		DiagnIsTemp.getChildren().add(t4);
		
		GridPane.setRowIndex(Diagnosis, 0);
		GridPane.setColumnIndex(Diagnosis, 0);
		GridPane.setRowIndex(DocWhoDiagnosed, 0);
		GridPane.setColumnIndex(DocWhoDiagnosed, 1);
		GridPane.setRowIndex(DateOfDiagnosis, 0);
		GridPane.setColumnIndex(DateOfDiagnosis, 2);
		GridPane.setRowIndex(DiagnIsTemp, 0);
		GridPane.setColumnIndex(DiagnIsTemp, 3);
		
		
		String selhistory = "SELECT * FROM mentcare.Diagnosis_History WHERE ? = mentcare.Diagnosis_History.PNum";
		
		PreparedStatement pstmt;
		Collection<String> Diagnoses = new ArrayList<>();
		Collection<String> DoctorNames = new ArrayList<>();
		Collection<String> DatesofD = new ArrayList<>();
		Collection<Integer> TemporaryStatus = new ArrayList<>();
		try {
			pstmt = ViewMenuController.con.prepareStatement(selhistory);
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
			Label l = new Label(s);
			Diagnosis.getChildren().add(l);
		}
		
		for(String s: DoctorNames){
			Label l = new Label(s);
			DocWhoDiagnosed.getChildren().add(l);
		}
		
		for(String s: DatesofD){
			Label l = new Label(s);
			DateOfDiagnosis.getChildren().add(l);
		}
		
		for(Integer i: TemporaryStatus){
			if(i == 0){
				Label l = new Label("No");
				DiagnIsTemp.getChildren().add(l);
			}
			else if(i == 1){
				Label l = new Label("Yes");
				DiagnIsTemp.getChildren().add(l);
			}
		}
		
	    backbutton.setOnAction(e-> {
	    	//PatientDAO.updatePatientInfo(a, 0);
	    	PatientRecordsController.ViewPatientRecordsDoc(a, window);
	    	
	    });
	    
	    deleteTemp.setMinWidth(250);
	    
	    deleteTemp.setOnAction(e ->{
	    	PreparedStatement prepstmt; 
	    	try {
				prepstmt = ViewMenuController.con.prepareStatement(deleteTempDiagn);
				prepstmt.execute();
				prepstmt = ViewMenuController.con.prepareStatement(mostRecentDiagnQuery);
				prepstmt.setInt(1, a.getPatientnum());
				ResultSet result = prepstmt.executeQuery();
				while(result.next()){
					mostRecentDiagnosis = result.getString("Diagnosis");
				}
				
				prepstmt = ViewMenuController.con.prepareStatement(resetCurrentDiagn);
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
	    Diagnosis.getChildren().addAll(deleteTemp, backbutton);
	    
	    DiagHistLayout.getChildren().addAll(Diagnosis, DocWhoDiagnosed, DateOfDiagnosis, DiagnIsTemp);
		
		Scene diaghistview = new Scene(DiagHistLayout, 700, 520);
		
		window.setScene(diaghistview);
	}

}
