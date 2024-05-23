import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddStudent {

	private Connection connection;

    public AddStudent(Connection connection) {
        this.connection = connection;
    }
	
	private JFrame frame;
	private JTextField firstNameText;
	private JTextField lastNameText;
	private JTextField addressText;
	
//	public static void newStudentScreen(Connection connection) {

	/**
	 * Launch the application.
	 */
	public static void newStudentScreen(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddStudent window = new AddStudent(connection);
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
	public AddStudent() {
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
		
		JLabel lblNewLabel = new JLabel("Add new Student");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Sitka Text", Font.PLAIN, 18));
		lblNewLabel.setBounds(142, 0, 156, 48);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(54, 52, 71, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(54, 83, 71, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Birthday");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(54, 117, 71, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Gender");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(54, 157, 71, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Address");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(54, 188, 71, 14);
		frame.getContentPane().add(lblNewLabel_5);
		
		JLabel successText = new JLabel("Student Added Successfully!!");
		successText.setVisible(false);
		successText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		successText.setHorizontalAlignment(SwingConstants.CENTER);
		successText.setBounds(64, 216, 283, 30);
		frame.getContentPane().add(successText);
		
		
		//Setting Variables For Inputs
		
		firstNameText = new JTextField();
		firstNameText.setBounds(135, 49, 149, 20);
		frame.getContentPane().add(firstNameText);
		firstNameText.setColumns(10);
		
		lastNameText = new JTextField();
		lastNameText.setBounds(135, 80, 149, 20);
		frame.getContentPane().add(lastNameText);
		lastNameText.setColumns(10);
		
		addressText = new JTextField();
		addressText.setBounds(135, 185, 149, 20);
		frame.getContentPane().add(addressText);
		addressText.setColumns(10);
		
		final String[] firstName = {""};
		final String[] lastName = {""};
		final String[] address = {""};
  		
		
		//CODE FOR CHECKBOX
		
		JCheckBox maleCheckBox = new JCheckBox("Male");
		maleCheckBox.setBounds(131, 153, 97, 23);
		frame.getContentPane().add(maleCheckBox);

		JCheckBox femaleCheckBox = new JCheckBox("Female");
		femaleCheckBox.setBounds(225, 153, 97, 23);
		frame.getContentPane().add(femaleCheckBox);

		ButtonGroup group = new ButtonGroup();
		group.add(maleCheckBox);
		group.add(femaleCheckBox);

		final char[] gender = {' '};
	
		maleCheckBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {		         
		            femaleCheckBox.setSelected(false);
		            gender[0] = 'M';
		        }
		    }
		});
	
		femaleCheckBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {		            
		            maleCheckBox.setSelected(false);
		            gender[0] = 'F';
		        }
		    }
		});
		
		//CODE FOR DATE
		
		JDateChooser birthday = new JDateChooser();
		birthday.setBounds(134, 111, 150, 20);
		frame.getContentPane().add(birthday);	
		final String[] formattedDate = {""};
		
		//CODE FOR BUTTON
		
		JButton newStudentButton = new JButton("Submit");
		newStudentButton.setBackground(Color.GREEN);
		newStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 firstName[0] = firstNameText.getText();
				 lastName[0]  = lastNameText.getText();
				 address[0]    = addressText.getText();
				Date selectedDate = birthday.getDate();				        
		        if (selectedDate != null) {				            
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            formattedDate[0] = sdf.format(selectedDate);                       
		        } else {				          
		            System.out.println("No date selected.");
		        }
		        
		        String sql = "INSERT INTO students (fName, lName, birthday, gender, address) "
		        		+ "VALUES ('" + firstName[0] + "', '" + lastName[0] + "', '" + formattedDate[0] + "', '" + gender[0] + "', '" + address[0] + "')";
		        
		        if(!firstName[0].equals(" ") && !lastName[0].equals("") && !address[0].equals("")) {		        
		        try {
		        	Statement statement = connection.createStatement();
		        	int rowsAffected = statement.executeUpdate(sql);
                    if (rowsAffected > 0) {
                        System.out.println("Student added successfully: " + firstName);
                        successText.setVisible(true);
                    } else {
                        System.out.println("Failed to add student: " +firstName);	                      
                    }
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();                
   		        
   		    }
   		}else {
   			System.out.println("empty fields");
   		}
			}});
		newStudentButton.setBounds(335, 113, 89, 23);
		frame.getContentPane().add(newStudentButton);		
		
	}
}

			
