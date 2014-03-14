<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="admin.Announcement" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	User user = (User) session.getAttribute("user");
	ArrayList<Announcement> announcements = (ArrayList<Announcement>) application.getAttribute("announcements");
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	HashSet<User> users = manager.getUsers();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Qurious</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="index.jsp"></jsp:param> 
	</jsp:include>
	
	<% if(user != null) {
			out.println("<div style=\"margin: 0 auto; width: 90%;\">");
			for(Announcement a : announcements) {
				out.println("<h2 style=\"border: 1px solid white\">" + a.getText() + "</h2>");
			}
		
			out.println("</div><table class=\"table\" id=\"newsFeed\">");
			out.println("<tr> <td>Username</td><td>Mail</td></tr>");
		
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
				out.println("</table>");
		} else out.println("<h1>Please Login</h1>");	
		%>
	
</body>
</html>