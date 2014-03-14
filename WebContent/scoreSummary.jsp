<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Score</title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
		</jsp:include>

	<h1>Quiz Summary for <%=request.getParameter("title") %> </h1>
	<h3>Score: <%=request.getParameter("score")%>/<%=request.getParameter("totalScore")%></h3>
	<%
	String timeStr = request.getParameter("time");
	int time =0;
	if (timeStr!=null){
		time = (Integer.parseInt(timeStr));
	}
	int minutes = time/60;
	int seconds = time%60;
	String secondsString = String.valueOf(seconds);
	if(seconds <= 9) secondsString = "0" + seconds;
	
	%>
	<h3>Time: <%=minutes%>:<%=secondsString%></h3>
	<a href="quizList.jsp"><button>Play More Quizzes!</button></a>
	
</body>
</html>