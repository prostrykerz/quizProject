<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="databases.UserTable" %>
<%@ page import="databases.QuizTable" %>
<%@ page import="databases.QuizHistoryTable" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Site Statistics</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	<div style="margin: 0 auto; width: 60%">
		<h1>Site Statistics</h1>
		<h3>Total Users: <%= UserTable.getNumUsers() %></h3>
		<h3>Total Quizzes: <%= QuizTable.getNumQuizzes() %></h3>
		<h3>Total Quiz Attempts: <%= QuizHistoryTable.getNumAttempts() %></h3>
	</div>
</body>
</html>