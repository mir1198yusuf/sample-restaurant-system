import java.sql.*;
import javax.swing.*;
class ConnectionToDB
{
	ConnectionToDB(String userName, String password, String userType, JFrame mainFrame)
	{
		Connection con = null;
		try
		{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.OracleDriver");  
			//step2 create  the connection object  
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","yusuf");  
			System.out.println("connected to oracle database");
			new NotificationDialog("connecting to oracle...");
			//step3 create the statement object  
			PreparedStatement stmt=con.prepareStatement("select restaurant_user_password from YUSUF.restaurant_authentication where restaurant_user_id like ? and restaurant_user_type like ?");  
			stmt.setString(1,userName);
			stmt.setString(2,userType);
			//step4 execute query  
			ResultSet rs=stmt.executeQuery(); 
			rs.next();	// if no row selected resultset will get exhausted means username not exists
			if((rs.getString(1)).equals(password))
			{
				new NotificationDialog("authentication successful...");
				//System.out.println("right");
				mainFrame.setVisible(false);
			}
			else	
			{
				//System.out.println("wrong password");
				new NotificationDialog("authentication failed: wrong password");
				con.close();
				return;
			} 
			if(userType.equals("Admin"))
			{
				new AdminPage(con);
			}
			else if(userType.equals("Employee"))
			{
				new EmployeePage(con, userName);
			}
		}
		catch(SQLException sqle)
		{
			System.out.println("username doesnot exists");
			new NotificationDialog("authentication failed: username not exists");
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
	}
}