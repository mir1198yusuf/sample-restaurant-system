import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class EmployeeOperation
{
	EmployeeOperation(int i, Connection con)
	{			
		if(i == 1)
		{
			JDialog addEmployeeDialog = new JDialog();
			JLabel employeeIDLabel = new JLabel("Employee Id");
			JLabel employeeNameLabel = new JLabel("Employee Name");
			JLabel employeeSalaryLabel = new JLabel("Employee Salary");
			JLabel employeeDesignationLabel = new JLabel("Employee Designation");
			JLabel branchIDLabel = new JLabel("Branch ID");
			JTextField employeeIDTextField = new JTextField();
			JTextField employeeNameTextField = new JTextField();
			JTextField employeeSalaryTextField = new JTextField();
			JTextField employeeDesignationTextField = new JTextField();
			JTextField branchIDTextField = new JTextField();
			JButton addEmployeeToDB = new JButton("Add Employee");
			JButton clearFields = new JButton("Clear");
			addEmployeeDialog.setSize(350,350);
			addEmployeeDialog.setLocationRelativeTo(null);
			addEmployeeDialog.setLayout(new GridLayout(6,2,5,5));
			addEmployeeDialog.add(employeeIDLabel);
			addEmployeeDialog.add(employeeIDTextField);
			addEmployeeDialog.add(employeeNameLabel);
			addEmployeeDialog.add(employeeNameTextField);
			addEmployeeDialog.add(employeeSalaryLabel);
			addEmployeeDialog.add(employeeSalaryTextField);
			addEmployeeDialog.add(employeeDesignationLabel);
			addEmployeeDialog.add(employeeDesignationTextField);
			addEmployeeDialog.add(branchIDLabel);
			addEmployeeDialog.add(branchIDTextField);
			addEmployeeDialog.add(addEmployeeToDB);
			addEmployeeDialog.add(clearFields);
			addEmployeeDialog.setVisible(true);
			addEmployeeDialog.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					new AdminPage(con);
				}
			});
			addEmployeeToDB.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					String employeeID = employeeIDTextField.getText().toString();
					String employeeName = employeeNameTextField.getText().toString();
					int employeeSalary = Integer.parseInt(employeeSalaryTextField.getText().toString());
					String employeeDesignation = employeeDesignationTextField.getText().toString();
					String branchID = branchIDTextField.getText().toString();  
					System.out.println("r1");
					try
					{
						if(employeeSalary >= 10000){
						PreparedStatement stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_employee_h1 (EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SALARY, EMPLOYEE_DESIGNATION,BRANCH_ID) VALUES (?,?,?,?,?)");
						stmt.setString(1,employeeID);
						stmt.setString(2,employeeName);
						stmt.setInt(3,employeeSalary);
						stmt.setString(4,employeeDesignation);
						stmt.setString(5,branchID);
						stmt.executeUpdate();	}
						else {
							PreparedStatement stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_employee_h2 (EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SALARY, EMPLOYEE_DESIGNATION,BRANCH_ID) VALUES (?,?,?,?,?)");
						stmt.setString(1,employeeID);
						stmt.setString(2,employeeName);
						stmt.setInt(3,employeeSalary);
						stmt.setString(4,employeeDesignation);
						stmt.setString(5,branchID);
						stmt.executeUpdate();	
						}
						
						PreparedStatement stmt1 = con.prepareStatement("INSERT INTO YUSUF.restaurant_employee (EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_SALARY, EMPLOYEE_DESIGNATION,BRANCH_ID) VALUES (?,?,?,?,?)");
						stmt1.setString(1,employeeID);
						stmt1.setString(2,employeeName);
						stmt1.setInt(3,employeeSalary);
						stmt1.setString(4,employeeDesignation);
						stmt1.setString(5,branchID);
						stmt1.executeUpdate();
						new NotificationDialog("employee inserted");
					}
					catch(Exception e)
					{
						System.out.println(e);
						new NotificationDialog("failed to insert employee");
					}
				}
			});
			clearFields.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					employeeIDTextField.setText(null);
					employeeNameTextField.setText(null);
					employeeDesignationTextField.setText(null);
					employeeSalaryTextField.setText(null);
					branchIDTextField.setText(null);
				}
			});
		}
		if(i == 2)
		{
			JDialog deleteEmployeeDialog = new JDialog();
			JLabel employeeIDLabel = new JLabel("Employee Id");
			JLabel employeeNameLabel = new JLabel("Employee Name");
			JTextField employeeIDTextField = new JTextField();
			JTextField employeeNameTextField = new JTextField();
			JButton deleteByID = new JButton("Delete by ID");
			JButton deleteByName = new JButton("Delete by Name");
			deleteEmployeeDialog.setSize(350,350);
			deleteEmployeeDialog.setLocationRelativeTo(null);
			deleteEmployeeDialog.setLayout(null);
			employeeIDLabel.setBounds(40,40,100,40);
			employeeNameLabel.setBounds(200,40,100,40);
			employeeIDTextField.setBounds(40,120,100,40);
			employeeNameTextField.setBounds(200,120,100,40);
			deleteByID.setBounds(40,180,110,40);
			deleteByName.setBounds(180,180,140,40);
			deleteEmployeeDialog.add(employeeIDLabel);
			deleteEmployeeDialog.add(employeeIDTextField);
			deleteEmployeeDialog.add(employeeNameLabel);
			deleteEmployeeDialog.add(employeeNameTextField);
			deleteEmployeeDialog.add(deleteByID);
			deleteEmployeeDialog.add(deleteByName);
			deleteEmployeeDialog.setVisible(true);
			deleteEmployeeDialog.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					new AdminPage(con);
				}
			});
			deleteByID.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					String employeeID = employeeIDTextField.getText().toString();  
					System.out.println("r2");
					try
					{
						PreparedStatement stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee where employee_id = ?" );
						stmt.setString(1,employeeID);
						stmt.executeUpdate();
						
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee_h1 where employee_id = ?" );
						stmt.setString(1,employeeID);
						stmt.executeUpdate();
						
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee_h2 where employee_id = ?" );
						stmt.setString(1,employeeID);
						stmt.executeUpdate();
						
						new NotificationDialog("employee deleted");
					}
					catch(Exception e)
					{
						System.out.println(e);
						new NotificationDialog("failed to delete employee");

					}
				}
			});
			deleteByName.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					String employeeName = employeeNameTextField.getText().toString();  
					System.out.println("r2");
					try
					{
						PreparedStatement stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee where employee_name = ?" );
						stmt.setString(1,employeeName);
						stmt.executeUpdate();
						
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee_h1 where employee_name = ?" );
						stmt.setString(1,employeeName);
						stmt.executeUpdate();
						
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_employee_h2 where employee_name = ?" );
						stmt.setString(1,employeeName);
						stmt.executeUpdate();
						
												new NotificationDialog("employee deleted");

					}
					catch(Exception e)
					{
						System.out.println(e);
												new NotificationDialog("failed to delete employee");

					}
				}
			});
		}
	}
}