package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import controller.DoctorViewController;
import controller.PatientDAO;
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
	
	public static void DiagnosisHistory(Patient a, Button backbutton, Stage window){
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
		try {
			pstmt = ViewMenuController.con.prepareStatement(selhistory);
			pstmt.setInt(1, a.getPatientnum());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Diagnoses.add(rs.getString("Diagnosis"));
				DoctorNames.add(rs.getString("Name_of_doctor"));
				DatesofD.add(rs.getDate("Date_of_diag").toString());
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
		
	    backbutton.setOnAction(e-> {
	    	PatientDAO.updatePatientInfo(a);
	    	DoctorViewController.patientrecords(a);
	    });
	    Diagnosis.getChildren().add(backbutton);
	    
	    DiagHistLayout.getChildren().addAll(Diagnosis, DocWhoDiagnosed, DateOfDiagnosis, DiagnIsTemp);
		
		Scene diaghistview = new Scene(DiagHistLayout, 480, 480);
		
		window.setScene(diaghistview);
	}

}
