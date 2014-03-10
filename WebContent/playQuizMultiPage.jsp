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
	for(int i = 0; i < questions.size(); i++) {
		question_text += "\"";
		if(i == questions.size()-1) {
			question_text += questions.get(i).getQuestion() + "\"";
		}
		else {
			question_text += questions.get(i).getQuestion() + "\"" + ",";
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
		WILL BE REPLACED BY JAVASCRIPT
	</div>
<script>
	$(document).ready(function() {
		//Initialization
		var question_text = new Array(<%= question_text %>);
		var answers = new Array();
		
		showQuestion(0);
		
		function showQuestion(index) {
			var prevdisabled = "";
			var nextdisabled = "";
			if(index == 0) prevdisabled = "disabled";
			if(index == question_text.length-1) nextdisabled = "disabled";
			var html = "";
			html += "Question: " + question_text[index];
			html += "<br />";
			html += "Answer: ";
			html += "<input type=\"text\" name=\"answer\" data-id=\"" + index + "\" />";
			html += "<br />";
			html += "<button id=\"prev_btn\" type=\"button\" " + prevdisabled + ">Prev</button>";
			html += "<button id=\"next_btn\" type=\"button\" " + nextdisabled + ">Next</button>";
			$("#question_box").html("");
			$("#question_box").html(html);
			
			$("#next_btn").click(function() {
				showQuestion(index+1);
			});
			$("#prev_btn").click(function() {
				showQuestion(index-1);
			});
		}
		
		
	});
	
</script>
</body>
</html>