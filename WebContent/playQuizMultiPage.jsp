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
		
		if ((Boolean)infoMap.get("random")){
			for (int i=0; i<qArrJson.length(); i++){
				int j = (int)Math.floor(Math.random()*(i+1));
				JSONObject temp = qArrJson.getJSONObject(i);
				qArrJson.put(i,qArrJson.getJSONObject(j));
				qArrJson.put(j,temp);
			}
		}
		
	%>
	<h1><%=infoMap.get("title") %></h1>
	<h3 id=immediateScore style="visibility:hidden">Current Score: 0/0</h3>
	<div id="question_box"">
	</div>
<script>
var startTime;
	$(document).ready(function() {
		//Initialization
		startTime = (new Date).getTime();
		var questionArr = <%= qArrJson%>;
		var answerArr = new Array(questionArr.length);
		console.log(questionArr);
		showQuestion(0);
		var currScore = 0;
		var totalScore = 0;
		function showQuestion(index) {
			
			var prevdisabled = "";
			var nextdisabled = "";
			if(index == 0) prevdisabled = "disabled";
			if(<%=(Boolean)infoMap.get("immediateFeedback")%>){
				prevdisabled = "disabled";
				$("#immediateScore").css("visibility", "visible");
				if (index!=0){
					var prevType = getType(index-1);
					if(prevType=="1" || prevType=="2" || prevType=="7") {
						var correct = false;
						var answerKeyArr = questionArr[index-1]["correctAnswers"];
						for (var j=0; j<answerKeyArr.length; j++){
							if (answerKeyArr[j]==(answerArr[index-1])){
								correct = true;
								break;
							}
						}
						if (correct) currScore++;
						totalScore++;
					}
					else if(prevType=="3" || prevType=="4"){
						
						var correct = true;
						var ans = new Array();
						for (var k=0; k<questionArr[index-1]["possibleAnswers"].length; k++){
							var isTrue = false;
							for (var g=0; g<questionArr[index-1]["correctAnswers"].length; g++){
								if (questionArr[index-1]["possibleAnswers"][k]==questionArr[index-1]["correctAnswers"][g]){
									isTrue=true;
									break;
								}
							}
							if (isTrue) ans.push(true);
							else ans.push(false);
						}
						
						for (var j=0; j<ans.length; j++){
							if (ans[j]!=answerArr[index-1][j]){
								correct = false;
								break;
							}	
						}
						if (correct) currScore++;
						totalScore++;
					}
					else if(prevType=="5" || prevType=="6"){
						
						var correct = true;
						var ans = new Array();
						for (var k=0; k<questionArr[index-1]["possibleAnswers"].length; k++){
							var isTrue = false;
							for (var g=0; g<questionArr[index-1]["correctAnswers"].length; g++){
								if (questionArr[index-1]["possibleAnswers"][k]==questionArr[index-1]["correctAnswers"][g]){
									isTrue=true;
									break;
								}
							}
							if (isTrue) ans.push(true);
							else ans.push(false);
						}
						
						for (var j=0; j<ans.length; j++){
							totalScore++;
							if (ans[j]!=answerArr[index-1][j]) continue;
							currScore++;
						}
					}
					$("#immediateScore").text("Current Score: "+currScore+"/"+totalScore);
				}
			}
			if(!<%=(Boolean)infoMap.get("immediateFeedback")%>){	
				if(index == questionArr.length-1) nextdisabled = "disabled";
			}
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
			if(!<%=(Boolean)infoMap.get("immediateFeedback")%>){
				html += "<button id=\"prev_btn\" type=\"button\" " + prevdisabled + ">Prev</button>";
			}
			html += "<button id=\"next_btn\" type=\"button\" " + nextdisabled + ">Next</button>";
			if(!<%=(Boolean)infoMap.get("immediateFeedback")%>){
				html += "<button id=\"submit_btn\" type=\"button\" style='visibility: hidden'>Submit</button>";
			}
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
			
			if(<%=(Boolean)infoMap.get("immediateFeedback")%>){
				$("#next_btn").click(function() {
					addData();
					$("#question-"+index).html("");
					createQuestion(index);
					if (index == questionArr.length-1){
						$("#next_btn").click(function() {
							var endTime = (new Date).getTime();
							var time = (endTime-startTime)/1000;
							var data = formatData(time);
							$.post("ScoreQuizServlet",{data: JSON.stringify(data), question_data: JSON.stringify(<%=qArrJson.toString()%>)}, function(responseJson) {
								console.log(responseJson);
								var response = $.parseJSON(responseJson);
								console.log(response);
								if(response.error) {
									alert(response.error);
								}
								//else alert(response.msg);
								document.location="scoreSummary.jsp?data="+responseJson;
								//else document.location="scoreSummary.jsp?title="+response["title"]+"&score="+response["score"]+"&totalScore="+response["totalScore"]+"&time="+response["time"];
							});
						});
					}
					else{
						$("#next_btn").click(function(){
							showQuestion(index+1);
						});
					}
				});
			}
			else{
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
					var endTime = (new Date).getTime();
					var time = (endTime-startTime)/1000;
					addData();
					var data = formatData(time);
					$.post("ScoreQuizServlet",{data: JSON.stringify(data), question_data: JSON.stringify(<%=qArrJson.toString()%>)}, function(responseJson) {
						console.log(responseJson);
						var response = $.parseJSON(responseJson);
						console.log(response);
						if(response.error) {
							alert(response.error);
						}
						//else alert(response.msg);
						document.location="scoreSummary.jsp?data="+responseJson;
						//else document.location="scoreSummary.jsp?title="+response["title"]+"&score="+response["score"]+"&totalScore="+response["totalScore"]+"&time="+response["time"];
					});
				});
			}
			
			function createQuestion(index) {

				var html = "";
				html += "<h3>Question "+(index+1)+": " + questionArr[index]["questionText"]+"</h3>";
				//html += "<br />";
				
				var correct = false;
				var simple = false;
				var type = getType(index);
				if(type=="1" || type=="2" || type=="7") {
					simple =true;
					if (type=="2") html += "<img src='"+questionArr[index]["pictureURL"] +"' alt='Smiley face' style='max-height:500px; max-width:500px'><br/>";
					html += "<h3 class='attemtedAnswer' style='display:inline;'>Your Input: "+answerArr[index]+"</h3><br/>";
					var answerKeyArr = questionArr[index]["correctAnswers"];
					html += "<h3 class='answer' style='display:inline;'>Possible Answers: ";
					for (var j=0; j<answerKeyArr.length; j++){
						if (answerKeyArr[j]==(answerArr[index])){
							correct = true;
						}
						if (j==answerKeyArr.length-1) html+= answerKeyArr[j];
						else html+= answerKeyArr[j]+", ";
					}
					html += "</h3><br/>";
					 
					
				}
				else if(type=="3" || type=="4"||type=="5" || type=="6"){
					if (type=="4" || type=="6") html += "<img src='"+questionArr[index]["pictureURL"] +"' alt='Smiley face' style='max-height:500px; max-width:500px'><br/>";
					
					html += "<ul>";
					var correct = true;
					var ans = new Array();
					for (var k=0; k<questionArr[index]["possibleAnswers"].length; k++){
						var isTrue = false;
						for (var g=0; g<questionArr[index]["correctAnswers"].length; g++){
							if (questionArr[index]["possibleAnswers"][k]==questionArr[index]["correctAnswers"][g]){
								isTrue=true;
								break;
							}
						}
						if (isTrue) ans.push(true);
						else ans.push(false);
						
					}
					console.log(ans);
					console.log(questionArr[index]);
					if (type=="3" || type=="4"){
						for (var j=0; j<ans.length; j++){
							if ((ans[j]==true) && ans[j]==answerArr[index][j]){
								html += "<li class='answer' style='border:2px solid green;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else if ((ans[j]==true) && ans[j]!=answerArr[index][j]){
								html += "<li class='answer' style='border:2px dotted green;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else if ((answerArr[index][j]==true) && ans[j]!=answerArr[index][j]){
								html += "<li class='answer' style='border:2px solid red;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else{
								html += "<li class='answer'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li><br/>";
							}
							
						}
					}
					else{
						for (var j=0; j<ans.length; j++){
							if ((ans[j]==true) && ans[j]==answerArr[index][j]){
								html += "<li class='answer' style='border:2px solid green;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else if ((ans[j]==true) && ans[j]!=answerArr[index][j]){
								html += "<li class='answer' style='border:2px dotted green;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else if ((answerArr[index][j]==true) && ans[j]!=answerArr[index][j]){
								html += "<li class='answer' style='border:2px solid red;'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li>";
							}
							else{
								html += "<li class='answer'>Answer: "+ questionArr[index]["possibleAnswers"][j]+"</li><br/>";
							}
						}
					}
					
					html+="</ul>"
				}

				html += "<button id=\"next_btn\" type=\"button\" " + nextdisabled + ">Next</button>";
				
				html += "<br/>";
				$("#question-"+index).append(html);
				if (correct && simple) $("#question-"+index+" .attemtedAnswer").css("border","2px green solid");
				if (!correct && simple) $("#question-"+index+" .attemtedAnswer").css("border","2px red solid");
				
			}
			
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
		
		
		function formatData(time) {
			var data = {};
			var correctAnswers = new Array();
			var attemptedAnswers = new Array();
			var type = new Array();
			for(var i = 0; i < questionArr.length; i++) {
				type.push(getType(i));
				var ans = new Array();
				if (type[i]=="1" || type[i]=="2" || type[i]=="7"){
					for (var j=0; j<questionArr[i]["correctAnswers"].length; j++){
						ans.push(questionArr[i]["correctAnswers"][j]);
					}
				}
				else{
					for (var k=0; k<questionArr[i]["possibleAnswers"].length; k++){
						var isTrue = false;
						for (var g=0; g<questionArr[i]["correctAnswers"].length; g++){
							if (questionArr[i]["possibleAnswers"][k]==questionArr[i]["correctAnswers"][g]){
								isTrue=true;
								break;
							}
						}
						if (isTrue) ans.push(true);
						else ans.push(false);
					}
				}
				correctAnswers.push(ans);
				
				var attemptedAns = new Array();
				for (var j=0; j<answerArr[i].length; j++){
					attemptedAns.push(answerArr[i][j]);
				}
				attemptedAnswers.push(attemptedAns);
			}
			
			data.correctAnswers = correctAnswers;
			data.attemptedAnswers = attemptedAnswers;
			data.type = type;
			data.time = time;
			data.title = "<%=infoMap.get("title") %>";
			<% if(q != null) out.println("data.quiz_id = " + id + ";");%>
			
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
