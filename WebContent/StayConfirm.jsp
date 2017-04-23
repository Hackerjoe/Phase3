<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*"%>
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

String login = request.getParameter("login");
Float cost = Float.parseFloat(request.getParameter("cost"));
int hid = Integer.parseInt(request.getParameter("hid"));
int pid = Integer.parseInt(request.getParameter("pid"));

boolean failed = false;
try
{
	User.addVisit(cost, login, hid, pid);
}
catch(Exception e)
{
	out.println("Visit is recored or there was an error. Press back.");
	failed = true;
}
if(!failed)
{
	out.println("Visit has been recored. Press back.");
}
%>
</body>
</html>