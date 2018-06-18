import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class CustomerOperation
{
	CustomerOperation(Connection con, String userName)
	{
		JDialog addCustomerDialog = new JDialog();
		JLabel customerIDLabel = new JLabel("Customer Id");
		JLabel customerNameLabel = new JLabel("Customer Name");
		JLabel customerAddressLabel = new JLabel("Customer Address");
		JLabel customerContactLabel = new JLabel("Customer Contact");
		JTextField customerIDTextField = new JTextField();
		JTextField customerNameTextField = new JTextField();
		JTextField customerAddressTextField = new JTextField();
		JTextField customerContactTextField = new JTextField();
		JButton addcustomerToDB = new JButton("Add customer");
		JButton clearFields = new JButton("Clear");
		addCustomerDialog.setSize(350,350);
		addCustomerDialog.setLocationRelativeTo(null);
		addCustomerDialog.setLayout(new GridLayout(5,2,5,5));
		addCustomerDialog.add(customerIDLabel);
		addCustomerDialog.add(customerIDTextField);
		addCustomerDialog.add(customerNameLabel);
		addCustomerDialog.add(customerNameTextField);
		addCustomerDialog.add(customerAddressLabel);
		addCustomerDialog.add(customerAddressTextField);
		addCustomerDialog.add(customerContactLabel);
		addCustomerDialog.add(customerContactTextField);
		addCustomerDialog.add(addcustomerToDB);
		addCustomerDialog.add(clearFields);
		addCustomerDialog.setVisible(true);
		addCustomerDialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				new EmployeePage(con, userName);
			}
		});
		addcustomerToDB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String customerID = customerIDTextField.getText().toString();
				String customerName = customerNameTextField.getText().toString();
				String customerAddress = customerAddressTextField.getText().toString();
				String customerContact = customerContactTextField.getText().toString();  
				System.out.println("r1");
				try
				{
					PreparedStatement stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_customer (customer_ID, customer_NAME, customer_address, customer_contact) VALUES (?,?,?,?)");
					stmt.setString(1,customerID);
					stmt.setString(2,customerName);
					stmt.setString(3,customerAddress);
					stmt.setString(4,customerContact);
					stmt.executeUpdate();	
					
											new NotificationDialog("customer added");

				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		clearFields.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				customerIDTextField.setText(null);
				customerNameTextField.setText(null);
				customerAddressTextField.setText(null);
				customerContactTextField.setText(null);
			}
		});
	}			
}