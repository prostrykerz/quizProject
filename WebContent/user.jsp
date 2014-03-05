<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="users.AccountManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	User curuser = (User) session.getAttribute("user");
	String username = request.getParameter("username");
	User user = new User("","");
	for(User u: manager.getUsers()) {
		if(u.getUsername().equals(username)) user = u;
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= user.getUsername() %></title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
	<%
		if(!curuser.equals(user) && !(curuser.getFriends().contains(user))) {
			out.println("not friends add button<br />");
		}
	%>
	<%
		if(!curuser.equals(user)) {
			out.println("<a href='message.jsp?user=" + user.getUsername() + "'>Message User</a>");
		}
	%>
	
	<br />
	
	Welcome to <%= user.getUsername() %>'s page
</body>
</html>