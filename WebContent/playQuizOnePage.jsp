<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=infoMap.get("title") %></title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		<jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
	</jsp:include>
	
	<h1><%=infoMap.get("title") %></h1>
	<div id="container"">
	</div>
<script>
var startTime;
	$(document).ready(function() {
		//Initialization
		startTime = (new Date).getTime();
		var questionArr = <%= qArrJson%>;
		var answerArr = new Array(questionArr.length);
		console.log(questionArr);
		for (var i=0; i<questionArr.length; i++){
			createQuestion(i);
		}

		$("#container").append( "<button id=\"submit_btn\" type=\"button\" >Submit</button>");

		$("#submit_btn").click(function() {
			var endTime = (new Date).getTime();
			var time = (endTime-startTime)/1000;
			for(var i = 0; i < questionArr.length; i++) addData(i);
			var data = formatData(time);
			console.log(data);
			$.post("ScoreQuizServlet",{data: JSON.stringify(data), question_data: JSON.stringify(<%=qArrJson.toString()%>)}, function(responseJson) {
				console.log(responseJson);
				var response = $.parseJSON(responseJson);
				console.log(response);
				if(response.error) {
					alert(response.error);
				}
				//else alert(response.msg);
				else document.location="scoreSummary.jsp?data="+responseJson;

				//else document.location="scoreSummary.jsp?title="+response["title"]+"&score="+response["score"]+"&totalScore="+response["totalScore"]+"&time="+response["time"];
			});
		});

		function addData(index){
			if ($("#question-" + index + " input").attr("type")!="checkbox" && $("#question-" + index + " input").attr("type")!="radio"){
				var ans = new Array();
				ans[0] = $("#question-" + index + " input").val();
				answerArr[index] = ans;
			}
			else{
				var checkboxArr = new Array();
				var inputs = $("#question-" + index + " input").each(function(index){
					checkboxArr[index] = $(this).is(":checked");
				});
				answerArr[index] = checkboxArr;
			}
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
				html += "<img src='"+questionArr[index]["pictureURL"] +"' alt='Smiley face' style='max-height:500px; max-width:500px'><br/>";
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
				html += "<img src='"+questionArr[index]["pictureURL"] +"' alt='Smiley face' height='500' width='500'><br/>";
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
