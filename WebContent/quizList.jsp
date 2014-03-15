<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="databases.QuizTable" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a quiz</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
		</jsp:include>
	<div class="container">
		<h1> Choose a Quiz! </h1>
		<table class="quiz_list">
			<% 	QuizTable qt = new QuizTable();
				ArrayList[] table = qt.getTable();
				for (int i=0; i<table[0].size(); i++){
					%><tr><td><a href="quizSummary.jsp?id=<%=table[0].get(i)%>"><%=table[1].get(i)%></a></td></tr><%
				}
			%>
		</table>
	</div>
	

</body>
</html>