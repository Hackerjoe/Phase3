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
	// check to see if there is a user
	User user = (User) session.getAttribute("user");
	if(user == null)
		response.sendRedirect("errorPage.jsp"); 
%>

<h1>Create a new house</h1>

<form action="showHouse.jsp">

	Category<br>
	<input type="text" name="category" /><br>
	
	Name<br>
	<input type="text" name="name" /><br>
	
	Address<br>
	<input type="text" name="address" /><br>
	
	URL<br>
	<input type="text" name="url" /><br>
	
	Phone Number<br>
	<input type="text" name="phone" /><br>
	
	Year Built<br>
	<input type="text" name="year" /><br>
	
	<input type="hidden" name="userLogin" value="<%=user.username%>" />
	
	<input type="submit" />
</form>

<br>
<a href="homePage.jsp">cancel</a>

</body>
</html>