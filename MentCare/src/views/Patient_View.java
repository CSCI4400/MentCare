package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.ToggleGroup;
import models.Patient;
import models.Patient_History;

import java.awt.TextArea;
import java.sql.SQLException;
import java.util.Enumeration;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Canvas;
import java.awt.Color;

public class Patient_View extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Patient_View dialog = new Patient_View(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			PropertyChangeListener listener = null;
			dialog.addPropertyChangeListener(listener);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Patient_View(Patient patient) throws ClassNotFoundException, SQLException {
		PropertyChangeListener listener = null;
		setBounds(100, 100, 450, 300);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);


		//Create Image Panel **********************
		JPanel patient_image_panel = new JPanel();
		patient_image_panel.setBounds(10, 36, 310, 253);
		getContentPane().add(patient_image_panel);
		//Add image to image panel******************************
		ImageIcon image = new ImageIcon(getClass().getResource("../resources/placehold.png"));
		JLabel label = new JLabel("", image, JLabel.CENTER);
		label.setBounds(5, 5, 300, 250);
		JPanel panel = new JPanel(new BorderLayout());
		patient_image_panel.setLayout(null);
		patient_image_panel.add( label );

		JPanel patient_info_panel = new JPanel();
		patient_info_panel.setBounds(330, 11, 444, 278);
		getContentPane().add(patient_info_panel);
		patient_info_panel.setLayout(null);

		JLabel lblPatientInformtation = new JLabel("Patient Informtation");
		lblPatientInformtation.setVerticalAlignment(SwingConstants.TOP);
		lblPatientInformtation.setBounds(10, 11, 315, 14);
		patient_info_panel.add(lblPatientInformtation);

		JLabel lblName = new JLabel("First Name: " + patient.getFirst_name());
		lblName.setBounds(20, 36, 295, 14);
		patient_info_panel.add(lblName);

		JLabel lblNewLabel = new JLabel("Last Name: " + patient.getLast_name());
		lblNewLabel.setBounds(20, 61, 295, 14);
		patient_info_panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("SSN: " + patient.getssn());
		lblNewLabel_1.setBounds(20, 86, 295, 14);
		patient_info_panel.add(lblNewLabel_1);

		JLabel lblLastVisit = new JLabel("Last Visit: " + patient.getLast_visit());
		lblLastVisit.setBounds(20, 111, 295, 14);
		patient_info_panel.add(lblLastVisit);

		JLabel lblNextVisit = new JLabel("Next Visit: " + patient.getLast_visit());
		lblNextVisit.setBounds(20, 136, 295, 14);
		patient_info_panel.add(lblNextVisit);

		JLabel lblHomeAddress = new JLabel("Home Address:" + patient.getHome_address());
		lblHomeAddress.setBounds(20, 161, 295, 14);
		patient_info_panel.add(lblHomeAddress);

		//text field associated with change address button
		TextField changeAddressText = new TextField();
		changeAddressText.setBounds(163, 197, 271, 22);
		patient_info_panel.add(changeAddressText);


		JButton btnChangeAddress = new JButton("Change Address");
		btnChangeAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//this is the action event handler for the change address button

				try {
					String newAddress = changeAddressText.getText();
					patient.Update_Patient_Address(newAddress);
					//refresh the frame after updateing the address in the database


				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnChangeAddress.setBounds(10, 197, 147, 23);
		patient_info_panel.add(btnChangeAddress);



		JPanel patient_history_panel = new JPanel();
		patient_history_panel.setBounds(10, 300, 764, 250);
		getContentPane().add(patient_history_panel);
		patient_history_panel.setLayout(null);



		JLabel lblNewLabel_2 = new JLabel("Patient History Description");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 5, 372, 14);
		patient_history_panel.add(lblNewLabel_2);


		//Insert Patient History by calling Patient_Histoty call and using Patient Object incoming from mainWindow
		Patient_History hist = new Patient_History(patient);
		TextArea textArea_1 = new TextArea();
		textArea_1.setText("Patients upcoming Surgeries: " + patient.getSurgeries() + "\n" +
				           "Patients recomended recovery time: " + patient.getRecovery_time() + "\n" +
				           "Patients prescriptions: " + patient.getPrescription() + "\n" +
				           "Patients recorded harm to self: " + patient.getPrevious_harm_to_self() + "\n" +
				           "Patients recorded harm to self: " + patient.getPrevious_harm_to_others() + "\n");

		textArea_1.setBounds(10, 25, 372, 215);
		patient_history_panel.add(textArea_1);

		JLabel lblCurrentDangerLevel = new JLabel("Current Danger Level: " + patient.getThreat_level());
		lblCurrentDangerLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentDangerLevel.setBounds(554, 25, 200, 14);
		patient_history_panel.add(lblCurrentDangerLevel);


		//Create new canvas for threat level
		Canvas canvas = new Canvas();
		canvas.setBounds(554, 45, 200, 166);
		//Control Structure to determine what color canvas should be initially

		if(patient.getThreat_level().contains("l")){
			canvas.setBackground(Color.GREEN);
			System.out.print("low");
		}if(patient.getThreat_level().contains("m")){
			canvas.setBackground(Color.yellow);
			System.out.print("low");
		}if(patient.getThreat_level().contains("h")){
			canvas.setBackground(Color.red);
			System.out.print("low");
		}if(patient.getThreat_level().contains("n")){
			canvas.setBackground(Color.gray);
			System.out.print("last");
		}



		patient_history_panel.add(canvas);

		JLabel lblSetDangerLevel = new JLabel("Set Danger Level");
		lblSetDangerLevel.setBounds(391, 25, 214, 14);
		patient_history_panel.add(lblSetDangerLevel);
		//create group for radio buttons
		final ButtonGroup group = new ButtonGroup();


		//initialize radio buttons
		JRadioButton rdbtnNone = new JRadioButton("none");
		rdbtnNone.setBounds(401, 45, 109, 23);
		group.add(rdbtnNone);
		patient_history_panel.add(rdbtnNone);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("low");
		rdbtnNewRadioButton.setBounds(401, 71, 109, 23);
		group.add(rdbtnNewRadioButton);
		patient_history_panel.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("medium");
		rdbtnNewRadioButton_1.setBounds(401, 97, 109, 23);
		group.add(rdbtnNewRadioButton_1);
		patient_history_panel.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("high");
		rdbtnNewRadioButton_2.setBounds(401, 123, 109, 23);
		group.add(rdbtnNewRadioButton_2);
		patient_history_panel.add(rdbtnNewRadioButton_2);

		JButton btnNewButton = new JButton("Sumbit ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//this is the action event handler for the set danger radio button

				String newdanger = null;
				for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
				    AbstractButton button = buttons.nextElement();

				    if (button.isSelected()) {
				        newdanger = button.getText();
				    }
				}
				try {
					patient.Update_Patient_Danger_level(newdanger);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//refresh the frame after updateing the address in the database
			}
		});
		btnNewButton.setBounds(421, 153, 89, 23);
		group.add(btnNewButton);
		patient_history_panel.add(btnNewButton);

		JLabel image_label = new JLabel("Patient Image");
		image_label.setBounds(10, 11, 310, 27);
		getContentPane().add(image_label);


	}
}
