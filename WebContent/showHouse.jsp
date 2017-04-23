<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="airbnbModels.*, java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>showHouse</title>
</head>
<body>

<%
	House house;
	// SHOW A HOUSE WITH ID
	if(request.getParameter("houseID") != null)
	{
		int houseID = Integer.parseInt(request.getParameter("houseID"));
		house = House.GetHouseWithID(houseID);
	}
	// SHOW NEWLY CREATED HOUSE
	else
	{
		String category = request.getParameter("category");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String url = request.getParameter("url");
		String phone = request.getParameter("phone");
		String year = request.getParameter("year");
		String userLogin = request.getParameter("userLogin");
		
		house = House.InsertHouse(category, name, address, url, phone, year, userLogin);
		if(house == null)
			response.sendRedirect("errorPage.jsp");
	}	
%>


<h1>Your house!</h1><br><br>
Category: <%=house.category%><br>
Name: <%=house.name%><br>
Address: <%=house.address%><br>
URL: <%=house.url%><br>
Phone Number: <%=house.phone%><br>
Year Built: <%=house.year%><br>
Owner: <%=house.ownerUsername%><br>

<a href="homePage.jsp">back</a>


</body>
</html>