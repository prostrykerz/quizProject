<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="users.AccountManager" %>
<%@ page import="models.Quiz" %>
<%@ page import="databases.QuizTable" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clear Quiz History</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	
	
	<div class="container" style="margin-top: 10px;">
		<h1>Clear Quiz History</h1>
		<form action="ClearQuizHistoryServlet" method="post">
			<select name="id">
				<%
					QuizTable qt = new QuizTable();
					ArrayList[] table = qt.getTable();
					for (int i=0; i<table[0].size(); i++){
						out.println("<option value=\"" + table[0].get(i) + "\">");
						out.println(table[1].get(i));
						out.println("</option>");
					}
				%>
			</select>
			<button type="submit">Clear Quiz History</button>
		</form>
	</div>
</body>
</html>