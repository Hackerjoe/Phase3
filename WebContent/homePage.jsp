<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HomePage</title>
</head>
<body>

<% 
 	
	User user = null;
	// FROM LOGIN PAGE
	if(request.getParameter("isFromLogin") != null) 
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		user = User.AuthenticateUser(username, password);
		if (user != null)
		{
			System.out.println("user " + username + " is authenticated");
			session.setAttribute("user", user);
		}
		else 
		{
			System.out.println("Username or password is incorect");
			response.sendRedirect("errorPage.jsp");
		}
	}
	// FROM NEW USER PAGE
	else if (request.getParameter("isFromNewUser") != null)
	{
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		int admin = 0;
		if(request.getParameter("admin").equals("on"))
			admin = 1;
		user = User.InsertUser(name, username, admin, password, address, phone);
		if (user != null)
		{
			System.out.println(name + " was added to the database");
			session.setAttribute("user", user);
		}
		else
		{
			System.out.println("Unable to insetert user");
			response.sendRedirect("errorPage.jsp");
		}
	}
	// RETURNING TO HOME PAGE
	else
	{
		user = (User) session.getAttribute("user");
	}
 
%>

<h1>Home Page</h1>
<%= user.username %>

<br>
<a href="newHouse.jsp">create house</a><br>

<a href="ownerHouses.jsp?ownerUsername=<%=user.username%>">Your house's</a><br>
<a href="Reserve.jsp?hid=1">Reserve Test (PLEASE DELETE THIS)</a><br>
<a href="feedbackHouse.jsp?hid=1">Feedback Test (PLEASE DELETE THIS)</a><br>
<a href="AddAvailable.jsp?hid=1">Add available Test (PLEASE DELETE THIS) NOTE: You have to be joeyDD to make this one work</a><br>
<a href="UserReserves.jsp">User Reserves (PLEASE DELETE THIS)</a><br>
<a href="ShowVisits.jsp">Show Visits (PLEASE DELETE THIS)</a><br>

</body>
</html>