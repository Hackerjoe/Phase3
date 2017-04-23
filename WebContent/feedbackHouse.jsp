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

int houseID = Integer.parseInt(request.getParameter("hid"));
House house = House.GetHouseWithID(houseID);
%>
<h1>Feedback for: <%out.print(house.name); %></h1>
<form action="UsefullFeedback.jsp">
		Get the top usefull feedback.<br>
		Number of feedback:<input type="number" name="number"">
		
		<input type="hidden" name="hid" value=<%=houseID %>>
		<input type="submit" />
	
</form>
<h2> Submit Feedback</h2>
<form action="FeedbackConfirmation.jsp">
		
		Score:<input type="number" name="score" min="0" max="10">
		Message: <input type="text" name="Text">
		
		<input type="hidden" name="hid" value=<%=houseID %>>
		<input type="hidden" name="login" value=<%=user.username %> >
		<input type="submit" />
	
</form>
<hr>
<% 
List<FeedBack> feeds = House.getFeedBackForHouse(houseID);


if(!feeds.isEmpty())
{
	for(FeedBack f : feeds)
	{
		%>
		<b><% out.print(f.Login); %></b> <br>
		<b><% out.print(f.date); %></b> <br>
		Score: <b><% out.print(f.score); %></b> <br>
		<p> <% out.print(f.message); %> </p> <br>
		
		<form action="RateConfirmation.jsp">
		
		Score:<input type="number" name="score" min="0" max="2">
		
		<input type="hidden" name="fid" value=<%=f.fid %> >
		<input type="hidden" name="login" value=<%=user.username %> >
		<input type="submit" />
	
		</form>
		<hr>
		<%
	}
}
%>
</body>
</html>