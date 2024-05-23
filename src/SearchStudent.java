import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class SearchStudent {
	
	    private Connection connection;
	    private JList<String> list;
	    private JFrame frame;
	    private JTextField firstName;
	    private JTextField lastName;
	    private JLabel idDisplay;
	    private JLabel genderDisplay;
	    private JLabel fNameDisplay;
	    private JLabel lNameDisplay;
	    private JLabel birthdayDisplay;
	    private JLabel addressDisplay;
	    private ResultSet resultSet;
	    private Statement statement;
	    private DefaultListModel<String> listModel;


	    public SearchStudent(Connection connection) {
	        this.connection = connection;
	    }
//	    public static void newSearchStudentWindow(Connection connection) {
//	    	public static void main(String[] args) {
	 

	/**
	 * Launch the application.
	 */
	    public static void newSearchStudentWindow(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchStudent window = new SearchStudent(connection);
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
	public SearchStudent() {
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
		
		JLabel lblNewLabel = new JLabel("Search Student");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(47, -1, 327, 38);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(22, 48, 73, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(132, 48, 71, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		firstName = new JTextField();
		firstName.setBounds(10, 73, 97, 31);
		frame.getContentPane().add(firstName);
		firstName.setColumns(10);
		
		lastName = new JTextField();
		lastName.setBounds(121, 73, 97, 31);
		frame.getContentPane().add(lastName);
		lastName.setColumns(10);
		
		JLabel idDisplay = new JLabel("");
		idDisplay.setBounds(10, 163, 50, 14);
		frame.getContentPane().add(idDisplay);
		
		JLabel genderDisplay = new JLabel("");
		genderDisplay.setBounds(150, 212, 129, 14);
		frame.getContentPane().add(genderDisplay);
		
		JLabel fNameDisplay = new JLabel("");
		fNameDisplay.setBounds(10, 187, 136, 14);
		frame.getContentPane().add(fNameDisplay);
		
		JLabel lNameDisplay = new JLabel("");
		lNameDisplay.setBounds(150, 187, 175, 14);
		frame.getContentPane().add(lNameDisplay);
		
		JLabel birthdayDisplay = new JLabel("");
		birthdayDisplay.setBounds(10, 212, 136, 14);
		frame.getContentPane().add(birthdayDisplay);
		
		JLabel addressDisplay = new JLabel("");
		addressDisplay.setBounds(150, 163, 129, 14);
		frame.getContentPane().add(addressDisplay);
		
		//CODE FOR LIST
		listModel = new DefaultListModel<>();
		list = new JList<>();
		JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setBounds(280, 23, 150, 150);
	    frame.getContentPane().add(scrollPane);
	    scrollPane.setVisible(false);
		
		
		//CODE FOR BUTTON
		
		 JButton searchStudent = new JButton("Search");
	        searchStudent.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String fName = firstName.getText();
	                String lName = lastName.getText();
	                try {
	                    if (fName != null && !fName.isEmpty() && lName != null && !lName.isEmpty()) {
	                        statement = connection.createStatement();	                       
	                        String sql = "SELECT * FROM students WHERE fName = '" + fName + "' AND lName = '" + lName + "'";
	                        resultSet = statement.executeQuery(sql);
	                        if (resultSet.next()) {
	                            String id = "ID: " + resultSet.getInt("id");
	                            String studentFirstName = "First Name: " + resultSet.getString("fName");
	                            String studentLastName = "Last Name: " + resultSet.getString("lName");
	                            String address = "Address: " + resultSet.getString("address");
	                            String birthday = "Birthday: " + resultSet.getString("birthday");
	                            String gender = "Gender: " + resultSet.getString("gender");

	                            idDisplay.setText(id);
	                            fNameDisplay.setText(studentFirstName);
	                            lNameDisplay.setText(studentLastName);
	                            addressDisplay.setText(address);
	                            birthdayDisplay.setText(birthday);
	                            genderDisplay.setText(gender);
	                           
	                            int studentID = resultSet.getInt("id");
	                            updateList(studentID);
	                            scrollPane.setVisible(true);
	                            String coursesQuery = "SELECT courses.title, COALESCE(grades.grade, 0) AS grade " +
	                                    "FROM courses " +
	                                    "LEFT JOIN grades ON courses.id = grades.courses_id AND grades.student_id = " + studentID;
	                            ResultSet coursesResultSet = statement.executeQuery(coursesQuery);
	                            List<String> itemList = new ArrayList<>();	                           
	                            while (coursesResultSet.next()) {
	                                String title = coursesResultSet.getString("title");
	                                int grade = coursesResultSet.getInt("grade");	                                
	                                if (grade == 0) {
	                                    itemList.add(title + ": Not graded");
	                                } else {
	                                    itemList.add(title + " - Grade: " + grade);
	                                }
	                            }
	                            list.setListData(itemList.toArray(new String[0]));
	                          
	                            coursesResultSet.close();
	                        } else {
	                            idDisplay.setText("Student Doesnt Exist");
	                        }
	                    } else {
	                        addressDisplay.setText("Input(s) Empty.Try Again");
	                    }
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                } finally {
	                    try {
	                        if (resultSet != null) resultSet.close();
	                        if (statement != null) statement.close();
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        });
	        searchStudent.setBounds(74, 115, 89, 23);
	        frame.getContentPane().add(searchStudent);
	    }	
	
	
	//METHODS

	private void updateList(int studentID) {
	    try {
	        // Prepare and execute SQL query to fetch courses and grades for the selected student
	        String coursesQuery = "SELECT courses.title, grades.grade FROM courses INNER JOIN grades ON courses.id = grades.courses_id WHERE grades.student_id = " + studentID;
	        statement = connection.createStatement();
	        ResultSet coursesResultSet = statement.executeQuery(coursesQuery);

	        // Populate list of courses and grades
	        List<String> itemList = new ArrayList<>();
	        while (coursesResultSet.next()) {
	            String title = coursesResultSet.getString("title");
	            int grade = coursesResultSet.getInt("grade");
	            itemList.add(title + " - Grade: " + grade);
	        }

	        // Update the JList with the courses and grades
	        list.setListData(itemList.toArray(new String[0]));

	        
	        coursesResultSet.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
}
