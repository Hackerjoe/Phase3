package airbnbModels;

import java.sql.*;

public class DBConnection {
	
	public static Connection getConnection() 
	{
		try {
			String userName = "5530u37";
            String password = "c711r33s";
            String url = "jdbc:mysql://georgia.eng.utah.edu/5530db37";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            System.out.println("Attempting to connect to database");
            Connection con = DriverManager.getConnection (url, userName, password);
            return con;
		}
		catch(Exception ex)
		{
			System.out.println("Unable to connect to database.");
			System.out.println(ex.getMessage());
			return null;
		}
	}

}
