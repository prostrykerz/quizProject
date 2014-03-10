<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="models.Question" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	ArrayList<Question> questions = new ArrayList<Question>(); //Receive from servlet
	Question one = new Question("one", new ArrayList<String>(), 1);
	Question two = new Question("two", new ArrayList<String>(), 2);
	questions.add(one);
	questions.add(two);
	String question_text = "";
	boolean first = true;
	for(Question q : questions) {
		question_text += "\"" + q.getQuestion() + "\"";
		if(first) {
			question_text += "\"" + q.getQuestion() + "\"";
		}
		else {
			
		}
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QUIZ TITLE</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
	<div id="question_box"">
		sdfsdf
	</div>
<script>
	$(document).ready(function() {
		quizInitialization();
		showFirstQuestion();
		
	});
	function quizInitialization() {
		var question_text = new Array("<%= question_text %>");
		
	}
	function showFirstQuestion() {
		console.log("test");
	}
</script>
</body>
</html>