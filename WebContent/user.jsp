<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<%@ page import="models.QuizHistory" %>
<%@ page import="databases.QuizHistoryTable" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	User curuser = (User) session.getAttribute("user");
	String username = request.getParameter("username");
	User user = null;
	for(User u: manager.getUsers()) {
		if(u.getUsername().equals(username)) user = u;
	}
	ArrayList<QuizHistory> attempts = QuizHistoryTable.getUserTakenQuizzes(user);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= user.getUsername() %></title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="user.jsp"></jsp:param> 
	</jsp:include>
	<%
		String error = (String) request.getParameter("error");
		if(error != null) {
			out.println("<div>" + error +  "</div>");
		}
	%>
	<div id="errors" style="display:none"></div>
	<%
		if(curuser != null && !curuser.equals(user)) {
			if(!(curuser.getFriends().contains(user))) {
				out.println("<button id=\"add_friend_btn\" type=\"button\">Add as Friend</button><br />");
			}
			else {
				for(Message fr : user.getFriendRequests()) {
					if(fr.getSender().getUsername().equals(curuser.getUsername())) {
						out.println("<button type=\"button\"Friend Request Sent<button>");
					}
				}
			}
		}
	%>
	<%
		if(curuser != null && !curuser.equals(user)) {
			out.println("<a href='message.jsp?user=" + user.getUsername() + "'>Message User</a>");
		}
	%>
	<br />
	Welcome to <%= user.getUsername() %>'s page
	<h2>My Friends</h2>
	<%
		for(int id : user.getFriends()) {
			for(User u : manager.getUsers()) {
				if(u.getId() == id) {
					out.println(u.getUsername() + " <a class=\"remove_friend_btn\" data-user=\"" + u.getUsername() + "\" href=\"#\">Remove Friend</a><br/>");
					break;
				}
			}
		}
	%>
	<br />
	<h2>Quizzes</h2>
	<%
		ArrayList<Quiz> quizzes = user.getQuizzes();
		for(Quiz q : quizzes) {
			out.println("<a href=\"quizSummary.jsp?id=" + q.getId() + "\">" + q.getTitle() + "</a>");
			out.println("<br />");
		}
	%>
	<h2>Achievements</h2>
	<%
		ArrayList<String> achievements = user.getAchievements();
		for(String s : achievements) {
			out.println(s + "<br />");
		}
	%>
	<h2>Attempts</h2>
	<%
		for(QuizHistory qh : attempts) {
			out.println(qh.getQuizId() + "<br />");
		}
	%>
	<script>
		$(document).ready(function() {
			$('#add_friend_btn').click(function() {
				$.post("FriendRequestServlet",{user: "<%= user.getUsername() %>"}, function(responseJson) {
					var response = $.parseJSON(responseJson);
					console.log(response);
					if(response.error) {
						$('#errors').show();
						$('#errors').html("ERROR: " + response.error);
					}
					else $('#add_friend_btn').html(response.msg);
				});
			});
			$(".remove_friend_btn").click(function(e) {
				e.preventDefault();
				var that = this;
				var user = $(this).attr("data-user");
				$.post("RemoveFriendServlet", {user: user}, function(responseJson) {
					var response = $.parseJSON(responseJson);
					console.log(response);
					//$(that).html(response.msg);
				});
			});
		});
	</script>
</body>
</html>