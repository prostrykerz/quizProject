<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.util.ArrayList" %>
<%@page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%!
ArrayList<Question> questions = new ArrayList<Question>();
int numQuestions = questions.size();
String quizTitle = "Adrian's Quiz";
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Take a Quiz</title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp">
	    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
<h2><%=quizTitle%></h2>
</head>
<body>
	<script type="text/javascript">
	
	
		for (var i = 0; i < <%=numQuestions%>; i++) { 
			
		}

	
</script>
</body>
</html>