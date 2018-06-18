import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;
class OrderOperation
{
	static int totalPrice =0;
	int itemAdded = 0;
	OrderOperation(Connection con, String userName)
	{
		Vector<String> menus = new Vector<String>();
		try
		{
			PreparedStatement stmt = con.prepareStatement("select menu_name from YUSUF.Restaurant_menu");
			ResultSet rs = stmt.executeQuery();
			String menu = "";
			while(rs.next())
			{
				menu = rs.getString(1);
				menus.add(menu);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		JDialog orderDialog = new JDialog();
		JTextField billIDTextField = new JTextField();
		JLabel billIDLabel = new JLabel("Bill ID");
		//JLabel billDateLabel = new JLabel("Bill date");
		//JTextField billDateTextField = new JTextField();
		JLabel customerIdLabel = new JLabel("Customer ID");
		JTextField customerIDTextField = new JTextField();
		JLabel itemChooseLabel = new JLabel("Choose menu"); 
		JComboBox itemNameChoice = new JComboBox(menus);
		JButton checkPriceButton = new JButton("Check price");
		JLabel priceLabel = new JLabel("Rupees");
		JLabel quantityLabel = new JLabel("Quantity");
		JTextField quantityTextField = new JTextField();
		JLabel tips1 = new JLabel("If you want to add more items \npress the below button and add again");
		JLabel tips2 = new JLabel(" \n else close this dialog");
		JButton addMenu = new JButton("Add Menu");
		JButton addOrder = new JButton("Add Order");
		orderDialog.setSize(800,320);
		orderDialog.setLayout(new GridLayout(7,2,5,5));
		orderDialog.setLocationRelativeTo(null);
		orderDialog.add(billIDLabel);
		orderDialog.add(billIDTextField);
		//orderDialog.add(billDateLabel);
		//orderDialog.add(billDateTextField);
		orderDialog.add(customerIdLabel);
		orderDialog.add(customerIDTextField);
		orderDialog.add(itemChooseLabel);
		orderDialog.add(itemNameChoice);
		orderDialog.add(checkPriceButton);
		orderDialog.add(priceLabel);
		orderDialog.add(quantityLabel);
		orderDialog.add(quantityTextField);
		orderDialog.add(tips1);
		orderDialog.add(tips2);
		orderDialog.add(addMenu);
		orderDialog.add(addOrder);
		orderDialog.setVisible(true);
		orderDialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				if(itemAdded == 0)
				{
					new EmployeePage(con, userName);
				}
				else
				{
					new NotificationDialog("complete current order");
				}
			}
		});
		checkPriceButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					String selectedMenu = (String) itemNameChoice.getItemAt(itemNameChoice.getSelectedIndex());
					PreparedStatement stmt = con.prepareStatement("select menu_price from yusuf.restaurant_menu where menu_name = ?");
					stmt.setString(1,selectedMenu);
					ResultSet rs = stmt.executeQuery();
					rs.next();
					String selectedMenuPrice = rs.getString(1);
					priceLabel.setText(String.valueOf(selectedMenuPrice));
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		addMenu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				itemAdded++;
				int billID = Integer.parseInt(billIDTextField.getText().toString());
				String chosenMenu = (String) itemNameChoice.getItemAt(itemNameChoice.getSelectedIndex());
				int item_price = Integer.parseInt(priceLabel.getText());
				int quantity = Integer.parseInt(quantityTextField.getText().toString());
				try
				{
					PreparedStatement stmt = con.prepareStatement("insert into yusuf.restaurant_item_list (item_name,item_price,item_quantity,bill_id) values (?,?,?,?)");
					stmt.setString(1, chosenMenu);
					stmt.setInt(2, item_price);
					stmt.setInt(3,quantity);
					stmt.setInt(4,billID);
					stmt.executeUpdate();
					totalPrice += item_price * quantity;
					System.out.println("inserted item");
											new NotificationDialog("item inserted in order");

				}
				catch(Exception e)
				{
					System.out.println(e);
											new NotificationDialog("failed to insert item in order");

				}
			}
		});
		addOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				int billID = Integer.parseInt(billIDTextField.getText().toString());
				//String billDate = billDateTextField.getText().toString();
				String employeeID = userName;	//obtained from argument
				String customerId = customerIDTextField.getText().toString();
				JDialog taxDiscountDialog = new JDialog();
				JLabel taxLabel = new JLabel("Tax amount");
				JLabel discountLabel = new JLabel("Discount");
				JTextField taxTextField = new JTextField();
				JTextField discountTextField = new JTextField();
				JButton applyTaxDiscount = new JButton("Apply");
				JButton clear = new JButton("Clear");
				taxDiscountDialog.setSize(400,200);
				taxDiscountDialog.setLayout(new GridLayout(3,2,3,3));
				taxDiscountDialog.setLocationRelativeTo(null);
				taxDiscountDialog.add(taxLabel);
				taxDiscountDialog.add(taxTextField);
				taxDiscountDialog.add(discountLabel);
				taxDiscountDialog.add(discountTextField);
				taxDiscountDialog.add(applyTaxDiscount);
				taxDiscountDialog.add(clear);
				taxDiscountDialog.setVisible(true);
				taxDiscountDialog.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent we)
					{
						taxDiscountDialog.dispose();
					}
				});
				clear.addActionListener(new ActionListener()
				{	
					public void actionPerformed(ActionEvent ae)
					{
						taxTextField.setText(null);
						discountTextField.setText(null);
					}
				});
				applyTaxDiscount.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						int tax = Integer.parseInt(taxTextField.getText().toString());
						int discount = Integer.parseInt(discountTextField.getText().toString());
						try
						{
							PreparedStatement stmt = con.prepareStatement("insert into yusuf.restaurant_bill (bill_id,bill_date,bill_tax,bill_discount,customer_id,employee_id) values (?,?,?,?,?,?)");
							stmt.setInt(1,billID);
							stmt.setDate(2,java.sql.Date.valueOf(java.time.LocalDate.now()));
							stmt.setInt(3,tax);
							stmt.setInt(4,discount);
							stmt.setString(5,customerId);
							stmt.setString(6,employeeID);
							stmt.executeUpdate();
							System.out.println("ins 1");
							stmt = null;
						/*	stmt = con.prepareStatement("select item_price,item_quantity from yusuf.restaurant_item_list where bill_id = ?");
							stmt.setInt(1,billID);
							ResultSet rs = stmt.executeQuery();
							int item_price, item_quantity;
							int total_amt=0;
							while(rs.next())
							{
								item_price = rs.getInt(1);
								item_quantity = rs.getInt(2);
								int t = item_price * item_quantity;
								total_amt += t;
							}
							int pay_amt = total_amt + tax - discount;
							System.out.println(total_amt);
							System.out.println(pay_amt);
							stmt =null;	*/
							int pay_amt = totalPrice + tax - discount;
							stmt = con.prepareStatement("update YUSUF.restaurant_bill set bill_total_price =?, bill_pay_amount=? where bill_id = ?");
							stmt.setInt(1,totalPrice);
							stmt.setInt(2,pay_amt);
							stmt.setInt(3,billID);
							stmt.executeUpdate();
							System.out.println("ins 2");
							orderDialog.dispose();
							taxDiscountDialog.dispose();
							new NotificationDialog("order placed successfully");
							new EmployeePage(con,userName);
						}
						catch(Exception e)
						{
							e.printStackTrace();
							new NotificationDialog("failed to place order");
							new NotificationDialog("contact DBA-wrong record inserted");
						}
					}
				});
			}
		});
	}
}