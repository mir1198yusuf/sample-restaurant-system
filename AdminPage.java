import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class AdminPage
{
	JFrame adminFrame = new JFrame("ADMIN PAGE");
	JButton addEmployeeButton = new JButton("Add Employee");
	JButton deleteEmployeeButton = new JButton("Delete Employee");
	JButton addFoodItemButton = new JButton("Add food item");
	JButton deleteFoodItemButton = new JButton("Delete food item");
	JButton addBranchButton = new JButton("Add Branch");
	JButton deleteBranchButton = new JButton("Delete Branch");
				
	AdminPage(Connection con)
	{
		adminFrame.setSize(500,600);
		adminFrame.setLocationRelativeTo(null);
		adminFrame.setLayout(null);
		adminFrame.setVisible(true);
		addEmployeeButton.setBounds(30,10,200,50);
		deleteEmployeeButton.setBounds(30,70,200,50);
		addBranchButton.setBounds(30,200,200,50);
		deleteBranchButton.setBounds(30,260,200,50);
		addFoodItemButton.setBounds(30,380,200,50);
		deleteFoodItemButton.setBounds(30,440,200,50);
		adminFrame.add(addEmployeeButton);
		adminFrame.add(deleteEmployeeButton);
		adminFrame.add(addBranchButton);
		adminFrame.add(deleteBranchButton);
		adminFrame.add(addFoodItemButton);
		adminFrame.add(deleteFoodItemButton);
		adminFrame.addWindowListener(new WindowAdapter()
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
		addEmployeeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);
				new EmployeeOperation(1, con);
			}
		});
		deleteEmployeeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);				
				new EmployeeOperation(2, con);
			}
		});
		addBranchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);
				new BranchOperations(1, con);
			}
		});
		deleteBranchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);
				new BranchOperations(2, con);
			}
		});
		addFoodItemButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);
				new FoodItemOperations(1, con);
			}
		});
		deleteFoodItemButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				adminFrame.setVisible(false);
				new FoodItemOperations(2, con);
			}
		});
	}
}