<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*, java.util.ArrayList"%>
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

ArrayList<Visit> visits = new ArrayList<Visit>();

try
{
	visits = User.getVisits(user.username);
}
catch(Exception e)
{
	response.sendRedirect("errorPage.jsp"); 
}

for(Visit v : visits)
{
	House h = House.GetHouseWithID(v.hid);
	Period p = House.getPeriod(v.pid, null);
	%>
	<h1> <%out.print(h.name); %> </h1><br>
	Address: <%out.print(h.address); %> <br>
	Start: <%out.print(p.start); %> End: <%out.print(p.end); %> <br>
	Cost: <%out.print(v.cost); %><br>
	<hr>
	<%
}

%>
</body>
</html>