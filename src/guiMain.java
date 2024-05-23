import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import java.awt.Font;
import java.awt.Color;

public class guiMain {
	private Connection connection;
	private JFrame frame;
	private JButton addCourseButton;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	   
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {        
		JDBCPostgreSQLConnection app = new JDBCPostgreSQLConnection();
	    
	    try {
	        Connection connection = app.connect();
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    guiMain window = new guiMain(connection); // Pass the Connection object
	                    window.frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Create the application.
	 */
	public guiMain(Connection connection) {
		this.connection = connection;	
		initialize();
	}
	
	public guiMain() {			
		initialize();
	}



	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("School Project");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(137, 11, 145, 20);
		frame.getContentPane().add(lblNewLabel);
		
		//ADD COURSE BUTTON
		
		addCourseButton = new JButton("Add Course");
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCourse addCourseNewWindow = new AddCourse(connection);
				addCourseNewWindow.newCourseScreen(connection);
			}});	
		addCourseButton.setBounds(137, 55, 145, 23);
		frame.getContentPane().add(addCourseButton);
		
		//ADD STUDENT BUTTON
		
		JButton newWindow = new JButton("Add Student");
		newWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddStudent addStudentNewWindow = new AddStudent(connection);
				addStudentNewWindow.newStudentScreen(connection);
			}
		});
		newWindow.setBounds(137, 89, 145, 23);
		frame.getContentPane().add(newWindow);
		
		
		//SEARCH BUTTON
		
		btnNewButton = new JButton("Search ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchStudent searchStudentNewWindow = new SearchStudent(connection);
				searchStudentNewWindow.newSearchStudentWindow(connection);				
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(10, 149, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		//DELETE BUTTON
		
		btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete deleteNewWindow = new Delete(connection);
				deleteNewWindow.newDeleteScreen(connection);
			}
		});
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.setBounds(10, 183, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		//ASSIGN COURSE BUTTON
		
		btnNewButton_2 = new JButton("Assign Course");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				AssignCourse assignCourseNewWindow = new AssignCourse(connection);
				assignCourseNewWindow.newAssignCourseScreen(connection);
			}
		});
		btnNewButton_2.setBounds(137, 123, 145, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		
		//GRADE BUTTON
		
		btnNewButton_3 = new JButton("Grade ");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grade gradeNewWindow = new Grade(connection);
				gradeNewWindow.newGradeScreen(connection);
			}
		});
		btnNewButton_3.setBounds(137, 157, 145, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Update");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update updateNewWindow = new Update(connection);
				updateNewWindow.newUpdateScreen(connection);
			}
		});
		btnNewButton_4.setBackground(new Color(0, 128, 0));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setBounds(10, 217, 89, 23);
		frame.getContentPane().add(btnNewButton_4);}
	}



