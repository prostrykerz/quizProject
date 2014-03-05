<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a quiz</title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
<h1> Create a quiz! </h1>
<h2> Add questions to create a quiz. </h2>
<%int currIndex = 0;%>

<form action="LoginServlet" method="post">
	Choose question type:
		<select  name="mydropdown<%=currIndex%>">
			<option value="Text Question-Response">Text Question-Response</option>
			<option value="Picture Question-Response"> Picture Question-Response </option>
			<option value="Text Multiple-Choice">Text Multiple-Choice</option>
			<option value="Picture Multiple-Choice">Picture Multiple-Choice</option>
			<option value="Fill in the blank">Fill in the blank</option>
		</select>
		<button id="addQ" type="button">Create Question</button>
		<br>
		<button id="addButton" type="submit">Add to Quiz</button>
		<button type="submit">Done</button>
	</form>
		
<script type="text/javascript"><!--
	$(document).ready(function() {
		$("#addQ").click(addAnotherQ);
		function addAnotherQ(e) {
			$("#addQ").remove();
			var newQ = "Choose question type: <select name=\"mydropdown\"><option value=\"Text Question-Response\">Text Question-Response</option><option value=\"Picture Question-Response\"> Picture Question-Response </option></select> <button id=\"addQ\" type=\"button\">Create Question</button> <br>";
			$("#addButton").before(newQ);
			$("#addQ").click(addAnotherQ);
		}
	});
	

</script>
	
	
</body>
</html>