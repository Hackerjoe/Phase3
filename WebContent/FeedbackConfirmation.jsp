<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*,java.util.List"%>
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

String username = request.getParameter("login");
String message = request.getParameter("Text");
int score = Integer.parseInt(request.getParameter("score"));
int hid = Integer.parseInt(request.getParameter("hid"));

try
{
	House.CreateFeedBack(username, hid, message, score);
}
catch(Exception e)
{
	response.sendRedirect("errorPage.jsp");
}
%>

Rating has been submitted.
Press back.

</body>
</html>