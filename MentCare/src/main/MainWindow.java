package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import models.Patient;
import models.Patient_List;
import views.Patient_View;

import java.awt.List;
import java.sql.SQLException;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	 * @throws ClassNotFoundException 
	 */
	public MainWindow() throws ClassNotFoundException, SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcomeToMent = new JLabel("Welcome To Ment Care System");
		lblWelcomeToMent.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToMent.setBounds(10, 11, 764, 14);
		frame.getContentPane().add(lblWelcomeToMent);
		
		
		//Populate List with Patient Names
		Patient_List patient_object_list = new Patient_List();
		List list = new List();
		
		for(Patient p : patient_object_list.getList()){
			list.add(p.getFirst_name() + " " + p.getLast_name());
		}
		
		//Add event Handler for when a name is selected from list
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Open the Patient_View class when name is selected from list
				
				Patient_View newWindow;
				try {
					Patient  p = patient_object_list.getPatient(list.getSelectedIndex());
					newWindow = new Patient_View(p);
					newWindow.setVisible(true);
					//change address in patient view when it is changed in db
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		
		
		list.setBounds(10, 60, 175, 452);
		frame.getContentPane().add(list);
		
		Label label = new Label("Patient List");
		label.setBounds(20, 32, 165, 22);
		frame.getContentPane().add(label);
	}
}
