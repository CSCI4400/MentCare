package controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import application.MainFXApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ListPatient;

import model.Patient_History;
import model.Patient_List;




public class PatientListController  {
	@FXML
    private TableView<ListPatient> patientTable;
    @FXML
    private TableColumn<ListPatient, String> firstNameColumn;
    @FXML
    private TableColumn<ListPatient, String> lastNameColumn;
    //Labels
    @FXML
    protected Label firstNameLabel;
    @FXML
    protected Label lastNameLabel;
    @FXML
    protected Label ssnLabel;
    @FXML
    protected Label lastVisitLabel;
    @FXML
    protected Label nextVisitLabel;
    @FXML
    protected Label homeAddressLabel;

    //Text Areas
    @FXML
    protected TextArea patientHistory;
    @FXML TextField changeAddressText;

    //Images
    @FXML
    protected Pane threatPane;
    @FXML
    ImageView patientImage;

    //Buttons
    @FXML Button changeAddressButton;
    @FXML Button backButton;



	@FXML
	private AnchorPane loginPane;
	@FXML
	private AnchorPane patientInfoPane;

	Stage stage;
	Scene scene;
	Parent root;


	// always reference main method, and build constructor
	private MainFXApp main;

	public void setMain(MainFXApp mainIn) {
		main = mainIn;

	}

	 @FXML public void backButtonAction(ActionEvent click) throws Exception {
	        try{
	            stage = (Stage) ((Button) click.getSource()).getScene().getWindow();

	            String source = ((Node) click.getSource()).getId();

	            switch (source) {
	                case "backButton":
	                    root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
	                    patientViewController act2 = new patientViewController();
	                    act2.setMain(main);
	                    break;
			default:
	                    break;
	            }
	            //sets fxml file as a scene
	            scene = new Scene(root);
	            //loads the scene on top of whatever stage the button is in
	            stage.setScene(scene);
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }





	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PatientListController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws ClassNotFoundException
     * @throws URISyntaxException
     *
     */
    @FXML
    private void initialize() throws SQLException, ClassNotFoundException, URISyntaxException {
	 //Initialize Patient_List
	    Patient_List patient_list = new Patient_List();

	 // Add observable list data to the table
        patientTable.setItems(patient_list.getObservableList());

     // Initialize the person table
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<ListPatient, String>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<ListPatient, String>("last_name"));

     // Auto resize columns
        patientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // clear person
        try {
			showPatientDetails(null);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       //Listen for selection changes
        patientTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListPatient>() {

         public void changed(ObservableValue<? extends ListPatient> observable,
             ListPatient oldValue, ListPatient newValue) {
           try {				showPatientDetails(newValue);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
        });


    }


    /**
     * @param patient
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws URISyntaxException
     */
    public void showPatientDetails(ListPatient patient) throws ClassNotFoundException, SQLException, IOException, URISyntaxException{
    	if(patient == null){



    	}else{

    		 firstNameLabel.setText(patient.getFirst_name());

	         lastNameLabel.setText(patient.getLast_name());

	         ssnLabel.setText(patient.getssn());

	         lastVisitLabel.setText(patient.getLast_visit());

	         nextVisitLabel.setText(patient.getNext_visit());

	         homeAddressLabel.setText(patient.getHome_address());



	         //Patient History
	         Patient_History hist = new Patient_History(patient);
	         patientHistory.setText("Patients upcoming Surgeries: " + patient.getSurgeries() + "\n" +
				           "Patients recomended recovery time: " + patient.getRecovery_time() + "\n" +
				           "Patients prescriptions: " + patient.getPrescription() + "\n" +
				           "Patients recorded harm to self: " + patient.getPrevious_harm_to_self() + "\n" +
				           "Patients recorded harm to self: " + patient.getPrevious_harm_to_others() + "\n");


	         //Patient Threat Level

	         if(patient.getThreat_level().contains("l")){
	        	 threatPane.setStyle("-fx-background-color: green");

	 			System.out.print("low");
	 		}if(patient.getThreat_level().contains("m")){
	 			threatPane.setStyle("-fx-background-color: yellow");
	 			System.out.print("medium");
	 		}if(patient.getThreat_level().contains("h")){
	 			threatPane.setStyle("-fx-background-color: red");
	 			System.out.print("high");
	 		}if(patient.getThreat_level().contains("n")){
	 			threatPane.setStyle("-fx-background-color: grey");
	 			System.out.print("none");
	 		}



	 		//set image last
	 		Image newImage = new Image("resources/"+patient.getPhoto());
	 		patientImage.setImage(newImage);







//	 		changeAddressButton.addActionListener(new ActionListener() {
//	    		public void actionPerformed(ActionEvent arg0) {
//	    			//this is the action event handler for the change address button
//
//	    			try {
//	    				String newAddress = changeAddressText.getText();
//	    				patient.Update_Patient_Address(newAddress);
//	    				//refresh the frame after updating the address in the database
//
//
//	    			} catch (ClassNotFoundException e) {
//	    				// TODO Auto-generated catch block
//	    				e.printStackTrace();
//	    			} catch (SQLException e) {
//	    				// TODO Auto-generated catch block
//	    				e.printStackTrace();
//	    			}
//
//
//
//	    }
   	}


    	}

	public void enableBlur() {
	    ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(25);
	    adj.setInput(blur);
	    patientInfoPane.setEffect(adj);
	}


   }




