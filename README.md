## FOR MENU TAB INTEGRATION

  2 - Add your main view fxml to this switch statement in MainViewController, follow the examples from appointments and patients
```Java
@FXML AnchorPane apBusiness = new AnchorPane();
@FXML AnchorPane apUser = new AnchorPane();
@FXML AnchorPane apAppointments = new AnchorPane();
@FXML AnchorPane apPatients = new AnchorPane();
```
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
