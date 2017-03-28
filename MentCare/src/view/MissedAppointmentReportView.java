package view;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Patient;

public class MissedAppointmentReportView{

	Button backbutton = new Button("Back");

	private String countmissedquery = "SELECT COUNT * FROM mentcare.Missed_Appointments WHERE missed = 1 AND date";
	private String getnamesofabsentees = "SELECT FName, LNAME from";

	public static void MissedAppointmentReport(){
		GridPane MissedAppointmentLayout = new GridPane();

	}


}
