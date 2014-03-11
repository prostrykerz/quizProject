<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
	<%
		ArrayList<Question> qArr = (ArrayList<Question>)request.getAttribute("questionArr");
		//ArrayList<Question> qArr1 = new ArrayList<Question>();
		//SingleResponseTextQuestion q1 = new SingleResponseTextQuestion("how are you?", new ArrayList<String>(Arrays.asList("good")), 1);
		//SingleResponseTextQuestion q2 = new SingleResponseTextQuestion("how are you?", new ArrayList<String>(Arrays.asList("bad")), 2);
		//qArr.add(q1);
		//qArr.add(q2);
		
		JSONArray qArrJson = new JSONArray();
		if(qArr != null) {
			for (int i = 0; i < qArr.size(); i++){
				qArrJson.put(qArr.get(i).getJSON());
			}
		}
		Integer id = Integer.parseInt(request.getParameter("quiz_id"));
		Quiz q = new Quiz(id);
		if (q==null) System.out.println("quiz is null");
		HashMap<String, Object> infoMap = q.getInfoMap();
		if (infoMap==null) System.out.println("infoMap is null");
		//System.out.println(qArrJson.toString());
	%>
	<h1>Quiz: <%=infoMap.get("title") %></h1>
	<div id="question_box"">
		WILL BE REPLACED BY JAVASCRIPT
	</div>
<script>
	$(document).ready(function() {
		//Initialization
		var questionArr = <%= qArrJson%>;
		var answerArr = new Array(questionArr.length);
		console.log(questionArr);
		showQuestion(0);
		
		function showQuestion(index) {
			
			var prevdisabled = "";
			var nextdisabled = "";
			if(index == 0) prevdisabled = "disabled";
			if(index == questionArr.length-1) nextdisabled = "disabled";
			var html = "";
			html += "<h3>Question: " + questionArr[index]["questionText"]+"</h3>";
			//html += "<br />";
			
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
			html += "<button id=\"prev_btn\" type=\"button\" " + prevdisabled + ">Prev</button>";
			html += "<button id=\"next_btn\" type=\"button\" " + nextdisabled + ">Next</button>";
			html += "<button id=\"submit_btn\" type=\"button\" style='visibility: hidden'>Submit</button>";
			$("#question_box").html("");
			$("#question_box").html(html);
			
			if (answerArr[index]!=undefined && ($("input").attr("type")!="checkbox"&&$("input").attr("type")!="radio")) $("input").val(answerArr[index][0]);
			if (answerArr[index]!=undefined && ($("input").attr("type")=="checkbox"||$("input").attr("type")=="radio")){
				var checkboxArr = answerArr[index];
				$("input").each(function(index){
					$(this).prop("checked",checkboxArr[index]);
				});
			}
			if(index == questionArr.length-1){
				$("#submit_btn").css("visibility","visible");
			}
			$("#next_btn").click(function() {
				addData();
				showQuestion(index+1);
			});
			$("#prev_btn").click(function() {
				addData();
				$("#submit_btn").css("visibility","hidden");
				showQuestion(index-1);
			});
			$("#submit_btn").click(function() {
				addData();
				var data = formatData();
				console.log(data);
				$.post("ScoreQuizServlet",{data: JSON.stringify(data)}, function(responseJson) {
					console.log(responseJson);
					var response = $.parseJSON(responseJson);
					console.log(response);
					if(response.error) {
						alert(response.error);
					}
					else alert(response.msg);
				});
			});
			function addData(){
				if ($("input").attr("type")!="checkbox" && $("input").attr("type")!="radio"){
					var ans = new Array();
					ans[0] = $("input").val();
					answerArr[index] = ans;
				}
				else{
					var checkboxArr = new Array();
					var inputs = $("input").each(function(index){
						checkboxArr[index] = $(this).is(":checked");
					});
					answerArr[index] = checkboxArr;
				}
			}
		}
		
		
		function formatData() {
			var data = {};
			var correctAnswers = new Array();
			var attemptedAnswers = new Array();
			var type = new Array();
			for(var i = 0; i < questionArr.length; i++) {
				var ans = new Array();
				for (var j=0; j<questionArr[i]["correctAnswers"].length; j++){
					ans.push(questionArr[i]["correctAnswers"][j]);
				}
				correctAnswers.push(ans);
				
				var attemptedAns = new Array();
				for (var j=0; j<answerArr[i].length; j++){
					if (answerArr[i][j]){
						attemptedAns.push(questionArr[i]["possibleAnswers"][j]);
					}
				}
				attemptedAnswers.push(attemptedAns);
				type.push(getType(i));
			}
			
			data.correctAnswers = correctAnswers;
			data.attemptedAnswers = attemptedAnswers;
			data.type = type;
			data.title = "<%=infoMap.get("title") %>";
			
			return data;
		}
		function getType(index) {
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
	});
	
</script>
</body>
</html>
