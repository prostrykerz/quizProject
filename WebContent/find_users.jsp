<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	String query = request.getParameter("query");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find User</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="find_user.jsp"></jsp:param> 
	</jsp:include>
	<input type="search" id="user_search" placeholder="User Search" results/>
	<br />
	<%
		HashSet<User> users = manager.getUsersIterable();
		HashSet<User> filtered_users = new HashSet<User>();
		if(query != null) {
			for(User u : users) {
				if(u.getUsername().contains(query)) {
					filtered_users.add(u);
				}
			}
		} else {
			for(User u : users) filtered_users.add(u);
		}
		out.println("<table>");
		out.println("<tr><td>Users Found" + "</td></tr>");
		for(User u: filtered_users) {
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href=\"user.jsp?username=" + u.getUsername() + "\">" + u.getUsername() + "</a>");
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("</table>");
	%>
	
	<script>
	$('#user_search').keypress(function (e) {
		  if (e.which == 13) {
		    window.location.href = "find_users.jsp?query=" + $('#user_search').val();
		  }
		});
	</script>
</body>
</html>