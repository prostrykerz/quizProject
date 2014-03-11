<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<h1> Create a quiz! </h1>

	Quiz Title: <input id="quiz_title" type="quizTitle" name="qTitle">
	<h4> Add questions to create a quiz. </h4>
	<div id="entry">
	Choose question type:
		<select id="drop" name="mydropdown">
			<option value="1">Text Question-Response</option>
			<option value="2"> Picture Question-Response </option>
			<option value="3">Single Answer Text Multiple-Choice</option>
			<option value="4">Single Answer Picture Multiple-Choice</option>
			<option value="5">Multiple Answers Text Multiple-Choice</option>
			<option value="6">Multiple Answers Picture Multiple-Choice</option>
			<option value="7">Fill in the blank</option>
		</select>
		<button id="addQ" type="button">Add Question</button> <br>
		</div>
		<br>
		How would you like your questions displayed? 
		<select id="onePage" name="mydropdown">
			<option value="single">All on one page</option>
			<option value="multi"> One per page</option>
		</select>
		<br><br>
		<button id="addButton" type="button">Create Quiz</button>
				
<script type="text/javascript">
	var counter = 1;
	$(document).ready(function() {
		var question_divs = new Array();
		
		$("#addQ").click(addAnotherQ);
		
		function addAnotherQ(e) {
			var dropVal = $("#drop").val();	
			$("#entry").remove();
			var divID = "#question-" + counter;
			question_divs.push(divID);
			var newQ = "<div id=\"entry\">Choose question type: <select id=\"drop\"name=\"mydropdown"+counter+"\">";
			newQ += "<option value=\"1\">Text Question-Response</option>";
			newQ += "<option value=\"2\">Picture Question-Response </option>";
			newQ += "<option value=\"3\">Single Answer Text Multiple-Choice</option>";
			newQ += "<option value=\"4\">Single Answer Picture Multiple-Choice</option>";
			newQ += "<option value=\"5\">Multiple Answers Text Multiple-Choice</option>";
			newQ += "<option value=\"6\">Multiple Answers Picture Multiple-Choice</option>";
			newQ += "<option value=\"7\">Fill in the blank</option>";
			newQ += "</select>";
			newQ += "<button id=\"addQ\" type=\"button\">Add Question</button> <br> </div>";
		
			if (dropVal === "5") {
				$(divID).append("Indicate a blank with 'X'. <br>");
			}
			
			var qText = "Question " + counter + " Text: <input class=\"question_text\" type=\"questionText\" name=\"qText\" />\<br>";
			$("#addButton").before("<div class=\"question type-" + dropVal + "\" id=\"question-" + counter + "\">");
			
			$(divID).append(qText);
			if (dropVal === "2" || dropVal === "4" || dropVal === "6") {
				$(divID).append("Picture Title: <input class=\"pictureTitle\" type=\"qPicTitle\" name=\"qPicTitle\"/>\<br>");
				$(divID).append("Picture URL: <input class=\"pictureURL\" type=\"qPic\" name=\"qPic\"/>\<br>");
			}
			
			if (dropVal === "3" || dropVal === "4") {
				var optionCount = 1;
				var optionButtonID = "addOpt"+ (counter ) + "-" + optionCount;
				$(divID).append("Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(divID).append(" Correct? <input type=\"radio\" class=\"correctBox-"+optionCount+"\"name=\"correctBox-"+counter+"\">");
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				console.log("First " + optionButtonID);
				$("#"+optionButtonID).click(addRadioOption);
			}
			
			if (dropVal === "5" || dropVal === "6") {
				var optionCount = 1;
				var optionButtonID = "addOpt"+ (counter ) + "-" + optionCount;
				$(divID).append("Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(divID).append(" Correct? <input type=\"checkbox\" class=\"correctBox-"+optionCount+"\"name=\"correctBox\">");
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				console.log("First " + optionButtonID);
				$("#"+optionButtonID).click(addOption);
			}
						
			if ((dropVal === "1") || (dropVal === "2") || (dropVal === "7")) {
				var answerCount = 1;
				var answerButtonID = "addAns"+ counter + "-" + answerCount;
				$(divID).append("Answer "+answerCount+": <input class=\"answer\"  type=\"text\" name=\"qAnswer\"/>");
				$(divID).append("<button id="+answerButtonID+" type=\"button\">Add Answer</button>");
				$("#"+answerButtonID).click(addAnswer);
			}
			
			function addOption() {
				optionCount++;
				optionButtonID = "addOpt"+ counter + "-" + optionCount;
				$(divID).append("<br>Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(divID).append(" Correct? <input type=\"checkbox\" class=\"correctBox-"+optionCount+"\"name=\"correctBox\">");
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				$(this).remove();
				console.log(optionButtonID);
				$("#"+optionButtonID).click(addOption);
			}
			
			function addRadioOption() {
				optionCount++;
				optionButtonID = "addOpt"+ counter + "-" + optionCount;
				$(divID).append("<br>Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(divID).append(" Correct? <input type=\"radio\" class=\"correctBox-"+optionCount+"\"name=\"correctBox-"+(counter-1)+"\">");
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				$(this).remove();
				console.log(optionButtonID);
				$("#"+optionButtonID).click(addRadioOption);
			}
			
			function addAnswer() {
				answerCount++;
				answerButtonID = "addAns"+ counter + "-" + answerCount;
				$(divID).append("<br>Answer "+answerCount+": <input class=\"answer\" type=\"qAnswer\" name=\"qAnswer\"/>");
				//$(divID).append("<br>Answer "+answerCount+": <input class=\"answer-" + answerCount + "\" type=\"qAnswer\" name=\"qAnswer\"/>");
				$(divID).append("<button id="+answerButtonID+" type=\"button\">Add Answer</button>");
				$(this).remove();
				$("#"+answerButtonID).click(addAnswer);
			}
			
			
			$("#addButton").before("<br>");
			$("#addButton").before(newQ);
			$("#addQ").click(addAnotherQ);
			counter++;
		}
		
		$("#addButton").click(function() {
			var data = formatData();
			console.log(data);
			$.post("QuizServlet",{data: JSON.stringify(data)}, function(responseJson) {
				console.log(responseJson);
				var response = $.parseJSON(responseJson);
				console.log(response);
				if(response.error) {
					alert(response.error);
				}
				else alert(response.msg);
			});
		});
		
		function formatData() {
			var questions = [];
			for(var i = 0; i < question_divs.length; i++) {
				var div = $(question_divs[i]);
				var question = {};
				
				var q_text = div.find(".question_text").val();
				question.text = q_text;
				if(div.hasClass("type-1") || div.hasClass("type-2") || div.hasClass("type-7")) {
					var answers = new Array();
					div.find(".answer").each(function() {
						answers.push($(this).val());
					});
					question.correct_answers = answers;
				}
				else if(div.hasClass("type-3") || div.hasClass("type-4") || div.hasClass("type-5") || div.hasClass("type-6")) {
					var possible_answers = new Array();
					var correct_answers = new Array();
					div.find(".answer").each(function(index) {
						console.log(".correctBox-" + index);
						if(div.find(".correctBox-" + (index+1)).is(":checked")) {
							correct_answers.push($(this).val());
						}
						possible_answers.push($(this).val());
					});
					question.correct_answers = correct_answers;
					question.possible_answers = possible_answers;
				}
				if(div.hasClass("type-2") || div.hasClass("type-4") || div.hasClass("type-6")){
					question.pictureTitle = div.find(".pictureTitle").val();
					question.pictureURL = div.find(".pictureURL").val();
				}
				question.type = getType(div);
				questions.push(question);
			}
			data = {};
			data.questions = questions;
			data.title = $('#quiz_title').val();
			data.description = "update me. In quizcreation.jsp";
			data.onePage = $('#onePage').val();
			return data;
		}
		
		function getType(div) {
			if(div.hasClass("type-1")) return "1";
			else if(div.hasClass("type-2")) return "2";
			else if(div.hasClass("type-3")) return "3";
			else if(div.hasClass("type-4")) return "4";
			else if(div.hasClass("type-5")) return "5";
			else if(div.hasClass("type-6")) return "6";
			else if(div.hasClass("type-7")) return "7";
			else return -1;
		}
	});
	
</script>
	
</body>
</html>
