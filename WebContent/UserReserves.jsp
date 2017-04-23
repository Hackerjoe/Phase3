<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*,java.util.List, java.util.ArrayList"%>
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

List<Reserve> res = new ArrayList<Reserve>();
try
{
	res = User.getReserves(user.username);
}
catch(Exception e)
{
	
}

if(!res.isEmpty())
{
	for(Reserve r : res)
	{
		House h = House.GetHouseWithID(r.hid);
		Period p = House.getPeriod(r.pid, null);
		%>
		<h1> <%out.print(h.name); %> </h1><br>
		Address: <% out.print(h.address); %> <br>
		Start: <% out.print(p.start); %> End: <% out.print(p.end); %> <br>

		<form action="StayConfirm.jsp">
			<input type="hidden" name="login" value=<%=user.username %> >
			<input type="hidden" name="pid" value=<%=r.pid %> >
			<input type="hidden" name="hid" value=<%=r.hid %> >
			<input type="hidden" name="cost" value=<%=r.cost %> >
			<button name="name" value="value" type="submit">Record Stay</button>
		</form>
		<hr>
		<% 
	}
}
%>
</body>
</html>