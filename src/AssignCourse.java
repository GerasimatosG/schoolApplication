import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AssignCourse {
	private JPanel checkboxPanel;	
	protected Connection connection;

    public AssignCourse(Connection connection) {
        this.connection = connection;
    }
	
	private JFrame frame;
	private JTextField firstName;
	private JTextField lastName;

	/**
	 * Launch the application.
	 */
	public static void newAssignCourseScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssignCourse window = new AssignCourse(connection);
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
	public AssignCourse() {
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
		
		JLabel lblNewLabel = new JLabel("Assign Course to Student");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(54, 11, 308, 51);
		frame.getContentPane().add(lblNewLabel);
		
		firstName = new JTextField();
		firstName.setBounds(229, 103, 86, 20);
		frame.getContentPane().add(firstName);
		firstName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(229, 78, 86, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(325, 78, 87, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		lastName = new JTextField();
		lastName.setBounds(326, 103, 86, 20);
		frame.getContentPane().add(lastName);
		lastName.setColumns(10);
		
		//CODE FOR BUTTON
		
		JButton btnNewButton = new JButton("Assign");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> selectedCourses = new ArrayList<>();
                Component[] components = checkboxPanel.getComponents();

                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            selectedCourses.add(checkBox.getText());
                        }
                    }
                }
				
				 //TURN TITLE TO ID NUMBER FROM DATABASE
                Map<Integer, Integer> courseIDMap = getCourseIDsFromDatabase(selectedCourses);
				 
				 
				 //ASSOCIATE WITH EACH STUDENT
                int studentID = getStudentId(firstName.getText(), lastName.getText()); 
                insertStudentCourseAssociations(studentID, courseIDMap);
			}
		});
		btnNewButton.setBounds(273, 152, 89, 23);
		frame.getContentPane().add(btnNewButton);	
		
		//CODE FOR CHECKBOX LIST 
		
		checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
		
		 ArrayList<String> courseTitles = getCourseTitlesFromDatabase();	 
		 
		
		 for (String courseTitle : courseTitles) {
	            JCheckBox checkBox = new JCheckBox(courseTitle);
	            checkboxPanel.add(checkBox);
	        }
		 
		 JScrollPane scrollPane = new JScrollPane(checkboxPanel);
		 scrollPane.setBounds(10, 60, 110, 180);
	        frame.getContentPane().add(scrollPane);
	        
	        frame.setVisible(true);
		
	}
	
	
		//METHODS 
	
		//RETRIEVE COURSE TITLES FROM DATABASE
	
	  protected ArrayList<String> getCourseTitlesFromDatabase() {
          ArrayList<String> courseTitles = new ArrayList<>();
          try {
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery("SELECT title FROM courses");
  
              while (resultSet.next()) {
                  String title = resultSet.getString("title");
                  courseTitles.add(title);
              }
  
              statement.close();
          } catch (Exception e) {
              e.printStackTrace();
          }
          return courseTitles;
      }
      
		
		
		//RETRIEVE COURSE ID FROM TITLES
		
	  protected Map<Integer, Integer> getCourseIDsFromDatabase(ArrayList<String> selectedCourses) {
		    Map<Integer, Integer> courseIDMap = new HashMap<>();
		    try {
		        Statement statement = connection.createStatement();
		        for (String courseTitle : selectedCourses) {
		            String query = "SELECT id FROM courses WHERE title = '" + courseTitle + "'";
		            ResultSet resultSet = statement.executeQuery(query);
		            if (resultSet.next()) {
		                int courseID = resultSet.getInt("id");
		                courseIDMap.put(courseID, courseID);
		            }
		        }
		        statement.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return courseIDMap;
		}
		
		//ASSOCIATE STUDENTS WITH COURSES
		
	  protected void insertStudentCourseAssociations(int studentID, Map<Integer, Integer> courseIDMap) {
		    try {
		        Statement statement = connection.createStatement();
		        
		        for (Map.Entry<Integer, Integer> entry : courseIDMap.entrySet()) {
		            int courseID = entry.getKey();
		            
		            // Insert a new record into the student_courses table
		            String query = "INSERT INTO student_courses (student_id, course_id) VALUES (" + studentID + ", " + courseID + ")";
		            statement.executeUpdate(query);
		        }
		        
		        statement.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		
		//GET STUDENT ID TO MAKE THE CONNECTIONS
	  protected int getStudentId(String firstName, String lastName) {
		    int studentId = -1;
		    try {
		        Statement statement = connection.createStatement();
		        String query = "SELECT id FROM students WHERE fName = '" + firstName + "' AND lName = '" + lastName + "'";
		        ResultSet resultSet = statement.executeQuery(query);
		        if (resultSet.next()) {
		            studentId = resultSet.getInt("id");
		        }
		        statement.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return studentId;
		}
}

