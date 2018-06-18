import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class FoodItemOperations
{
	FoodItemOperations(int i, Connection con)
	{
		if(i == 1)
		{
			JDialog addMenuDialog = new JDialog();
			JLabel menuNameLabel = new JLabel("Menu Name");
			JLabel menuPriceLabel = new JLabel("Menu Price");
			JTextField menuNameTextField = new JTextField();
			JTextField menuPriceTextField = new JTextField();
			JButton addMenuToDB = new JButton("Add Menu");
			JButton clearFields = new JButton("Clear");
			addMenuDialog.setSize(250,250);
			addMenuDialog.setLocationRelativeTo(null);
			addMenuDialog.setLayout(new GridLayout(3,2,5,5));
			addMenuDialog.add(menuNameLabel);
			addMenuDialog.add(menuNameTextField);
			addMenuDialog.add(menuPriceLabel);
			addMenuDialog.add(menuPriceTextField);
			addMenuDialog.add(addMenuToDB);
			addMenuDialog.add(clearFields);
			addMenuDialog.setVisible(true);
			addMenuDialog.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent we)
				{
					new AdminPage(con);
				}
			});
			addMenuToDB.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					String menuName = menuNameTextField.getText().toString();
					int menuPrice = Integer.parseInt(menuPriceTextField.getText().toString());
					System.out.println("r1");
					try
					{
						PreparedStatement stmt = con.prepareStatement("INSERT INTO YUSUF.restaurant_menu (menu_name, menu_price) VALUES (?,?)");
						stmt.setString(1,menuName);
						stmt.setInt(2,menuPrice);
						stmt.executeUpdate();
												new NotificationDialog("menu added");

					}
					catch(Exception e)
					{
						System.out.println(e);
												new NotificationDialog("failed to add menu");

					}
				}
			});
			clearFields.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					menuNameTextField.setText(null);
					menuPriceTextField.setText(null);
				}
			});
		}
		if(i == 2)
		{
			JDialog deleteMenuDialog = new JDialog();
			JLabel menuNameLabel = new JLabel("Menu Name");
			JTextField menuNameTextField = new JTextField();
			JButton deleteByID = new JButton("Delete by ID");
			deleteMenuDialog.setSize(350,350);
			deleteMenuDialog.setLocationRelativeTo(null);
			deleteMenuDialog.setLayout(null);
			menuNameLabel.setBounds(40,40,100,40);
			menuNameTextField.setBounds(40,120,100,40);
			deleteByID.setBounds(40,180,110,40);
			deleteMenuDialog.add(menuNameLabel);
			deleteMenuDialog.add(menuNameTextField);
			deleteMenuDialog.add(deleteByID);
			deleteMenuDialog.setVisible(true);
			deleteMenuDialog.addWindowListener(new WindowAdapter()
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
					String menuName = menuNameTextField.getText().toString();  
					System.out.println("r2");
					try
					{
						PreparedStatement stmt = con.prepareStatement("Delete from YUSUF.restaurant_menu where menu_name = ?" );
						stmt.setString(1,menuName);
						stmt.executeUpdate();
												new NotificationDialog("menu deleted");

					}
					catch(Exception e)
					{
						System.out.println(e);
												new NotificationDialog("failed to delete menu");

					}
				}
			});
		}
	}
}