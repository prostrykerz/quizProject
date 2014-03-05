<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.HashSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	HashSet<User> users = manager.getUsers();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Quiztopia</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="index.jsp"></jsp:param> 
	</jsp:include>
	HOME PAGE
	<table class="table">
		<tr>
			<td>Username</td>
			<td>Mail</td>
		</tr>
		<%
			for(User u : users) {
				StringBuilder sb = new StringBuilder();
				for(Message m : u.getFriendRequests()) {
					sb.append("DONT ACTUALLY CLICK THESE: " + m.getMessage() + "<br />");
				}
				for(Message m : u.getMessages()) {
					sb.append(m.getMessage() + "<br />");
				}
				out.println("<tr>");
				out.println("<td><a href='user.jsp?username=" + u.getUsername() + "'>" + u.getUsername() + "</a></td>");
				out.println("<td>" + sb.toString() + "</td>");
				out.println("</tr>");
			}
		%>
	</table>
	<a href="create_account.jsp">Create New Account</a>

</body>
</html>