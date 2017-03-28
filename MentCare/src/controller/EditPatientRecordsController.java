package controller;

import java.time.LocalDate;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Patient;

public class EditPatientRecordsController {
	
	static Button backbutton = new Button("Back");
	static Button updatebutton = new Button("Update");

	public static void DocEditPatientRecords(Patient a, Stage window){
		VBox layout3 = new VBox(10);

		TextField fname = new TextField(a.getFirstname()); TextField lname = new TextField(a.getLastname());
		TextField birthdate = new TextField((a.getBirthdate()).toString()); TextField addr = new TextField(a.getAddress());
		TextField sex = new TextField(a.getGender()); TextField phonenum = new TextField(a.getPhoneNumber());
		TextField social = new TextField(a.getSsn()); TextField lastapt = new TextField((a.getLastVisit()).toString());
		TextField diago = new TextField(a.getDiagnosis());


		updatebutton.setOnAction( e -> {
			/* if(tempDiagnosis.isSelected()){ //Add code here for handling status of Diagnosis in database
			 * ifSelected will be true when checkbox is selected (Diagnosis is temporary) and false when
			 * checkbos is not selected (Diagnosis is not temporary)
			}*/
			a.updateRecord(fname.getText(), lname.getText(), LocalDate.parse(birthdate.getText()), addr.getText(), sex.getText(), phonenum.getText(), social.getText(), LocalDate.parse(lastapt.getText()), diago.getText(), a.getPatientnum());

			PatientDAO.updatePatientInfo(a);
			PatientRecordsController.ViewPatientRecordsDoc(a, window);
			});
	}
	
	public static void RecepEditPatientRecords(Patient a){
		
	}

}
