<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	HashSet<User> users = manager.getUsersIterable();
	User user = (User) session.getAttribute("user");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Promote User</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	<div class="container" style="margin-top: 10px;">
		<h1>Promote User To Admin</h1>
		<form action="PromoteUserServlet" method="post">
			<select name="username">
				<%
					for(User u : users) {
						if(u.getId() != user.getId() && !u.isAdmin()) {
							out.println("<option>");
							out.println(u.getUsername());
							out.println("</option>");
						}
					}
				%>
			</select>
			<button type="submit">Promote User To Admin</button>
		</form>
	</div>
	
</body>
</html>