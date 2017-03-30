<<<<<<< HEAD
//edited by Anna, 3/25/17, added a button and link to addUser

package controller;

import application.MainFXApp;
=======
/*
 * @author Caleb
 * Description: Hub for menus
 */
package controller;

import java.io.IOException;

import java.net.URL;

import application.MainFXApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
>>>>>>> master
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.stage.Stage;

=======
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
>>>>>>> master
public class mainViewController {

	Stage stage;
	Scene scene;
	Parent root;
<<<<<<< HEAD
=======
	private static int numTab = 0;
	
>>>>>>> master
	//always reference main method, and build constructor
	private MainFXApp main;
	public void setMain(MainFXApp mainIn)
	{
<<<<<<< HEAD
		main = mainIn;
	}

	@FXML private Button displayButton;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button userBttn;

    @FXML
	void ClickButton(ActionEvent event) throws Exception {
    	try{
    	//finds what stage the button is in
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		//this gets the name of button
		String temp = ((Node) event.getSource()).getId().toString();
		//debugging
		System.out.println("(Node) event.getSource()).getId().toString() = " + temp);
		//this will allow for all buttons to go through one method, loading the
		//right fxml file

		switch (temp) {
		case "displayButton":
			root = FXMLLoader.load(getClass().getResource("/view/displayApp.fxml"));
			displayAppController con1 = new displayAppController();
			con1.setMain(main);
			break;
		case "addButton":
			root = FXMLLoader.load(getClass().getResource("/view/addApp.fxml"));
			addAppController con2 = new addAppController();
			con2.setMain(main);
			break;
		case "updateButton":
			root = FXMLLoader.load(getClass().getResource("/view/updateApp.fxml"));
			updateAppController con3 = new updateAppController();
			con3.setMain(main);
			break;
		case "userButton":
			root = FXMLLoader.load(getClass().getResource("/view/addUserView.fxml"));
			addUserController con4 = new addUserController();
			con4.setMain(main);
			break;
		case "psychButton":
			root = FXMLLoader.load(getClass().getResource("/view/psychView.fxml"));
			psychController con5 = new psychController();
			con5.setMain(main);
			break;
		default:
			root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
			mainViewController con6 = new mainViewController();
			con6.setMain(main);
			break;
		}
		//sets fxml file as a scene
		scene = new Scene(root);
		//loads the scene on top of whatever stage the button is in
		stage.setScene(scene);
	} catch (Exception e){
		e.printStackTrace();
	}
    }


=======
	main=mainIn;
	}
	
	public static void setTab(int t){
		numTab = t;
		System.out.println("SET: "+numTab);
	}
	
	public static int getTab(){
		return numTab;
	}
	@FXML private TabPane tpMenu = new TabPane();
	@FXML AnchorPane apUser = new AnchorPane();
	@FXML AnchorPane apAppointments = new AnchorPane();
	@FXML Tab tbUsers = new Tab();
	@FXML Tab tbAppointments = new Tab();
	@FXML Tab tbPatients = new Tab();
	@FXML Tab tbBusiness = new Tab();
	@FXML SingleSelectionModel<Tab> selectionModel = tpMenu.getSelectionModel();

	
	public void initialize(){
		
		System.out.println(numTab);
		switch(numTab){
		case 0:
			selectionModel.select(0);
			break;
		case 1:
			selectionModel.select(1);
			//tpMenu.getSelectionModel().select(tbAppointments);
			break;
		case 2:
			selectionModel.select(2);
			break;
		case 3:
			selectionModel.select(3);
			break;
		}
		numTab = 0;
		
		
		//tpMenu.getTabs().remove(0); //This line can remove a tab at any given index (top index is 0)
		tpMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			  @Override public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				  	
				  try {	
					  String call = newTab.getId().toString();
					  switch(call)
					  {
					  case "tbAppointments":
						  URL toPane = getClass().getResource("/view/appointmentView.fxml");
					      AnchorPane temp = FXMLLoader.load(toPane);     
					      apAppointments.getChildren().setAll(temp);
						  break;
					  case "tbUsers":
						  break;
					  case "tbPatients":
						  
						  break;
					  case "tbBusiness":
						  break;
					  }
					    
				    } catch (IOException e) {
				      e.printStackTrace();
				    }
			  }
			});
	}
>>>>>>> master
}
