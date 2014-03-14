<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<%@ page import="users.User" %>
<%@ page import="databases.QuizHistoryTable" %>
<%@ page import="models.QuizHistory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 	
	User user = (User) session.getAttribute("user");
	Integer id = Integer.parseInt(request.getParameter("id"));
	Quiz q = new Quiz(id);
	if (q==null) System.out.println("quiz is null");
	HashMap<String, Object> infoMap = q.getInfoMap();
	if (infoMap==null) System.out.println("infoMap is null");
	User owner = q.getOwner();
	ArrayList<QuizHistory> attempts = QuizHistoryTable.getUserAttemptsOnQuiz(user, id);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= infoMap.get("title") %></title><link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
		    <jsp:param value="Dynamic Include Examples" name="title"></jsp:param> 
		</jsp:include>
	
	<div id="quiz_info">
		<h1>Quiz: <%=infoMap.get("title") %> </h1>
		<h3>Description: <%=infoMap.get("description")%></h3>
		<h3>Creator: <a href="user.jsp?username=<%= owner.getUsername() %>"><%=infoMap.get("creator")%></a></h3>
		<% 
		if((Boolean)infoMap.get("practiceMode")){
		%><a href="#"><button>Practice Quiz</button></a><%
		}
		%>
		<form style="display:inline-block" action="PlayQuizServlet" method="post">
			<input type="hidden" name="quiz_id" value="<%=id%>">
			<button type="submit">Take the Quiz</button>
		</form>
		<a href="challenge.jsp?id=<%=id%>"><button type="button">Challenge Friend</button></a>
		<br />
		<br />
		<% if(attempts.size() > 0) { %>
		<h3>Past Performances By: 
		<select id="sort_dropdown">
			<option>Date Taken</option>
			<option>Score</option>
			<option>Duration</option>
		</select>
		<div id="past_performance">
		
		</div>
		</h3>
		<% } %>
	</div>
	<script>
	$(document).ready(function() {
		var attempts = [];
		<%
			if(attempts.size() > 0) {
				out.println("attempts = JSON.parse('" + attempts.toString() + "');");
			}
		%>
		$("#sort_dropdown").change(function() {
			if($(this).val() == "Date Taken") {
				showDateSortedAttempts(attempts);
			}
			if($(this).val() == "Score") {
				showScoreSortedAttempts(attempts);
			}
			if($(this).val() == "Duration") {
				showTimeSortedAttempts(attempts);
			}
		});
		if(attempts.length > 0) {
			console.log(attempts);
			showDateSortedAttempts(attempts);
		}
		function showDateSortedAttempts(attempts) {
			attempts.sort(function(a,b) {
				return a.time_ago - b.time_ago;
			});
			setPastPerformance(attempts);
		}
		function showScoreSortedAttempts(attempts) {
			attempts.sort(function(a,b) {
				return b.score - a.score;
			});
			setPastPerformance(attempts);
		}
		function showTimeSortedAttempts(attempts) {
			attempts.sort(function(a,b) {
				return b.time - a.time;
			});
			setPastPerformance(attempts);
		}
		
		function setPastPerformance(attempts) {
			$('#past_performance').html("");
			var html = "";
			html += "<table id='past_performance_table'>";
			html += "<tr><td>Attempt #</td><td>Score</td><td>Duration</td><td>Date</td></tr>";
			for(var i = 0; i < attempts.length; i++) {
				html += "<tr>";
				var count = i+1
				html += "<td>" + count + "</td>";
				html += "<td>" + attempts[i].score + "</td>";
				html += "<td>" + attempts[i].time + "</td>";
				html += "<td>" + attempts[i].at + "</td>";
				html += "</tr>";
			}
			html += "</table>";
			$('#past_performance').html(html);
		}
	});
	</script>
</body>
</html>