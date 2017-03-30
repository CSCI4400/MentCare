package application;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainViewController implements Initializable{
	
	private Connection connection = DBConnection.dbConnection();
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private ObservableList<PersonalInfoList> data = FXCollections.observableArrayList();	
	
	@FXML
	private TableView<PersonalInfoList> tablePersonalInfo;
	@FXML
	private TableColumn<PersonalInfoList, String> columnPNumber;
	@FXML
	private TableColumn<PersonalInfoList, String> columnFName;
	@FXML
	private TableColumn<PersonalInfoList, String> columnLName;
	@FXML
	private TableColumn<PersonalInfoList, String> columnBDate;
	@FXML
	private TableColumn<PersonalInfoList, String> columnSex;
	@FXML
	private TableColumn<PersonalInfoList, String> columnAddress;
	@FXML
	private TableColumn<PersonalInfoList, String> columnPhoneNumber;
	@FXML
	private TableColumn<PersonalInfoList, String> columnDangerLvl;
	@FXML
	private TextArea addressTextArea;
	@FXML
	private TextField dangerTextField;
	@FXML
	private Button updateAddressButton;
	@FXML
	private Button updateDangerLvlButton;
	@FXML
	private Button loginBtn;
	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private AnchorPane loginPane;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private AnchorPane patientInfoPane;


	private long timeOut = 5000;
	private int currentPin = 1234;
	public Timer timer = new Timer();

	@Override
	public void initialize(URL url, ResourceBundle rb){
		login();
		setCellTable();
		loadPersonalInfo();
		setCellValueFromTableToTextArea();
		updateAddress();
		updateDangerLevel();
	}
	
	public void showLogin(){
		patientInfoPane.setVisible(false);
		loginPane.setVisible(true);
	}
	
	public void showPatientInfo(){
		patientInfoPane.setVisible(true);
		loginPane.setVisible(false);
	}
	
	private void login(){
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(usernameTF.getText().equals("1") && passwordTF.getText().equals("1")){
					patientInfoPane.setVisible(true);
					loginPane.setVisible(false);
				}
				else
					System.out.println("Wrong!");
			}
		});
	}
	
	public void resetTimer(){
		timer.cancel();
	}
	
	public void enableBlur() {
	    ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
	    GaussianBlur blur = new GaussianBlur(25);
	    adj.setInput(blur);
	    patientInfoPane.setEffect(adj);
	}
	
	public void timeOut(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			  public void run() {
				  Platform.runLater(new Runnable() {
				       public void run() {
				    	   if(patientInfoPane.isVisible()){
								enableBlur();
								TextInputDialog dialog = new TextInputDialog();
								dialog.setTitle("Timed Out");
								dialog.setHeaderText("You have timed out.");
								dialog.setContentText("Please enter your pin to remain logged in: ");
								Optional<String> result = dialog.showAndWait();
								if (result.isPresent()){
								    if(result.get().equals(String.valueOf(currentPin))){
								    	patientInfoPane.setEffect(null);
								    	return;
								    }
								    else{
								    	Alert alert = new Alert(AlertType.ERROR);
										alert.setTitle("Error");
										alert.setHeaderText("Incorrect Pin!");
										alert.setContentText("You are being signed out.");
										alert.showAndWait();
								    	patientInfoPane.setVisible(false);
										loginPane.setVisible(true);
								    }
								}
								else{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Canceled");
									alert.setHeaderText("You closed the dialog!");
									alert.setContentText("You are being signed out.");
									alert.showAndWait();
							    	patientInfoPane.setVisible(false);
									loginPane.setVisible(true);
								}
							}
				       }
				  });
			  }
		}, timeOut);
	}
	
	private void setCellTable(){
		columnPNumber.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("PNumber"));
		columnFName.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("FName"));
		columnLName.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("LName"));
		columnBDate.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("BDate"));
		columnSex.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("Sex"));
		columnAddress.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("Address"));
		columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("Phone_Number"));
		columnDangerLvl.setCellValueFactory(new PropertyValueFactory<PersonalInfoList, String>("Danger_lvl"));
	}
	
	private void loadPersonalInfo(){
		data.clear();
		try {
			String query = "SELECT PNumber,FName,LName,BDate,Sex,Address,Phone_Number,Danger_lvl FROM Personal_Info";
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()){
				String pNumber = Integer.toString(rs.getInt(1));
				String fName = rs.getString(2);
				String lName = rs.getString(3); 
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				String bDate = df.format(rs.getDate(4)); 
				String sex = rs.getString(5);
				String address = rs.getString(6);
				String phone_Number = rs.getString(7);
				String danger_Lvl = Integer.toString(rs.getInt(8));
				data.add(new PersonalInfoList(pNumber, fName, lName, bDate, sex, address, phone_Number, danger_Lvl));
				tablePersonalInfo.setItems(data);
			}
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void setCellValueFromTableToTextArea(){
		tablePersonalInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event){
				PersonalInfoList list = tablePersonalInfo.getItems().get(tablePersonalInfo.getSelectionModel().getSelectedIndex());
				addressTextArea.setText(list.getAddress());
				dangerTextField.setText(list.getDanger_lvl());
			}
		});
	}
	
	private void updateAddress(){
		updateAddressButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try{
					PersonalInfoList list = tablePersonalInfo.getItems().get(tablePersonalInfo.getSelectionModel().getSelectedIndex());
					String id = list.getPNumber();
					String newAddress = addressTextArea.getText();
					
					String query = "UPDATE Personal_Info SET Address = '" + newAddress + "' WHERE PNumber = '" + id + "'";
					pst = connection.prepareStatement(query);
					pst.executeUpdate();
					
					loadPersonalInfo();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	private void updateDangerLevel(){
		updateDangerLvlButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try{
					PersonalInfoList list = tablePersonalInfo.getItems().get(tablePersonalInfo.getSelectionModel().getSelectedIndex());
					String id = list.getPNumber();
					String newDangerLvl = dangerTextField.getText();
					
					String query = "UPDATE Personal_Info SET Danger_lvl = '" + newDangerLvl + "' WHERE PNumber = '" + id + "'";
					pst = connection.prepareStatement(query);
					pst.executeUpdate();
					
					loadPersonalInfo();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
