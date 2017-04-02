## FOR MENU TAB INTEGRATION
  Add your main view fxml to this switch statement in MainViewController, follow the examples from appointments and patients
```Java
switch(call)
	  {
	  case "tbUsers":
		  // ADD MAIN USERS VIEW HERE
		  // USE APPOINTMENTS AS EXAMPLE
		  break;
	  case "tbAppointments":
		  toPane = getClass().getResource("/view/appointmentView.fxml");
	      	  temp = FXMLLoader.load(toPane);     
	      	  apAppointments.getChildren().setAll(temp);
		  break;
	  case "tbPatients":
		  toPane = getClass().getResource("/view/patientView.fxml");
	          temp = FXMLLoader.load(toPane);
	          apPatients.getChildren().setAll(temp);
		  break;
	  case "tbBusiness":
		  // ADD MAIN BUSINESS VIEW HERE
		  // USE BUSINESS AS EXAMPLE
		  break;
}
```
