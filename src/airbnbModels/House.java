package airbnbModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class House {
	
	public String category;
	public String name;
	public String address;
	public String url;
	public String phone;
	public String year;
	public String ownerUsername;
	
	public House(String category, String name, String address, String url, String phone, String year, String ownerUsername)
	{
		this.category = category;
		this.name = name;
		this.address = address;
		this.url = url;
		this.phone = phone;
		this.year = year;
		this.ownerUsername = ownerUsername;
	}
	
	public static House InsertHouse(String Category, String Name, String Address, String URL, String PhoneNumber, String Year, String UserLogin)
    {
		Connection con = DBConnection.getConnection();
		System.out.println("connected to the database");
	 	if(con == null) {
			return null;
		}

	 	String query;
		query= "INSERT INTO THs (Category, Name, Address, URL, PhoneNumber, Year, Login) "+"VALUES (?, ?, ?, ?, ?,?,?);";
		
		try{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setString (1, Category);
		      preparedStmt.setString (2, Name);
		      preparedStmt.setString (3, Address);
		      preparedStmt.setString(4, URL);
		      preparedStmt.setString(5, PhoneNumber);
		      preparedStmt.setString(6, Year);
		      preparedStmt.setString(7, UserLogin);
		      preparedStmt.execute();
		      House house = new House(Category, Name, Address, URL, PhoneNumber, Year, UserLogin);
		      return house;
			 
        } catch(Exception e) {
        	System.out.println("unable to insert house");
			System.err.println("Unable to execute query:"+query+"\n");
	        System.err.println(e.getMessage());
			return null;
		}
    }
	
	public static House GetHouseWithID(int ID)
	{
		Connection con = DBConnection.getConnection();
		System.out.println("connected to the database");
	 	if(con == null) {
			return null;
		}
	 	
	 	String query;
		query= "SELECT * FROM THs h WHERE h.hid = ?";
		ResultSet rs = null;
		
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt (1, ID);
			rs = preparedStmt.executeQuery();
			
			rs.next();
	        String dbCategory = rs.getString("Category");
	        String dbName = rs.getString("Name");
	        String dbAddress = rs.getString("Address");
	        String dbURL = rs.getString("URL");
	        String dbPhoneNumber = rs.getString("PhoneNumber");
	        String dbYear = rs.getString("Year");
	        String dbOwnerLogin = rs.getString("Login");
	         
	        House house = new House(dbCategory, dbName, dbAddress, dbURL, dbPhoneNumber, dbYear, dbOwnerLogin);
		    return house;
		}
		catch (Exception e) {
			System.out.println("unable to get house");
			System.err.println("Unable to execute query:"+query+"\n");
	        System.err.println(e.getMessage());
			return null;
		}	
	}
	
	// havent tried this out yet
	public static ArrayList<House> GetHousesOwnedBy(String ownerUsername)
	{
		Connection con = DBConnection.getConnection();
		System.out.println("connected to the database");
	 	if(con == null) {
			return null;
		}
	 	
	 	String query;
		query= "SELECT * FROM THs h WHERE h.Login = ?";
		ResultSet rs = null;
		ArrayList<House> houseArray = new ArrayList<House>();
		
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString (1, ownerUsername);
			rs = preparedStmt.executeQuery();
			
			while(rs.next()) 
			{
				String dbCategory = rs.getString("Category");
		        String dbName = rs.getString("Name");
		        String dbAddress = rs.getString("Address");
		        String dbURL = rs.getString("URL");
		        String dbPhoneNumber = rs.getString("PhoneNumber");
		        String dbYear = rs.getString("Year");
		        String dbOwnerLogin = rs.getString("Login");
		         
		        House house = new House(dbCategory, dbName, dbAddress, dbURL, dbPhoneNumber, dbYear, dbOwnerLogin);
			    houseArray.add(house);
			}
	        
			return houseArray;
		}
		catch (Exception e) {
			System.out.println("unable to get house");
			System.err.println("Unable to execute query:"+query+"\n");
	        System.err.println(e.getMessage());
			return null;
		}	
		
		
	}
	
	public static ArrayList<Period> GetAvailableDates( int HotelId) throws Exception
	{
		Connection con = DBConnection.getConnection();
		String query;
		ResultSet results;
		query = "select A.PPN,P.start,P.end from Available A, Period P where P.pid = A.pid and A.hid ="+ HotelId+";";
		try{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		ArrayList<Period> periods = new ArrayList<Period>();
		if(!results.isBeforeFirst())
		{
			return periods;
		}
		else
		{
			System.out.println("--> This Hotel is available:");
			while(results.next())
			{
				
				String start = results.getString("start");
				String end = results.getString("end");
				float PPN = results.getFloat("PPN");
				System.out.println("--> Start date: " + start +" End Date: "+ end +" Price Per Night: $" +PPN);
				periods.add(new Period(start, end, -1,PPN));
			}
		}
		return periods;
	}
	
	
	public static float CheckIfDatesAreAvailable(Connection con,int HotelId, String start, String end) throws Exception
	{
		if(con == null)
			con = DBConnection.getConnection();
		
		String query;
		ResultSet results;
		query = "select * from Available A inner join Period P on P.pid where A.hid = "+HotelId+"   and start <= '" + start +"'  and end >='" +end +"' ;";
		try{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return -1;
		}
		
		while(results.next())
		{
			float PPN = results.getFloat("PPN");
			return PPN;
		}
		return -1;
	}
	
	public static void SQLReserveDate( String Start, String End,float cost, String UserLogin, int hid) throws Exception
	{
		Connection con = DBConnection.getConnection();
		int pid = CreatePeriod(con, Start, End);
		int APid = getPidOfDates(con, hid, Start, End);
		String query;
		query= "INSERT INTO Reserve (Cost, Login, hid, pid) "+"VALUES (?, ?, ?, ?);";
		try
		{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setFloat(1,cost);
		      preparedStmt.setString(2,UserLogin);
		      preparedStmt.setInt(3, hid);
		      preparedStmt.setInt(4, pid);
		      preparedStmt.execute();
		      SQLSplitAvailable(APid, hid, Start, End, con);
		      System.out.println("Splited!");
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
		
		
	}
	
	static int CreatePeriod(Connection con, String Start, String End) throws Exception
	{
		int pid = CheckIfDateExist(con, Start, End);
		if(pid != -1)
			return pid;
		
		String query;
		query= "INSERT INTO Period (start, end) "+"VALUES (?, ?);";
		try
		{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setDate(1,java.sql.Date.valueOf(Start));
		      preparedStmt.setDate(2, java.sql.Date.valueOf(End));
		      preparedStmt.execute();
		      pid = CheckIfDateExist(con, Start, End);
		      if(pid == -1)
		      {
		    	  Exception e = new Exception("ERROR Could not find period!");
		    	  throw e;
		      }
		      System.out.println("Created Period!");
		      return pid;
		      
		} 
		catch(Exception e)
		{
            throw(e);
		}
		
	}
	
	static int CheckIfDateExist(Connection con, String Start, String End) throws Exception
	{
		
		String query;
		ResultSet results;
		query = "select * from Period where start = '"+ Start +"' and end='"+End+"';";
		try{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
        	System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return -1;
		}
		else
		{
			results.next();
			return results.getInt("pid");
		}
	}
	
	
	static int getPidOfDates(Connection con, int hid, String start, String end) throws Exception
	{
		String query;
		ResultSet results;
		query = "select * from Available A inner join Period P on P.pid where A.hid = "+hid+"  and start <= '" + start +"'  and end >='" +end +"' ;";
		try{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return -1;
		}
		
		while(results.next())
		{
			int pid = results.getInt("pid");
			System.out.println("Got PID!");
			return pid;
		}
		return -1;
	}
	
	
	static void SQLSplitAvailable(int APid,int hid, String start, String end, Connection con) throws Exception
	{
		float PPN = CheckIfDatesAreAvailable(con, hid, start, end);
		Period ParentPeriod = getPeriod(APid,con);
		DeleteAvailable(APid, hid, con);
		
		if(Period.ConvertCompare(ParentPeriod.start,start)  == 0 && Period.ConvertCompare(ParentPeriod.end,end) == 0)
		{
			// They are equal
			return;
		}
		else if(Period.ConvertCompare(start,ParentPeriod.start) > 0 && Period.ConvertCompare(ParentPeriod.end,end) > 0)
		{
			// Middle case where we need to create two available.
			int newPid = CreatePeriod(con,ParentPeriod.start,start);
			int newPid2 = CreatePeriod(con, end, ParentPeriod.end);
			AddAvailable(newPid, hid,PPN, con);
			AddAvailable(newPid2, hid,PPN, con);
		}
		else if(Period.ConvertCompare(start,ParentPeriod.start) == 0)
		{
			//Starts are the same.
			int newPid = CreatePeriod(con, end, ParentPeriod.end);
			AddAvailable(newPid, hid,PPN, con);
			return;
		}
		else if(Period.ConvertCompare(ParentPeriod.end,end) < 0)
		{
			int newPid = CreatePeriod(con, ParentPeriod.start, start);
			AddAvailable(newPid, hid,PPN, con);
			return;
		}

	}
	
	static void DeleteAvailable(int pid,int hid, Connection con) throws Exception
	{
		String query;
		query= "delete from Available where pid = ? and hid = ?;";
		try
		{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setInt(1,pid);
		      preparedStmt.setInt(2,hid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
	}
	
	static void AddAvailable(int pid,int hid,float PPN, Connection con) throws Exception
	{
		String query;
		query= "insert into Available (PPN,hid,pid) values (?,?,?);";
		try
		{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setFloat(1, PPN);
			  preparedStmt.setInt(2,hid);
		      preparedStmt.setInt(3,pid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
	}
	
	public static Period getPeriod(int pid, Connection con) throws Exception
	{
		//select * from Period where pid = 5;
		if(con == null)
			con = DBConnection.getConnection();
		String query;
		ResultSet results;
		query = "select * from Period where pid = " +pid+";";
		
		try
		{
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
		results.next();
		return new Period(results.getString("start"),results.getString("end"),pid,null);
	}
	
	
	
	static boolean CheckForFeedback(Connection con, String Login, int Hid) throws Exception
	{
		String query;
		ResultSet results;
		query = "select * from Feedback where Login = '" +Login+"' and hid ="+Hid+";";
		
		try
		{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static List<FeedBack> getFeedBackForHouse(int Hid) throws Exception
	{
		Connection con = DBConnection.getConnection();
		String query;
		ResultSet results;
		List<FeedBack> returnList = new ArrayList<FeedBack>();
		query = "select * from Feedback where hid ="+Hid+";";
		
		try
		{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return returnList;
		}
		else
		{
			while(results.next())
			{
				FeedBack newFeed = new FeedBack();
				newFeed.date = results.getString("fbdate");
				newFeed.fid = results.getInt("fid");
				newFeed.hid = Hid;
				newFeed.Login = results.getString("Login");
				newFeed.message = results.getString("Text");
				newFeed.score = results.getInt("Score");
				returnList.add(newFeed);
			}
		}
		return returnList;
	}
	//select avg(rating) as rate,F.text, F.fbdate,F.Login from Rates R, Feedback F where F.fid = R.fid  group by R.fid order by rate DESC limit 5;
	
	public static List<FeedBack> getFeedBackForHouseTop(int Hid,int n) throws Exception
	{
		Connection con = DBConnection.getConnection();
		String query;
		ResultSet results;
		List<FeedBack> returnList = new ArrayList<FeedBack>();
		query = "select avg(rating) as rate,F.text, F.fbdate,F.Login, F.fid,F.Score from Rates R, Feedback F where F.fid = R.fid and F.hid = "+Hid+" group by R.fid,F.hid order by rate DESC limit "+n+";";
		
		try
		{
			results = con.createStatement().executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return returnList;
		}
		else
		{
			while(results.next())
			{
				FeedBack newFeed = new FeedBack();
				newFeed.date = results.getString("F.fbdate");
				newFeed.score = results.getInt("F.Score");
				newFeed.fid = results.getInt("F.fid");
				newFeed.hid = Hid;
				newFeed.Login = results.getString("F.Login");
				newFeed.message = results.getString("F.Text");
				returnList.add(newFeed);
			}
		}
		return returnList;
	}
	
	public static void CreateFeedBack( String Login, int Hid, String message,int Score) throws Exception
	{
		Connection con = DBConnection.getConnection();
		if(CheckForFeedback(con, Login, Hid) == true)
		{
			Exception e = new Exception("Already gave feedback");
			throw(e);
			
		}
			
		//insert into Feedback (hid,Login,fbdate,Text,Score) values ('1','joeyDD','2017-01-01','It was a place',10);
		String query;

		query= "insert into Feedback (hid,Login,fbdate,Text,Score) "+"VALUES (?, ?, CURDATE(), ?, ?);";
		
		try{
			  PreparedStatement preparedStmt = con.prepareStatement(query);
			  preparedStmt.setInt (1, Hid);
		      preparedStmt.setString (2, Login);
		      preparedStmt.setString (3, message);
		      preparedStmt.setInt(4, Score);
		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		
	}
	
	public static void InsertAvailableDate(String Start, String End, float PPN, int hid) throws Exception
	{
		Connection con = DBConnection.getConnection();
		int pid = CreatePeriod(con, Start, End);
		AddAvailable(pid, hid, PPN, con);
	}

}
