<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*,java.util.ArrayList, java.text.DateFormat, java.text.SimpleDateFormat, java.util.Date"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

User user = (User) session.getAttribute("user");
if(user == null)
	response.sendRedirect("errorPage.jsp"); 

String username = request.getParameter("username");
String StartDate = request.getParameter("start");
String EndDate = request.getParameter("end");
int hid = Integer.parseInt(request.getParameter("hid"));

boolean confirmed = false;

try {
	System.out.println(StartDate);
	float PPN = House.CheckIfDatesAreAvailable(null,hid, StartDate, EndDate);
	if(PPN != -1)
	{
		DateFormat formatter ; 
		Date sd,ed; 
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		sd =  formatter.parse(StartDate);
		ed = formatter.parse(EndDate);
		int daysBetween = (int)( (ed.getTime() - sd.getTime()) / (1000 * 60 * 60 * 24) );
		if(daysBetween <= 0)
			System.out.println("Those dates do not work.");
		float cost = PPN * daysBetween;
		House.SQLReserveDate(StartDate, EndDate, cost, username, hid);
		confirmed = true;
	}
	else
	{
		System.out.println("The dates you have selected are not Available.");
		confirmed = false;
	}
	
} catch (Exception e) {
	System.out.println("Unable to reserve dates. =(");
       System.err.println(e.getMessage());
}

%>


<%
	if(confirmed == true)
	{
		%>
		Your dates have been confirmed. 
		<a href="homePage.jsp">Home</a>
		<% 
	}
	else
	{
		%>
		Unable to create those dates.
		<a href="homePage.jsp">Home</a>
		<% 
	}
%>
</body>
</html>