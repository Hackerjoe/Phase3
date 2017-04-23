<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Welcome, Create an account!</h1>

<form action="homePage.jsp">

	Name<br>
	<input type="text" name="name" /><br>
	
	Username<br>
	<input type="text" name="username" /><br>
	
	Password<br>
	<input type="password" name="password" /><br>
	
	Address<br>
	<input type="text" name="address" /><br>
	
	Phone Number<br>
	<input type="text" name="phone" /><br>
	
	Admin<br>
	<input type="checkbox" name="admin" /><br>
	
	<input type="hidden" name="isFromNewUser" value="true" >
	<input type="submit" />

</form>

</body>
</html>