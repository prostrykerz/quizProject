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
<%int currIndex = 1;%>

<form action="LoginServlet" method="post">
	Choose question type:
		<select id="drop<%=currIndex%>" name="mydropdown<%=currIndex%>">
			<option value="1">Text Question-Response</option>
			<option value="2"> Picture Question-Response </option>
			<option value="3">Text Multiple-Choice</option>
			<option value="4">Picture Multiple-Choice</option>
			<option value="5">Fill in the blank</option>
		</select>
		<button id="addQ" type="button">Add Question</button>
		<br>
		<button id="addButton" type="submit">Create Quiz</button>
	</form>
				
<script type="text/javascript">
	var counter = 2;
	$(document).ready(function() {
		
		$("#addQ").click(addAnotherQ);
		
		function addAnotherQ(e) {
			var answerCount = 1;
			var dropVal = $("#drop"+ (counter - 1)).val();
			
			$("#addQ").remove();
			var newQ = "Choose question type: <select id=\"drop"+counter+"\"name=\"mydropdown"+counter+"\">";
			newQ += "<option value=\"1\">Text Question-Response</option>";
			newQ += "<option value=\"2\"> Picture Question-Response </option>";
			newQ += "<option value=\"3\">Text Multiple-Choice</option>";
			newQ += "<option value=\"4\">Picture Multiple-Choice</option>";
			newQ += "<option value=\"5\">Fill in the blank</option>";
			newQ += "</select>";
			newQ += "<button id=\"addQ\" type=\"button\">Add Question</button> <br>";
		
			var qText = "Question Text: <input type=\"questionText\" name=\"qText\" />\<br>";
			if (dropVal === "5") {
				$("#addButton").before("Indicate a blank with 'X'. <br>");
			}
			$("#addButton").before(qText);
			console.log(dropVal);
			
			if (dropVal === "2") {
				$("#addButton").before("Picture Title: <input type=\"qPicTitle\" name=\"qPicTitle\"/>\<br>");
				$("#addButton").before("Picture URL: <input type=\"qPic\" name=\"qPic\"/>\<br>");
				$("#addButton").before("Answer: <input type=\"qResponse\" name=\"qResponse\"/>\<br>");
			}
			
			if (dropVal === "3") {
				var optionCount = 1;
				
				$("#addButton").before("Option "+optionCount+": <input id=\"oppField"+optionCount+"\"  type=\"qOppField\" name=\"qOppField\"/>");
				$("#addButton").before("<button id=\"addOpp"+optionCount+"\" type=\"button\">Add Option</button><br>");
				$("#addOpp" + optionCount).click(addOption);
				function addOption(e) {
					optionCount++;
					$("#addOpp" + (optionCount - 1)).before("<br>Option "+optionCount+": <input  type=\"qPicTitle\" name=\"qPicTitle\"/>");
					$("#addOpp" + (optionCount -1 )).before("<button id=\"addOpp"+optionCount+"\" type=\"button\">Add Option</button>");
					$("#addOpp" + (optionCount -1)).remove();
					$("#addOpp"+(optionCount)).click(addOption);
				}	
			}
		
			if (dropVal === "4") {
				var optionCount = 1;
		
				$("#addButton").before("Picture Title: <input type=\"qPicTitle\" name=\"qPicTitle\"/>\<br>");
				$("#addButton").before("Picture URL: <input type=\"qPic\" name=\"qPic\"/>\<br>");
				$("#addButton").before("Option "+optionCount+": <input id=\"oppField"+optionCount+"\"  type=\"qOppField\" name=\"qOppField\"/>");
				$("#addButton").before("<button id=\"addOpp"+optionCount+"\" type=\"button\">Add Option</button><br>");
				$("#addOpp" + optionCount).click(addOption);
				function addOption(e) {
					optionCount++;
					$("#addOpp" + (optionCount - 1)).before("<br>Option "+optionCount+": <input  type=\"qPicTitle\" name=\"qPicTitle\"/>");
					$("#addOpp" + (optionCount -1 )).before("<button id=\"addOpp"+optionCount+"\" type=\"button\">Add Option</button>");
					$("#addOpp" + (optionCount -1)).remove();
					$("#addOpp"+(optionCount)).click(addOption);
				}	
			}	
			
			$("#addButton").before("Answer "+answerCount+": <input id=\"qAnswer"+answerCount+"\"  type=\"text\" name=\"qAnswer\"/>");
			$("#addButton").before("<button id=\"addAns"+answerCount+"\" type=\"button\">Add Answer</button><br>");
			$("#addAns" + answerCount).click(addAnswer);
			function addAnswer(e) {
				answerCount++;
				$("#addAns" + (answerCount - 1)).before("<br>Answer "+answerCount+": <input  type=\"qAnswer\" name=\"qAnswer\"/>");
				$("#addAns" + (answerCount -1 )).before("<button id=\"addAns"+answerCount+"\" type=\"button\">Add Answer</button>");
				$("#addAns" + (answerCount -1)).remove();
				$("#addAns" + (answerCount)).click(addAnswer);
			}
				
			$("#addButton").before(newQ);
			$("#addQ").click(addAnotherQ);
			counter++;
		}
	});
	
</script>
	
</body>
</html>