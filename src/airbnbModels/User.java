package airbnbModels;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class User implements Serializable {
	
	public String name;
	public String username;
	public String password;
	public String address;
	public String phone;
	
	public User(String name, String username, String password, int type, String address, String phone)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.address = address;
		this.phone = phone;
	}
	
	public User(String _username, String _password) {
		username = _username;
		password = _password;
	}
	
	public static User AuthenticateUser(String username, String password)
	{
		Connection con = DBConnection.getConnection();
		System.out.println("connected to the database");
	 	if(con == null) {
			return null;
		}
		
		String query;
        query = "SELECT * FROM Users WHERE Login = ?;";
        ResultSet rs = null;
        
        try {
            // set up query
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, username);
            rs = preparedStmt.executeQuery();
            
            // get the password
            rs.next();
            String dbPassword = rs.getString("Password");
            
            // check validity
            if (password.equals(dbPassword)) {
                String dbName = rs.getString("Name");
                String dbLogin = rs.getString("Login");
                int dbType = rs.getInt("userType");
                String dbAddress = rs.getString("Address");
                String dbPhoneNumebr = rs.getString("PhoneNumber");
                User user = new User(dbName, dbLogin, dbPassword, dbType, dbAddress, dbPhoneNumebr);
                return user;
            }
            else
                return null;
            
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            return null;
        }
	}
	
	public static User InsertUser(String Name, String Login, int type, String Password, String Address, String PhoneNumber)
    {
		Connection con = DBConnection.getConnection();
		System.out.println("connected to the database");
	 	if(con == null) {
			return null;
		}
		
        //INSERT INTO Users (Name, Login, userType, Password, Address, PhoneNumber) VALUES ('Joey Despain','Jdlog','0','password','123 street','5556666');
        String query;
        
        query= "INSERT INTO Users (Name, Login, userType, Password, Address, PhoneNumber) "+"VALUES (?, ?, ?, ?, ?,?)";
        
        try{
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, Name);
            preparedStmt.setString (2, Login);
            preparedStmt.setInt(3, type);
            preparedStmt.setString(4, Password);
            preparedStmt.setString(5, Address);
            preparedStmt.setString(6, PhoneNumber);
            preparedStmt.execute();
            User user = new User(Name, Login, Password, type, Address, PhoneNumber);
            return user;
            
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            return null;
        }
    }

	
	public String toString() {
		return "username: " + username + " password: " + password;
	}
	
    static public void RateFeedback( int fid, String Login, int score) throws Exception
    {
    	Connection con = DBConnection.getConnection();
    	//insert into Rates (fid,Login,rating) values (1,'joeyDD','2');
    	String query;

		query= "INSERT INTO Rates (fid, Login, rating) "+"VALUES (?, ?, ?);";
		
		try{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setInt(1, fid);
		      preparedStmt.setString (2, Login);
		      preparedStmt.setInt (3, score);

		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
    }
    
    public static List<Reserve> getReserves(String Login) throws Exception
    {
    	Connection con = DBConnection.getConnection();
    	//select * from Reserve where Login = 'joeyDD';
    	String query;
        ResultSet results;
        query = "select * from Reserve where Login = '"+Login+"';";
        try{
            results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
        }
        if(!results.isBeforeFirst())
        {
            return null;
        }
        List<Reserve> newList = new ArrayList<Reserve>();
        while(results.next())
        {
        	Reserve res = new Reserve();
        	res.cost = results.getFloat("cost");
        	res.hid = results.getInt("hid");
        	res.pid = results.getInt("pid");
        	newList.add(res);
        }
        
    	return newList;
    }
    
    public static void addVisit(float cost, String Login, int hid, int pid) throws Exception
    {
    	Connection con = DBConnection.getConnection();
    	String query;
		query= "insert into Visits (cost,Login,hid,pid) values (?,?,?,?);";
		try
		{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setFloat(1, cost);
			  preparedStmt.setString(2,Login);
		      preparedStmt.setInt(3,hid);
		      preparedStmt.setInt(4,pid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
    }
    
    
    public static ArrayList<Visit> getVisits(String Login) throws Exception
    {
    	Connection con = DBConnection.getConnection();
    	String query;
        ResultSet results;
        query = "select * from Visits where Login = '"+Login+"';";
        try{
            results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
        }
        ArrayList<Visit> visits = new ArrayList<Visit>();
        if(!results.isBeforeFirst())
        {
            return visits;
        }

        while(results.next())
        {
            int hid = results.getInt("hid");
        	float cost = results.getFloat("cost");
        	int pid = results.getInt("pid");
        	Visit newVisit = new Visit();
        	newVisit.cost = cost;
        	newVisit.hid = hid;
        	newVisit.pid = pid;
        	newVisit.Login = Login;
        	visits.add(newVisit);
        }
        return visits;
    }
    
}
