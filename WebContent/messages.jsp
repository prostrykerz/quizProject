<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messages</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<%
	User user = (User) session.getAttribute("user");
%>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
	<div class="container">
		<table>
			<tr>
				<td>From:</td>
				<td>Message</td>
			</tr>
			<%
				ArrayList<Message> messages = user.getMessages();
				for(int i = 0; i < messages.size(); i++) {
					out.println("<tr>");
					out.println("<td>" + messages.get(i).getSender().getUsername() + "</td>");
					out.println("<td>" + messages.get(i).getMessage() + "</td>");
					out.println("</tr>");
				}
			%>
		</table>
	</div>
</body>
</html>