<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<%@ page import="models.QuizHistory" %>
<%@ page import="databases.QuizHistoryTable" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	User curuser = (User) session.getAttribute("user");
	ArrayList<QuizHistory> attempts = QuizHistoryTable.getUserTakenQuizzes(curuser);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz History</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name=""></jsp:param>
	</jsp:include>
	<h2>Quiz History</h2>
	<table>
		<tr>
			<td>Quiz</td>
			<td>Score</td>
			<td>Time</td>
		</tr>
		<%
			for(QuizHistory qh : attempts) {
				Quiz q = new Quiz(qh.getQuizId());
				out.println("<tr>");
				out.println("<td>" + q.getTitle() + "</td>");
				out.println("<td>" + qh.getScore() + "</td>");
				out.println("<td>" + qh.getTime() + "</td>");
				out.println("</tr>");
			}
		%>
	</table>
</body>
</html>