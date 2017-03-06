package views;

import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import common.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainClass {

	private JFrame frame;
	private ArrayList <Patient> patient_list = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClass window = new MainClass();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException
	 */
	public MainClass() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblWelcomeToMentcare = new JLabel("Welcome To Mentcare");
		lblWelcomeToMentcare.setBounds(346, 11, 250, 30);
		frame.getContentPane().add(lblWelcomeToMentcare);

		JLabel lblPatientList = new JLabel("Patient List");
		lblPatientList.setBounds(20, 53, 190, 14);
		frame.getContentPane().add(lblPatientList);



		//Start********************************************Initialize Patient list to list view MainClass
		 try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    System.out.println("Driver loaded");
		 // Connect to a database
	    java.sql.Connection connection = DriverManager.getConnection
				("jdbc:mysql://198.71.227.86:3306/mentcare_db", "TeamTigerWoods", "GOATGOAT");
	    System.out.println("Database connected");

	    // Create a statement
	    Statement statement = connection.createStatement();

	    // Execute a statement
	    ResultSet resultSet = statement.executeQuery
	      ("select * from patients");
	    //Initialize place holding variables for incoming data
	    String id = null;
	    String first_name = null;
	    String last_name = null;
	    String email_address = null;
	    String home_address = null;
	    String last_visit = null;
	    String next_visit = null;
	    String ssn = null;
	    String photo = null;



	    // Iterate through the result and print the student names
	    while (resultSet.next()){
	      id = resultSet.getString("id");
	      first_name = resultSet.getString("first_name");
	      last_name = resultSet.getString("last_name");
	      email_address = resultSet.getString("email_address");
	      home_address = resultSet.getString("home_address");
	      last_visit = resultSet.getString("last_visit");
	      next_visit = resultSet.getString("next_visit");
	      ssn = resultSet.getString("ssn");
	      photo = resultSet.getString("photo");

	      Patient p = new Patient( id,  first_name,  last_name,  email_address,  home_address,  last_visit,
	  			 next_visit,  ssn,  photo);
	      patient_list.add(p);

	    }
	    // Close the connection
	    connection.close();
	    List list = new List();
	    list.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		 Patient_View patient_view = new Patient_View();
//	    		 patient_view.window.frame.setVisible(true);
	    		
	    	}
	    });

		list.setBounds(20, 73, 149, 293);


//		ListView<String> patient_list_view = new  ListView<String>();
//		ObservableList<String> items =FXCollections.observableArrayList ();
		for(Patient p : patient_list){
			list.add(p.getFirst_name());
		}

//		patient_list_view.setItems(items);
		frame.getContentPane().add(list);
	  }


	}

