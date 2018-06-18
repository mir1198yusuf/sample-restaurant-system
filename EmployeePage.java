import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class EmployeePage
{
	JFrame employeeFrame = new JFrame("EMPLOYEE PAGE");
	JButton addCustomerButton = new JButton("Add Customer");
	JButton makeOrderButton = new JButton("Make Food Order");
				
	EmployeePage(Connection con, String userName)
	{
		employeeFrame.setSize(400,300);
		employeeFrame.setLocationRelativeTo(null);
		employeeFrame.setLayout(null);
		employeeFrame.setVisible(true);
		addCustomerButton.setBounds(30,10,200,50);
		makeOrderButton.setBounds(30,100,200,50);
		employeeFrame.add(addCustomerButton);
		employeeFrame.add(makeOrderButton);
		employeeFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				try
				{
					con.close();
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				System.exit(1);
			}
		});
		addCustomerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				employeeFrame.setVisible(false);
				new CustomerOperation(con, userName);
			}
		});
		makeOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				employeeFrame.setVisible(false);				
				new OrderOperation(con, userName);
			}
		});
	}
}