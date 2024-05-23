import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;



import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Delete {
	
	private Connection connection;
	private JFrame frame;
	private JTextField firstName;
	private JTextField courseTitle;
	
	public Delete(Connection connection) {
        this.connection = connection;
    }

	/**
	 * Launch the application.
	 */
	
//	 public static void newDeleteScreen(Connection connection) {
//	public static void main(String[] args) {
	public static void newDeleteScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete window = new Delete(connection);
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
	public Delete() {
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
		
		JLabel lblNewLabel = new JLabel("DELETE");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(25, 29, 383, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Students ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(20, 85, 118, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("First Name");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(25, 120, 92, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JTextArea lastName = new JTextArea();
		lastName.setBounds(10, 200, 128, 16);
		frame.getContentPane().add(lastName);
		
		
		firstName = new JTextField();
		firstName.setBounds(10, 144, 128, 20);
		frame.getContentPane().add(firstName);
		firstName.setColumns(10);
		
		JButton deleteStudentButton = new JButton("Delete");
		deleteStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String stdFirstName = firstName.getText();
				String stdLastName  =  lastName.getText();
				String sql1 = "DELETE FROM students WHERE fname = '" + stdFirstName + "' AND lName = '" + stdLastName + "'";
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql1);
				}catch(SQLException e1) {					
					e1.printStackTrace();
				}			
			}
		});
		deleteStudentButton.setBounds(28, 227, 89, 23);
		frame.getContentPane().add(deleteStudentButton);
		

		JLabel lblNewLabel_3 = new JLabel("Last Name");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 175, 118, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Courses");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(180, 85, 80, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		courseTitle = new JTextField();
		courseTitle.setBounds(174, 144, 86, 20);
		frame.getContentPane().add(courseTitle);
		courseTitle.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Title");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(190, 120, 46, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JButton btnNewButton = new JButton("Delete Course");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String title = courseTitle.getText();
				String sql2 = "DELETE FROM courses WHERE title = '" + title +"'";
				Statement statement2 = connection.createStatement();
				statement2.executeUpdate(sql2);				
			}catch(SQLException e1) {					
				e1.printStackTrace();
			}		
		   }
		});
		btnNewButton.setBounds(155, 227, 135, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_6 = new JLabel("Grades");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(315, 85, 71, 14);
		frame.getContentPane().add(lblNewLabel_6);
		
		JButton btnNewButton_1 = new JButton("DELETE GRADES");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String fName = firstName.getText();
				String lName = lastName.getText();
				String titleOFcourse = courseTitle.getText();	
				
				String sql3 = "SELECT id FROM students WHERE fName = '" + fName +"' AND lName = '" + lName +"'";
				String sql4 = "SELECT id FROM courses WHERE title = '" + titleOFcourse +"'";
				
				Statement statement3 = connection.createStatement();
				ResultSet resultSet3 = statement3.executeQuery(sql3);
				
				Statement statement4 = connection.createStatement();
				ResultSet resultSet4 = statement4.executeQuery(sql4);
				
				  if(resultSet3.next() && resultSet4.next()) {
				        int studentID = resultSet3.getInt("id");
				        int courseID = resultSet4.getInt("id");
				        System.out.println(resultSet3);
				        System.out.println(resultSet4);
				        
				        String sql5 = "DELETE FROM grades WHERE student_id = ? AND courses_id = ?";
				        PreparedStatement statement5 = connection.prepareStatement(sql5);
				        statement5.setInt(1, studentID);
				        statement5.setInt(2, courseID);
				        int rowsAffected = statement5.executeUpdate();
				        
				        if (rowsAffected > 0) {
				            System.out.println("Grades deleted successfully.");
				        } else {
				            System.out.println("No grades found for the specified student and course.");
				        }
				    }
				}catch(SQLException e1) {					
					System.out.println("error");
				}		
				}
				});
		btnNewButton_1.setBounds(290, 143, 134, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_7 = new JLabel("Insert ALL fields to delete grade");
		lblNewLabel_7.setBounds(190, 200, 200, 14);
		frame.getContentPane().add(lblNewLabel_7);
	}
}
