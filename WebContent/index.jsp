<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="admin.Announcement" %>
<%@ page import="models.Quiz" %>
<%@ page import="databases.QuizTable" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	User user = (User) session.getAttribute("user");
	if(user == null) response.sendRedirect("login.jsp");
	ArrayList<Announcement> announcements = (ArrayList<Announcement>) application.getAttribute("announcements");
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
	<div style="margin: 0 auto; width: 90%;">
		<%
			for(Announcement a : announcements) {
				out.println("<h2 style=\"border: 1px solid white\">" + a.getText() + "</h2>");
			}
		%>
	</div>
	<div id="content">
		<div id="popular_quizzes">
			<%
				//ArrayList<Quiz> topTenQuizzes = QuizTable.getTopTenQuizzes();
			%>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			
		});
	</script>
</body>
</html>