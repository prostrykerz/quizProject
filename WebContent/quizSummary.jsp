<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a quiz</title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
		</jsp:include>
	<% 	Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		Quiz q = new Quiz(id);
		if (q==null) System.out.println("quiz is null");
		HashMap<String, Object> infoMap = q.getInfoMap();
		if (infoMap==null) System.out.println("infoMap is null");
	%>
	<h1><%=infoMap.get("title") %> </h1>
	<h3>Description: <%=infoMap.get("description")%></h3>
	<h3>Creator: <%=infoMap.get("creator")%></h3>
	<% 
	if((Boolean)infoMap.get("practiceMode")){
	%><a href="#"><button>Practice Quiz</button></a><%
	}
	%>
	<form action="PlayQuizServlet" method="post">
		<input type="hidden" name="quiz_id" value="<%=id%>">
		<button type="submit">Take the Quiz</button>
	</form>
	<a href="challenge.jsp?id=<%=id%>"><button type="button">Challenge Friend</button></a>
</body>
</html>