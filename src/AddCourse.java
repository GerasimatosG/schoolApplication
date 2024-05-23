import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;


   public class AddCourse {
	   private Connection connection;

	    public AddCourse(Connection connection) {
	        this.connection = connection;
	    }
   
	
	private JFrame frame;
	private JTextField addCourseText;
   
	/**
	 * Launch the application.
	 */
	public static void newCourseScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 AddCourse window = new AddCourse(connection);
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
	public AddCourse() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setForeground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add New Course");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Mongolian Baiti", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 38, 396, 14);
		frame.getContentPane().add(lblNewLabel);
		
		addCourseText = new JTextField();
		addCourseText.setHorizontalAlignment(SwingConstants.CENTER);
		addCourseText.setBounds(146, 80, 123, 20);
		frame.getContentPane().add(addCourseText);
		addCourseText.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String course = addCourseText.getText();
				 if (course != null && !course.isEmpty()) {
					 try {
	                        Statement statement = connection.createStatement();
	                        String sql = "INSERT INTO courses (title) VALUES ('" + course + "')";
	                        int rowsAffected = statement.executeUpdate(sql);
	                        if (rowsAffected > 0) {
	                            System.out.println("Course added successfully: " + course);	                       
	                        } else {
	                            System.out.println("Failed to add course: " + course);	                      
	                        }
	                        statement.close();
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                    
	                    }
				 }
			}
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setBounds(165, 127, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}

}
