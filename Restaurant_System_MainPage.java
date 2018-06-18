import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.sql.*;
class Restaurant_System_MainPage
{
	JFrame mainFrame = new JFrame("RESTAURANT SYSTEM");
	JLabel nameLabel = new JLabel("Welcome to Restaurant System");
	JLabel loginLabel = new JLabel("Login Window");
	JLabel userTypeLabel = new JLabel("Enter type of user");
	String userTypes[] = {"Admin", "Employee"};
	JComboBox userTypeChoice = new JComboBox(userTypes);
	JLabel userNameLabel = new JLabel("Enter username");
	JLabel passwordLabel = new JLabel("Enter password");
	JTextField usernameTextField = new JTextField();
	JPasswordField passwordTextField = new JPasswordField();
	JButton loginButton = new JButton("Click to Login");
	
	public static void main(String args[])
	{
		new Restaurant_System_MainPage();
	}
	
	Restaurant_System_MainPage()
	{
		mainFrame.setSize(400,700);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(null);
		nameLabel.setBounds(100,50,190,30);
		loginLabel.setBounds(115,115,100,25);
		userTypeLabel.setBounds(40,180,120,30);
		userTypeChoice.setBounds(180,180,120,30);
		userNameLabel.setBounds(40,250,120,30);
		usernameTextField.setBounds(180,250,120,30);
		passwordLabel.setBounds(40,300,120,30);
		passwordTextField.setBounds(180,300,120,30);
		loginButton.setBounds(120,400,150,40);
		mainFrame.add(nameLabel);
		mainFrame.add(loginLabel);
		mainFrame.add(userTypeLabel);
		mainFrame.add(userTypeChoice);
		mainFrame.add(userNameLabel);
		mainFrame.add(passwordLabel);
		mainFrame.add(usernameTextField);
		mainFrame.add(passwordTextField);
		mainFrame.add(loginButton);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		loginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String userName = usernameTextField.getText().toString();
				String password = passwordTextField.getText().toString();
				String userType = (String) userTypeChoice.getItemAt(userTypeChoice.getSelectedIndex());
				new ConnectionToDB(userName, password, userType, mainFrame);
			}
		});
	}
}
