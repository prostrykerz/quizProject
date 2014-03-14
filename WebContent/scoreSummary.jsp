<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="org.json.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<%@ page import="users.User" %>
<%@ page import="databases.QuizHistoryTable" %>
<%@ page import="users.AccountManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Score</title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
		</jsp:include>
	<%
	JSONObject data = new JSONObject(request.getParameter("data"));	
	User user = (User) session.getAttribute("user");
	ArrayList<QuizHistory> attempts = QuizHistoryTable.getUserAttemptsOnQuiz(user, (Integer) data.get("quiz_id"));
	Collections.sort(attempts, new Comparator<QuizHistory>() {
		public int compare(QuizHistory q1, QuizHistory q2) {
			// TODO Auto-generated method stub
			long diff = q2.getCreatedAt().getTime() - q1.getCreatedAt().getTime();
			return (int) diff;
		}
	});
	ArrayList<QuizHistory> topAttempts = QuizHistoryTable.getTopPerformers((Integer) data.get("quiz_id"), 10);

	QuizHistory curQH = new QuizHistory(user.getId(), (int)(Integer) data.get("quiz_id"), (int)(Integer) data.get("score"), (int)(Integer) data.get("time"), new Date());
	topAttempts.add(0, curQH);
	ArrayList<User> friends = user.getFriendsAsUsers();
	ArrayList<QuizHistory> friendAttempts = new ArrayList<QuizHistory>();
	for(User u : friends) {
		ArrayList<QuizHistory> userAttempts = QuizHistoryTable.getUserAttemptsOnQuiz(u,(int)(Integer) data.get("quiz_id"));
		friendAttempts.addAll(userAttempts);
	}
	Collections.sort(friendAttempts, new Comparator<QuizHistory>() {
		@Override
		public int compare(QuizHistory q1, QuizHistory q2) {
			// TODO Auto-generated method stub
			int diff = q2.getScore() - q1.getScore();
			if(diff == 0) return q1.getTime() - q2.getTime();
			return diff;
		}
	});
	friendAttempts.add(0, curQH);
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	%>

	<h1>Quiz Summary for <%=data.get("title").toString() %> </h1>
	<h3>Score: <%=data.get("score").toString()%>/<%=data.get("totalScore").toString()%></h3>
	<%
	String timeStr = (String)data.get("time").toString();
	int time =0;
	if (timeStr!=null){
		time = (Integer.parseInt(timeStr));
	}
	int minutes = time/60;
	int seconds = time%60;
	String secondsString = String.valueOf(seconds);
	if(seconds <= 9) secondsString = "0" + seconds;
	
	%>
	<h3>Time: <%=minutes%>:<%=secondsString%></h3>
	<a href="quizList.jsp"><button>Play More Quizzes!</button></a>
	<h2>Results</h2>
	<div id="container"></div>
	
	<div>
		<h3>
		Your Past Performances
		<table class="performance_table">
			<tr>
				<td>Attempt #</td>
				<td>Score</td>
				<td>Score Difference</td>
				<td>Duration</td>
				<td>Duration Difference</td>
			</tr>
			<%
				for(int i = 0; i < attempts.size(); i++) {
					String username = manager.getUserById(attempts.get(i).getUserId()).getUsername();
					out.println("<tr>");
					if(i == 0) out.println("<td>" + "This Attempt" + "</td>");
					else out.println("<td>" + i + "</td>");
					out.println("<td>" + attempts.get(i).getScore() + "</td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((int)(Integer)data.get("score"), attempts.get(i).getScore())) + "%</td>");
					out.println("<td>" + attempts.get(i).getTime() + " seconds </td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((Integer)data.get("time"), attempts.get(i).getTime())) + "%</td>");
					out.println("</tr>");
				}
			%>
		</table>
		</h3>
	</div>
	
	<div>
		<h3>
		You Compared to Top Performers
		<table class="performance_table">
			<tr>
				<td>Username</td>
				<td>Score</td>
				<td>Score Difference</td>
				<td>Duration</td>
				<td>Duration Difference</td>
			</tr>
			<%
				for(int i = 0; i < topAttempts.size(); i++) {
					String username = manager.getUserById(topAttempts.get(i).getUserId()).getUsername();
					out.println("<tr>");
					if(i == 0) out.println("<td>" + "YOU" + "</td>");
					else out.println("<td><a href='user.jsp?username=" + username + "'>" + username + "</a></td>");
					out.println("<td>" + topAttempts.get(i).getScore() + "</td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((int)(Integer)data.get("score"), topAttempts.get(i).getScore())) + "%</td>");
					out.println("<td>" + topAttempts.get(i).getTime() + " seconds </td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((Integer)data.get("time"), topAttempts.get(i).getTime())) + "%</td>");
					out.println("</tr>");
				}
			%>
		</table>
		</h3>
	</div>
	
	<div>
		<h3>
		You Compared to Your Friends
		<table class="performance_table">
			<tr>
				<td>Username</td>
				<td>Score</td>
				<td>Score Difference</td>
				<td>Duration</td>
				<td>Duration Difference</td>
			</tr>
			<%
				for(int i = 0; i < friendAttempts.size(); i++) {
					String username = manager.getUserById(friendAttempts.get(i).getUserId()).getUsername();
					out.println("<tr>");
					if(i == 0) out.println("<td>" + "YOU" + "</td>");
					else out.println("<td><a href='user.jsp?username=" + username + "'>" + username + "</a></td>");
					out.println("<td>" + friendAttempts.get(i).getScore() + "</td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((int)(Integer)data.get("score"), friendAttempts.get(i).getScore())) + "%</td>");
					out.println("<td>" + friendAttempts.get(i).getTime() + " seconds </td>");
					out.println("<td>" + String.format("%.1f",Quiz.getPercentDifferent((Integer)data.get("time"), friendAttempts.get(i).getTime())) + "%</td>");
					out.println("</tr>");
				}
			%>
		</table>
		</h3>
	</div>
	
	<script>
var startTime;
	$(document).ready(function() {
		console.log(<%=request.getParameter("data")%>);
		//Initialization
		startTime = (new Date).getTime();
		
		var questionArr = <%= new JSONArray(data.get("quiz_data").toString())%>;
		
		var answerArr = <%= new JSONArray(data.get("attemptedAnswers").toString())%>;

		console.log(answerArr);
		
		for (var i=0; i<questionArr.length; i++){
			console.log(questionArr);
			createQuestion(i);
		}

		function createQuestion(index) {

			var html = "";
			html += "<div id='question-"+index+"'><h3>Question "+(index+1)+": " + questionArr[index]["questionText"]+"</h3>";
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


			html += "<br/>";
			html += "</div>";
			$("#container").append(html);
			if (correct && simple) $("#question-"+index+" .attemtedAnswer").css("border","2px green solid");
			if (!correct && simple) $("#question-"+index+" .attemtedAnswer").css("border","2px red solid");
			
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
