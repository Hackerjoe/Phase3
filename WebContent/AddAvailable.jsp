<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	// check to see if there is a user
	User user = (User) session.getAttribute("user");
	if(user == null)
		response.sendRedirect("errorPage.jsp"); 
	
	int houseID = Integer.parseInt(request.getParameter("hid"));
	ArrayList<Period> periods = House.GetAvailableDates(houseID);
	House house = House.GetHouseWithID(houseID);
	
	if(user.username.compareTo(house.ownerUsername) != 0)
	{
		response.sendRedirect("errorPage.jsp"); 
	}
	
	if(periods == null)
	{
		response.sendRedirect("errorPage.jsp"); 
	}
	

	%>
	
	<h1><% out.println(house.name);%>
	<h1> Times Available </h1>
	
	<%
	if(!periods.isEmpty())
	{
		for(Period p : periods)
		{
			%>
			From: <% out.print(p.start); %> To: <% out.print(p.end); %> Price per night: <% out.print(p.ppn); %> <br>
			<%
		}
	}
	else
	{
		%>
		No Time Available!
		<%
	}
	
	

%>

<form action="AddAvailableConfirm.jsp">

	Start Date<br>
	<input type="text" name="start" /><br>
	
	End Date<br>
	<input type="text" name="end" /><br>
	
	Price Per Night<br>
	<input type="number" name="ppn" /><br>
	
	<input type="hidden" name="hid" value=<%=houseID %> >
	<input type="submit" />

</form>

</body>
</html>