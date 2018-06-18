import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class BranchOperations
{
	BranchOperations(int i, Connection con)
	{
		if(i == 1)
		{
			JDialog addBranchDialog = new JDialog();
			JLabel branchIDLabel = new JLabel("Branch Id");
			JLabel branchAddressLabel = new JLabel("Branch Address");
			JLabel managerNameLabel = new JLabel("Manager Name");
			JLabel managerDurationLabel = new JLabel("Manager Duration");
			JTextField branchIDTextField = new JTextField();
			JTextField branchAddressTextField = new JTextField();
			JTextField managerNameTextField = new JTextField();
			JTextField managerDurationtextField = new JTextField();
			JButton addBranchToDB = new JButton("Add Branch");
			JButton clearFields = new JButton("Clear");
			addBranchDialog.setSize(350,350);
			addBranchDialog.setLocationRelativeTo(null);
			addBranchDialog.setLayout(new GridLayout(5,2,5,5));
			addBranchDialog.add(branchIDLabel);
			addBranchDialog.add(branchIDTextField);
			addBranchDialog.add(branchAddressLabel);
			addBranchDialog.add(branchAddressTextField);
			addBranchDialog.add(managerNameLabel);
			addBranchDialog.add(managerNameTextField);
			addBranchDialog.add(managerDurationLabel);
			addBranchDialog.add(managerDurationtextField);
			addBranchDialog.add(addBranchToDB);
			addBranchDialog.add(clearFields);
			addBranchDialog.setVisible(true);
			addBranchDialog.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					new AdminPage(con);
				}
			});
			addBranchToDB.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					String branchID = branchIDTextField.getText().toString();
					String branchAddress = branchAddressTextField.getText().toString();
					String managerName = managerNameTextField.getText().toString();
					String managerDuration = managerDurationtextField.getText().toString();  
					System.out.println("r1");
					try
					{
						PreparedStatement stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_branch_v1 (branch_id, branch_address) VALUES (?,?)");
						stmt.setString(1,branchID);
						stmt.setString(2,branchAddress);
						//stmt.setString(3,managerName);
						//stmt.setString(4,managerDuration);
						stmt.executeUpdate();
						stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_branch_v2 (branch_id,branch_manager_name, branch_manager_duration) VALUES (?,?,?)");
						stmt.setString(1,branchID);
						//stmt.setString(2,branchAddress);
						stmt.setString(2,managerName);
						stmt.setString(3,managerDuration);
						stmt.executeUpdate();
						
						stmt = null;						
						stmt= con.prepareStatement("insert into yusuf.restaurant_branch (branch_id,branch_address) select branch_id,branch_address from yusuf.restaurant_branch_v1 where not exists (select branch_id from yusuf.restaurant_branch where (yusuf.restaurant_branch_v1.branch_id = yusuf.restaurant_branch.branch_id))");
						stmt.executeUpdate();
					
						stmt = null;
						stmt =con.prepareStatement("UPDATE yusuf.restaurant_branch T1 SET T1.branch_id = (SELECT T2.branch_id FROM yusuf.restaurant_branch_v2 T2 WHERE T2.branch_id = T1.branch_id), T1.branch_manager_duration = (SELECT T2.branch_manager_duration FROM yusuf.restaurant_branch_v2 T2 WHERE T2.branch_id = T1.branch_id), T1.branch_manager_name = (SELECT T2.branch_manager_name FROM yusuf.restaurant_branch_v2 T2 WHERE T2.branch_id = T1.branch_id) WHERE T1.branch_id IN (SELECT T2.branch_id FROM yusuf.restaurant_branch_v2 T2 WHERE T2.branch_id = T1.branch_id)");
						stmt.executeUpdate();
						
												new NotificationDialog("branch added");

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
					branchIDTextField.setText(null);
					branchAddressTextField.setText(null);
					managerNameTextField.setText(null);
					managerDurationtextField.setText(null);
				}
			});
		}
		if(i == 2)
		{
			JDialog deleteBranchDialog = new JDialog();
			JLabel branchIDLabel = new JLabel("Branch Id");
			JTextField branchIDTextField = new JTextField();
			JButton deleteByID = new JButton("Delete by ID");
			deleteBranchDialog.setSize(350,350);
			deleteBranchDialog.setLocationRelativeTo(null);
			deleteBranchDialog.setLayout(null);
			branchIDLabel.setBounds(40,40,100,40);
			branchIDTextField.setBounds(40,120,100,40);
			deleteByID.setBounds(40,180,110,40);
			deleteBranchDialog.add(branchIDLabel);
			deleteBranchDialog.add(branchIDTextField);
			deleteBranchDialog.add(deleteByID);
			deleteBranchDialog.setVisible(true);
			deleteBranchDialog.addWindowListener(new WindowAdapter()
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
					String branchID = branchIDTextField.getText().toString();  
					System.out.println("r2");
					try
					{
						PreparedStatement stmt = con.prepareStatement("Delete from YUSUF.restaurant_branch where branch_id = ?" );
						stmt.setString(1,branchID);
						stmt.executeUpdate();
						
						stmt =null;
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_branch_v1 where branch_id = ?" );
						stmt.setString(1,branchID);
						stmt.executeUpdate();

						stmt = null;
						stmt = con.prepareStatement("Delete from YUSUF.restaurant_branch_v2 where branch_id = ?" );
						stmt.setString(1,branchID);
						stmt.executeUpdate();
						
												new NotificationDialog("failed to delete branch");

					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
			});
		}
	}
}