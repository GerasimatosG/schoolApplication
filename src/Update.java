import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Update {
	private Connection connection;
	private JFrame frame;
	private JTextField fnameFROM;
	private JTextField fnameTO;
	private JTextField lnameFROM;
	private JTextField lnameTO;
	private JTextField addressFROM;
	private JTextField addressTO;
	
	 public Update(Connection connection) {
	        this.connection = connection;
	    }
//	 public static void main(String[] args) {
//	 public static void newUpdateScreen(Connection connection) {
	/**
	 * Launch the application.
	 */
	 public static void newUpdateScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update window = new Update(connection);
					window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Update() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UPDATE STUDENTS");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(48, 30, 310, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" First Name FROM ");
		lblNewLabel_1.setBounds(0, 93, 104, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		fnameFROM = new JTextField();
		fnameFROM.setBounds(124, 90, 86, 20);
		frame.getContentPane().add(fnameFROM);
		fnameFROM.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("to");
		lblNewLabel_2.setBounds(220, 93, 26, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		fnameTO = new JTextField();
		fnameTO.setBounds(244, 90, 86, 20);
		frame.getContentPane().add(fnameTO);
		fnameTO.setColumns(10);
						
		JLabel lblNewLabel_3 = new JLabel(" Last Name FROM");
		lblNewLabel_3.setBounds(0, 133, 114, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		lnameFROM = new JTextField();
		lnameFROM.setBounds(124, 130, 86, 20);
		frame.getContentPane().add(lnameFROM);
		lnameFROM.setColumns(10);
		
		lnameTO = new JTextField();
		lnameTO.setBounds(244, 130, 86, 20);
		frame.getContentPane().add(lnameTO);
		lnameTO.setColumns(10);
		
		addressFROM = new JTextField();
		addressFROM.setBounds(124, 173, 86, 20);
		frame.getContentPane().add(addressFROM);
		addressFROM.setColumns(10);
		
		addressTO = new JTextField();
		addressTO.setBounds(244, 173, 86, 20);
		frame.getContentPane().add(addressTO);
		addressTO.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel(" Address FROM");
		lblNewLabel_4.setBounds(0, 176, 97, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("to");
		lblNewLabel_5.setBounds(220, 130, 26, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("to");
		lblNewLabel_6.setBounds(220, 176, 26, 14);
		frame.getContentPane().add(lblNewLabel_6);
				
		JLabel resultMessage = new JLabel("");
		resultMessage.setHorizontalAlignment(SwingConstants.CENTER);
		resultMessage.setBounds(195, 204, 229, 14);
		frame.getContentPane().add(resultMessage);
		
		// FIRST NAME UPDATE		
		JButton updateFNAMEbutton = new JButton("Update");
		updateFNAMEbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = fnameFROM.getText();
				String newFirstName = fnameTO.getText();
				String sql1 = "UPDATE students SET fName = '" + newFirstName + "' WHERE fName = '" + firstName + "'";
				Statement statement;
				try {
					statement = connection.createStatement();
					int rowsAffected1 = statement.executeUpdate(sql1);
					if( rowsAffected1 > 0) {
						resultMessage.setText("Update Success");
					}else {
						resultMessage.setText("Error.Try Again");
					}
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}			
			}
		});
		updateFNAMEbutton.setBounds(340, 89, 89, 23);
		frame.getContentPane().add(updateFNAMEbutton);
		
		// LAST NAME UPDATE	
		JButton updateLNAMEbutton = new JButton("Update");
		updateLNAMEbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lastName = lnameFROM.getText();
				String newLastName = lnameTO.getText();
				String sql2 = "UPDATE students SET lName = '" + newLastName + "' WHERE lName = '" +  lastName + "'";
				Statement statement;
				try {
					statement = connection.createStatement();
					int rowsAffected2 = statement.executeUpdate(sql2);
					if( rowsAffected2 > 0) {
						resultMessage.setText("Update Success");
					}else {
						resultMessage.setText("Error.Try Again");
					}
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}	
			}
		});
		updateLNAMEbutton.setBounds(340, 126, 89, 23);
		frame.getContentPane().add(updateLNAMEbutton);
		
		// ADDRESS UPDATE
		JButton updateADDRESSbutton = new JButton("Update");		
		updateADDRESSbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = addressFROM.getText();
				String newAddress = addressTO.getText();
				String sql3 = "UPDATE students SET address = '" + newAddress + "' WHERE address = '" + address + "'";
				Statement statement;
				try {
					statement = connection.createStatement();
					int rowsAffected1 = statement.executeUpdate(sql3);
					if( rowsAffected1 > 0) {
						resultMessage.setText("Update Success");
					}else {
						resultMessage.setText("Error.Try Again");
					}
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}			
			}
		});		
		updateADDRESSbutton.setBounds(340, 172, 89, 23);
		frame.getContentPane().add(updateADDRESSbutton);
	}
	
	
	

}
