import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Grade extends AssignCourse {
	private JPanel checkboxPanel;
	

    public Grade(Connection connection) {
    	super(connection);
    }

	private JFrame frame;
	private JTextField fName;
	private JTextField lName;

	/**
	 * Launch the application.
	 */
	public static void newGradeScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grade window = new Grade(connection);
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
	public Grade() {
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
		
		//MAKING OF LABELS AND INPUTS
		 
		 JLabel lblNewLabel = new JLabel("Grade Course of a Student");
		 lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		 lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNewLabel.setBounds(38, 29, 344, 40);
		 frame.getContentPane().add(lblNewLabel);
		 
		 
		 JLabel lblNewLabel_1 = new JLabel("Course");
		 lblNewLabel_1.setForeground(Color.RED);
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNewLabel_1.setBounds(28, 80, 46, 14);
		 frame.getContentPane().add(lblNewLabel_1);
		 
		 JLabel lblNewLabel_2 = new JLabel("Student Name");
		 lblNewLabel_2.setForeground(Color.BLUE);
		 lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNewLabel_2.setBounds(157, 80, 89, 14);
		 frame.getContentPane().add(lblNewLabel_2);
		 
		 JLabel lblNewLabel_3 = new JLabel("Grade");
		 lblNewLabel_3.setForeground(new Color(0, 128, 0));
		 lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNewLabel_3.setBounds(308, 80, 46, 14);
		 frame.getContentPane().add(lblNewLabel_3);	
		 
		 fName = new JTextField();
		 fName.setBounds(157, 105, 86, 20);
		 frame.getContentPane().add(fName);
		 fName.setColumns(10);
		 
		 JLabel lblNewLabel_4 = new JLabel("Last Name");
		 lblNewLabel_4.setForeground(Color.BLUE);
		 lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		 lblNewLabel_4.setBounds(157, 153, 89, 14);
		 frame.getContentPane().add(lblNewLabel_4);
		 
		 lName = new JTextField();
		 lName.setBounds(157, 193, 86, 20);
		 frame.getContentPane().add(lName);
		 lName.setColumns(10);
		 
		 JLabel message = new JLabel("");
		 message.setHorizontalAlignment(SwingConstants.CENTER);
		 message.setBounds(220, 226, 214, 14);
		 frame.getContentPane().add(message);
		
	        
	    	//CODE FOR CHECKBOX LIST 
			
			checkboxPanel = new JPanel();
			checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
			
			 ArrayList<String> courseTitles = getCourseTitlesFromDatabase();	 
			 
			
			 for (String courseTitle : courseTitles) {
		            JCheckBox checkBox = new JCheckBox(courseTitle);
		            checkboxPanel.add(checkBox);
		        }
			 
			 JScrollPane scrollPane = new JScrollPane(checkboxPanel);
			 scrollPane.setBounds(100, 80, 120, 100);
		        frame.getContentPane().add(scrollPane);
		        scrollPane.setBounds(10, 100, 100, 140);
		        
		        frame.setVisible(true);
		        
		        
		        //CODE FOR COMBO BOX
		        final Integer[] selectedNumber = new Integer[1];
		        
		        JComboBox<Integer> comboBox = new JComboBox<>();
				 comboBox.addActionListener(new ActionListener() {
					    public void actionPerformed(ActionEvent e) {
					        @SuppressWarnings("unchecked")
							JComboBox<Integer> comboBox = (JComboBox<Integer>) e.getSource();
					        selectedNumber[0] = (Integer) comboBox.getSelectedItem();
					    }
					});
				 comboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
				 comboBox.setMaximumRowCount(10);
				 comboBox.setBounds(308, 105, 56, 22);
				 frame.getContentPane().add(comboBox);
			     frame.setVisible(true);
		        
		        
		        
		    //CODE FOR BUTTON  
			     JButton btnNewButton = new JButton("Submit");
			     btnNewButton.addActionListener(new ActionListener() {
			         public void actionPerformed(ActionEvent e) {
			             // Get the course ID from the database
			             int course = getCourseSingleIDFromDatabase();

			             // Get student ID from inputs
			             int studentID = getStudentId(fName.getText(), lName.getText());

			             try {
			                 Statement statement = connection.createStatement();
			                 Integer selectedGrade = selectedNumber[0];

			                 // Check if the course is assigned to the student
			                 String checkIfAssignedQuery = "SELECT * FROM student_courses WHERE student_id = " + studentID + " AND course_id = " + course;
			                 ResultSet assignmentResult = statement.executeQuery(checkIfAssignedQuery);

			                 if (assignmentResult.next()) {
			                     // Check if the course is already graded for the student
			                     String checkIfGradedQuery = "SELECT * FROM grades WHERE student_id = " + studentID + " AND courses_id = " + course;
			                     ResultSet gradingResult = statement.executeQuery(checkIfGradedQuery);

			                     if (gradingResult.next()) {
			                         // Update the existing grade
			                         String updateQuery = "UPDATE grades SET grade = " + selectedGrade + " WHERE student_id = " + studentID + " AND courses_id = " + course;
			                         int rowsAffected = statement.executeUpdate(updateQuery);
			                         if (rowsAffected > 0) {
			                             message.setText("Grade updated successfully");
			                         } else {
			                             message.setText("Something went wrong while updating grade. Please try again.");
			                         }
			                     } else {
			                         // Insert a new grade
			                         String insertQuery = "INSERT INTO grades (student_id, courses_id, grade) VALUES ('" + studentID + "', '" + course + "', '" + selectedGrade + "')";
			                         int rowsAffected = statement.executeUpdate(insertQuery);
			                         if (rowsAffected > 0) {
			                             message.setText("Grade added successfully");
			                         } else {
			                             message.setText("Something went wrong while adding grade. Please try again.");
			                         }
			                     }
			                 } else {
			                     message.setText("Course is Not Assigned");
			                 }
			             } catch (SQLException ex) {
			                 message.setText("Error with database operation. Please try again.");
			                 ex.printStackTrace();
			             }
			         }
			     });

				 btnNewButton.setBounds(322, 149, 89, 23);
				 frame.getContentPane().add(btnNewButton);		
				 
			
	}	
	//METHODS 
		
		public int getCourseSingleIDFromDatabase() {
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
	        
	        String queryIDcourses = "";
	        if (!selectedCourses.isEmpty()) {
	           
	            String firstSelectedCourse = selectedCourses.get(0);
	            queryIDcourses = "SELECT id FROM courses WHERE title = '" + firstSelectedCourse + "'";
	        }
	        
	        int courseID = -1;
	        
	        try {
	        	Statement statement = connection.createStatement();
	        	ResultSet resultSet = statement.executeQuery(queryIDcourses);
	        	if (resultSet.next()) { 
	                courseID = resultSet.getInt("id");
	            } else {	                
	                System.out.println("No course found with the specified title.");
	            }	            
	            resultSet.close(); 
	            statement.close();                
                
	        }catch (Exception e) {
	               System.out.println("Empty Fields.Try Again");
	           }
	        
	        return courseID;
		}
		
	}
	
	


