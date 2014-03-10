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
<jsp:include page="header.jsp">
	    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
<h1> Create a quiz! </h1>
<h2> Add questions to create a quiz. </h2>
<%int currIndex = 1;%>

<form action="QuizServlet" method="post">
	<div id="entry">
	Choose question type:
		<select id="drop<%=currIndex%>" name="mydropdown<%=currIndex%>">
			<option value="1">Text Question-Response</option>
			<option value="2"> Picture Question-Response </option>
			<option value="3">Text Multiple-Choice</option>
			<option value="4">Picture Multiple-Choice</option>
			<option value="5">Fill in the blank</option>
		</select>
		<button id="addQ" type="button">Add Question</button> <br>
		</div>
		<button id="addButton" type="submit">Create Quiz</button>
</form>
				
<script type="text/javascript">
	var counter = 2;
	$(document).ready(function() {
		
		$("#addQ").click(addAnotherQ);
		
		function addAnotherQ(e) {
			var dropVal = $("#drop"+ (counter - 1)).val();	
			$("#entry").remove();
			
			var newQ = "<div id=\"entry\">Choose question type: <select id=\"drop"+counter+"\"name=\"mydropdown"+counter+"\">";
			newQ += "<option value=\"1\">Text Question-Response</option>";
			newQ += "<option value=\"2\"> Picture Question-Response </option>";
			newQ += "<option value=\"3\">Text Multiple-Choice</option>";
			newQ += "<option value=\"4\">Picture Multiple-Choice</option>";
			newQ += "<option value=\"5\">Fill in the blank</option>";
			newQ += "</select>";
			newQ += "<button id=\"addQ\" type=\"button\">Add Question</button> <br> </div>";
		
			if (dropVal === "5") {
				$("#addButton").before("Indicate a blank with 'X'. <br>");
			}
			
			var qText = "Question " +(counter -1)+ " Text: <input type=\"questionText\" name=\"qText\" />\<br>";
			
			$("#addButton").before(qText);
			
			if (dropVal === "2" || dropVal === "4") {
				$("#addButton").before("Picture Title: <input type=\"qPicTitle\" name=\"qPicTitle\"/>\<br>");
				$("#addButton").before("Picture URL: <input type=\"qPic\" name=\"qPic\"/>\<br>");
			}
			
			if (dropVal === "3" || dropVal === "4") {
				var optionCount = 1;
				var optionButtonID = "addOpt"+ (counter ) + "-" + optionCount;
				$("#addButton").before("Option "+optionCount+": <input id=\"oppField"+optionCount+"\"  type=\"qOppField\" name=\"qOppField\"/>");
				$("#addButton").before(" Correct? <input type=\"checkbox\" id=\"correctBox"+counter+"-"+optionCount+"\"name=\"correctBox\">");
				$("#addButton").before("<button id="+optionButtonID+" type=\"button\">Add Option</button><br>");
				console.log("First " + optionButtonID);
				$("#"+optionButtonID).click(addOption);
			}
						
			if ((dropVal === "1") || (dropVal === "2") || (dropVal === "5")) {
				var answerCount = 1;
				var answerButtonID = "addAns"+ counter + "-" + answerCount;
				$("#addButton").before("Answer "+answerCount+": <input id=\"qAnswer-"+counter+"-"+answerCount+"\"  type=\"text\" name=\"qAnswer\"/>");
				$("#addButton").before("<button id="+answerButtonID+" type=\"button\">Add Answer</button><br>");
				$("#"+answerButtonID).click(addAnswer);
			}
			
			function addOption() {
				optionCount++;
				optionButtonID = "addOpt"+ (counter - 1) + "-" + optionCount;
				$(this).before("<br>Option "+optionCount+": <input id=\"oppField"+optionCount+"\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(this).before(" Correct? <input type=\"checkbox\" id=\"correctBox"+counter+"-"+optionCount+"\"name=\"correctBox\">");
				$(this).before("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				$(this).remove();
				console.log(optionButtonID);
				$("#"+optionButtonID).click(addOption);
			}
			
			function addAnswer() {
				answerCount++;
				answerButtonID = "addAns"+ (counter - 1) + "-" + answerCount;
				$(this).before("<br>Answer "+answerCount+": <input  type=\"qAnswer\" name=\"qAnswer\"/>");
				$(this).before("<button id="+answerButtonID+" type=\"button\">Add Answer</button>");
				$(this).remove();
				$("#"+answerButtonID).click(addAnswer);
			}
				
			$("#addButton").before("<br>");
			$("#addButton").before(newQ);
			$("#addQ").click(addAnotherQ);
			counter++;
		}
	});
	
</script>
	
</body>
</html>
