<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
		Integer id = Integer.parseInt(request.getParameter("id"));
		Quiz q = new Quiz(id);
		if (q==null) System.out.println("quiz is null");
		HashMap<String, Object> infoMap = q.getInfoMap();
		if (infoMap==null) System.out.println("infoMap is null");
		//System.out.println(qArrJson.toString());
		
		ArrayList<Question> qArr = q.getQuestionArr();
		JSONArray qArrJson = new JSONArray();
		if(qArr != null) {
			for (int i = 0; i < qArr.size(); i++){
				qArrJson.put(qArr.get(i).getJSON());
			}
		}
		
		if ((Boolean)infoMap.get("random")){
			for (int i=0; i<qArrJson.length(); i++){
				int j = (int)Math.floor(Math.random()*(i+1));
				JSONObject temp = qArrJson.getJSONObject(i);
				qArrJson.put(i,qArrJson.getJSONObject(j));
				qArrJson.put(j,temp);
			}
		}
	%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editing A Quiz</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<style>

</style>
</head>
<body>
<jsp:include page="header.jsp">
	    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
<h1> Edit Your Quiz! </h1>
	
	Quiz Title: <input id="quiz_title" type="quizTitle" name="qTitle">
	<br>
	Quiz Description: <input id="quiz_description" type="quizDesc" name="quizDesc">
	<br>
	<div id="configure">
	<h4> Configure your quiz. </h4>
	How would you like your questions displayed? 
	<select id="onePage" name="mydropdown">
		<option value="single">All on one page</option>
		<option value="multi"> One per page</option>
	</select>
	<br>
	In what order would you like your questions displayed? 
	<select id="order" name="mydropdown">
		<option value="normal">As inputed</option>	
		<option value="random"> Randomly</option>
	</select>
	<br>
	Enable immediate feedback for this quiz? 
	<select id="feedback" name="mydropdown">	
		<option value="no"> No</option>
		<option value="yes">Yes</option>
	</select>
	<br>
	Enable practice mode for this quiz? 
	<select id="practice" name="mydropdown">
		<option value="no"> No</option>
		<option value="yes">Yes</option>	
	</select>
	</div>
	<h4> Add questions to create a quiz. </h4>
	<div id="container"></div>
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
		<button id="addButton" type="button">Create Quiz</button>
				
<script type="text/javascript">

	var counter = 1;
	$(document).ready(function() {
		var question_divs = new Array();
		var questionArr = <%= qArrJson%>;
		var answerArr = new Array(questionArr.length);
		console.log(questionArr);
		fillQuizData();
		for (var i=0; i<questionArr.length; i++){
			
			buildQuestionSkeleton();
			fillIn(i);
		}
		
		$("#addQ").unbind().bind("click", addAnotherQ);
		
		function fillQuizData(){
			$("#quiz_title").val('<%=infoMap.get("title")%>');
			$("#quiz_description").val('<%=infoMap.get("description")%>');
			if(<%=infoMap.get("onePage")%>){
				$("#onePage").val("single");
			}
			else{
				$("#onePage").val("multi");
			}
			if(<%=infoMap.get("random")%>){
				$("#order").val("random");
			}
			else{
				$("#order").val("normal");
			}
			if(<%=infoMap.get("immediateFeedback")%>){
				$("#feedback").val("yes");
			}
			else{
				$("#feedback").val("no");
			}
			if(<%=infoMap.get("practiceMode")%>){
				$("#practice").val("yes");	
			}
			else{
				$("#practice").val("no");
			}
			
		}
		
		function fillIn(index){
			var type = getType1(index);
			var containerDiv = $(question_divs[index]);
			var questionObj = questionArr[index];
			containerDiv.find(".question_text").val(questionObj["questionText"]);
			if (type=="2" || type=="4" || type=="6") {
				containerDiv.find(".pictureURL").val(questionObj["pictureURL"]);
			}
			if (type=="1" || type=="2" || type=="7") {
				for (var i=0; i<questionObj["correctAnswers"].length; i++){
					containerDiv.find(".answer-"+i).val(questionObj["correctAnswers"][i]);
				}
			}
			else if (type=="3" || type=="4"||type=="5" || type=="6"){
				for (var i=0; i<questionObj["possibleAnswers"].length; i++){
					containerDiv.find(".answer-"+(i+1)).val(questionObj["possibleAnswers"][i]);
				}
				for (var i=0; i<questionObj["correctAnswers"].length; i++){
					for (var j=0; j<questionObj["possibleAnswers"].length; j++){
						if(containerDiv.find(".answer-"+(j+1)).val()==questionObj["correctAnswers"][i]){
							containerDiv.find(".correctBox-"+(j+1)).prop("checked",true);
							break;
						}
					}
				}
				
			}
		}
		
		
		function buildQuestionSkeleton() {
			var dropVal = getType1(counter-1);
			console.log("hi");
			console.log(dropVal);
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
		
			
			
			var qText = "Question " + counter + " Text: <textarea class=\"question_text\" type=\"questionText\" name=\"qText\" />\<br>";
			$("#addButton").before("<div class=\"question type-" + dropVal + "\" id=\"question-" + counter + "\">");
			
			if (dropVal === "7") {
				$(divID).append("Indicate a blank with: ** <br>");
			}
			
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
				for (var i=1; i<questionArr[counter-1]["possibleAnswers"].length; i++){
					optionCount++;
					optionButtonID = "addOpt"+ (counter) + "-" + optionCount;
					$(divID).append("<br>Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
					$(divID).append(" Correct? <input type=\"radio\" class=\"correctBox-"+optionCount+"\"name=\"correctBox-"+counter+"\">");
				}
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				console.log("First " + optionButtonID);
				$("#"+optionButtonID).click(addRadioOption);
			}
			
			if (dropVal === "5" || dropVal === "6") {
				var optionCount = 1;
				var optionButtonID = "addOpt"+ (counter) + "-" + optionCount;
				$(divID).append("Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
				$(divID).append(" Correct? <input type=\"checkbox\" class=\"correctBox-"+optionCount+"\"name=\"correctBox\">");
				for (var i=1; i<questionArr[counter-1]["possibleAnswers"].length; i++){
					optionCount++;
					optionButtonID = "addOpt"+ (counter) + "-" + optionCount;
					$(divID).append("<br>Option "+optionCount+": <input class=\"answer-" + optionCount + " answer\"  type=\"qOppField\" name=\"qOppField\"/>");
					$(divID).append(" Correct? <input type=\"checkbox\" class=\"correctBox-"+optionCount+"\"name=\"correctBox\">");
					
				}
				$(divID).append("<button id="+optionButtonID+" type=\"button\">Add Option</button>");
				console.log("First " + optionButtonID);
				$("#"+optionButtonID).click(addOption);
			}
						
			if ((dropVal === "1") || (dropVal === "2") || (dropVal === "7")) {
				var answerCount = 1;
				var answerButtonID = "addAns"+ counter + "-" + answerCount;
				$(divID).append("Answer "+answerCount+": <input class=\"answer answer-0\"  type=\"text\" name=\"qAnswer\"/>");
				for (var i=1; i<questionArr[counter-1]["correctAnswers"].length; i++){
					answerCount++;
					answerButtonID = "addAns"+ counter + "-" + answerCount;
					$(divID).append("<br>Answer "+answerCount+": <input class=\"answer answer-"+i+"\"  type=\"text\" name=\"qAnswer\"/>");
				}
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
			$("#addQ").click(buildQuestionSkeleton);
			counter++;
		}
		
		
		
		function getType1(index) {
			if(questionArr[index]["class"]=="<%=SingleResponseTextQuestion.class.toString()%>") return "1";
			else if(questionArr[index]["class"]=="<%=SingleResponsePicQuestion.class.toString()%>") return "2";
			else if(questionArr[index]["class"]=="<%=MultiChoiceTextQuestion.class.toString()%>"){
				if (questionArr[index]["correctAnswers"].length==1){
					return "3";
				}
				else return "5";
			}
			else if(questionArr[index]["class"]=="<%=MultiChoicePicQuestion.class.toString()%>"){
				if (questionArr[index]["correctAnswers"].length==1){
					return "4";
				}
				else return "6";
			}
			else if(questionArr[index]["class"]=="<%=FillBlankQuestion.class.toString()%>") return "7";
			else return -1;
		}
		
		function createQuestion(index) {

			var html = "";
			var qText = questionArr[index]["questionText"];
			if(questionArr[index]["class"]=="<%=FillBlankQuestion.class.toString()%>") {
				qText = questionArr[index]["questionText"];
				qText = qText.replace("**", "______");
			}
			html += "<div id='question-"+index+"'><h3>Question "+(index+1)+": " + qText +"</h3>";
			

			if(questionArr[index]["class"]=="<%=SingleResponseTextQuestion.class.toString()%>"){
				html += "<h3 style='display:inline;'>Answer: </h3>";
				html += "<input type=\"text\" name=\"answer\" data-id=\"" + index + "\" /><br/>";
			}
			else if(questionArr[index]["class"]=="<%=SingleResponsePicQuestion.class.toString()%>"){
				html += "<img src='http://www.geekosystem.com/wp-content/uploads/2013/12/doge.jpg' "+questionArr[index]["pictureURL"] +" alt='Smiley face' style='max-height:500px; max-width:500px'><br/>";
				html += "<h3 style='display:inline;'>Answer: </h3>";
				html += "<input type=\"text\" name=\"answer\" data-id=\"" + index + "\" /><br/>";
			}
			else if(questionArr[index]["class"]=="<%=MultiChoiceTextQuestion.class.toString()%>"){
				var possibleAnswers = questionArr[index]["possibleAnswers"];
				if (questionArr[index]["correctAnswers"].length==1){
					for(var i=0; i<possibleAnswers.length; i++){
						html +="<input type=\"radio\" class=\"answer-"+i+"\" name=\"checkBox"+index +"\">";
						html +="<h3 style='display:inline;'> Option "+(i+1)+": "+possibleAnswers[i]+"</h3><br\>";
					}
				}
				else{
					for(var i=0; i<possibleAnswers.length; i++){
						html +="<input type=\"checkbox\" class=\"answer-"+i+"\" name=\"checkBox\">";
						html +="<h3 style='display:inline;'> Option "+(i+1)+": "+possibleAnswers[i]+"</h3><br\>";
					}
				}
			}
			else if(questionArr[index]["class"]=="<%=MultiChoicePicQuestion.class.toString()%>"){
				var possibleAnswers = questionArr[index]["possibleAnswers"];
				html += "<img src="+questionArr[index]["pictureURL"] +"alt='Smiley face' height='500' width='500'><br/>";
				if (questionArr[index]["correctAnswers"].length==1){
					for(var i=0; i<possibleAnswers.length; i++){
						html +="<input type=\"radio\" class=\"answer-"+i+"\" name=\"checkBox"+index +"\">";
						html +="<h3 style='display:inline;'> Option "+(i+1)+": "+possibleAnswers[i]+"</h3><br\>";
					}
				}
				else{
					for(var i=0; i<possibleAnswers.length; i++){
						html +="<input type=\"checkbox\" class=\"answer-"+i+"\" name=\"checkBox\">";
						html +="<h3 style='display:inline;'> Option "+(i+1)+": "+possibleAnswers[i]+"</h3><br\>";
					}
				}		
			}
			else if(questionArr[index]["class"]=="<%=FillBlankQuestion.class.toString()%>"){
				html += "<h3 style='display:inline;'>Answer: </h3>";
				html += "<input type=\"text\" name=\"answer\" data-id=\"" + index + "\" /><br/>";
			}


			html += "<br/>";
			html += "</div>";
			$("#container").append(html);

			if (answerArr[index]!=undefined && ($("#question-" + index + " input").attr("type")!="checkbox"&&$("#question-" + index + " input").attr("type")!="radio")) $("#question-" + index + " input").val(answerArr[index][0]);
			if (answerArr[index]!=undefined && ($("#question-" + index + " input").attr("type")=="checkbox"||$("#question-" + index + " input").attr("type")=="radio")){
				var checkboxArr = answerArr[index];
				$("#question-" + index + " input").each(function(index){
					$(this).prop("checked",checkboxArr[index]);
				});
			}
		}
		
	
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
		
			
			
			var qText = "Question " + counter + " Text: <textarea class=\"question_text\" type=\"questionText\" name=\"qText\" />\<br>";
			$("#addButton").before("<div class=\"question type-" + dropVal + "\" id=\"question-" + counter + "\">");
			
			if (dropVal === "7") {
				$(divID).append("Indicate a blank with: ** <br>");
			}
			
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
				window.location.href = "quizSummary.jsp?id=" + response.id;
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
				question.type = getType2(div);
				questions.push(question);
			}
			data = {};
			data.questions = questions;
			data.title = $('#quiz_title').val();
			data.description = $("#quiz_description").val();
			data.feedback = $('#feedback').val();
			data.onePage = $('#onePage').val();
			data.order = $('#order').val();
			data.practice = $('#practice').val();
			return data;
		}
		function getType2(div) {
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