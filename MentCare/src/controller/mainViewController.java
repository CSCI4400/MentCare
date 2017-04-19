
//edited by Anna, 3/25/17, added a button and link to addUser

package controller;

import application.MainFXApp;


import java.io.IOException;

import java.net.URL;

import application.MainFXApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;


import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;

public class mainViewController {

	Stage stage;
	Scene scene;
	Parent root;

	private static int numTab = 0;


	//always reference main method, and build constructor
	private MainFXApp main;
	public void setMain(MainFXApp mainIn)
	{
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

	@FXML AnchorPane apAppointments = new AnchorPane();
	@FXML AnchorPane apPatients = new AnchorPane();
	@FXML AnchorPane toBuisnessPane = new AnchorPane();

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
		
		
		try { //Make appointment view open at application launch
			URL toPane;
			AnchorPane temp;
			toPane = getClass().getResource("/view/appointmentView.fxml");
		    temp = FXMLLoader.load(toPane);
		    apAppointments.getChildren().setAll(temp);
		} catch (Exception e) {
			
		}


		//tpMenu.getTabs().remove(0); //This line can remove a tab at any given index (top index is 0)
		tpMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			  @Override public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {

				  try {
					  String call = newTab.getId().toString();
					  URL toPane;
					  AnchorPane temp;
					  switch(call)
					  {

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
						  toPane = getClass().getResource("/view/businessView.fxml");
					      temp = FXMLLoader.load(toPane);
					      toBuisnessPane.getChildren().setAll(temp);
						  break;
					  }

				    } catch (IOException e) {
				      e.printStackTrace();
				    }
			  }
			});
	}

}
