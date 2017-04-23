<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.sql.*" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
try {
/*here mysqltest is the database name. you have to give the database name you have created and 3306 is the default sql port */
String connectionURL = "jdbc:mysql://georgia.eng.utah.edu/5530db37"; 

Connection connection = null; 

Class.forName("com.mysql.jdbc.Driver").newInstance(); 

//mysql -h georgia.eng.utah.edu -u 5530u37 -p 5530db37

connection = DriverManager.getConnection(connectionURL, "5530u37", "c711r33s");

if(!connection.isClosed())
%>
<% 
out.println("Successfully connected to " + "MySQL server using TCP/IP...");
connection.close();
}
catch(Exception ex){
%>
<%
out.println("Unable to connect to database.");
}
%>


</body>
</html>