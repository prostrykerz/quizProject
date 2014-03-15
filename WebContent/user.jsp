<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*" %>
<%@ page import="models.Quiz" %>
<%@ page import="models.QuizHistory" %>
<%@ page import="databases.*" %>
<%@ page import="models.Achievement" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	User curuser = (User) session.getAttribute("user");
	String username = request.getParameter("username");
	User user = null;
	if(username == null) user = curuser;
	else user = manager.getUserByUsername(username);
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
	<div class="container">
	<h1><%= user.getUsername() %>'s Page</h1>
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
				out.println("<button id=\"add_friend_btn\" type=\"button\">Add as Friend</button>");
			}
			else {
				for(Message fr : user.getFriendRequests()) {
					if(manager.getUserById(fr.getSender()).equals(curuser.getUsername())) {
						out.println("<button type=\"button\"Friend Request Sent<button>");
					}
				}
			}
		}
	%>
	<%
		if(curuser != null && !curuser.equals(user)) {
			out.println("<a href='message.jsp?user=" + user.getUsername() + "'><button type='button'>Message User</button></a>");
		}
	%>
	<a href="history.jsp"><button type="button">Quiz History</button></a>
	<h2>Friends</h2>
	<%
		if(user.getFriends().size() > 0) {
			out.println("<table>");
			for(int id : user.getFriends()) {
				User u = manager.getUserById(id);
				if(u != null) {
					out.println("<tr><td>" + u.getUsername() + " <a class=\"remove_friend_btn\" data-user=\"" + u.getUsername() + "\" href=\"#\">Remove Friend</a></td></tr>");
				}
			}
			out.println("</table>");
		}
	%>
	<br />
	<h2>Quizzes</h2>
	<%
		ArrayList<Quiz> quizzes = user.getQuizzes();
		if(quizzes.size() > 0) {
			out.println("<table>");
			for(Quiz q : quizzes) {
				out.println("<tr><td><a href=\"quizSummary.jsp?id=" + q.getId() + "\">" + q.getTitle() + "</a></td></tr>");
			}
			out.println("</table>");
		}
		
	%>
	<h2>Achievements</h2>
	<%
		ArrayList<Achievement> achievements = AchievementTable.getAchievements(user.getId());
		if(achievements.size() > 0) {
			out.println("<table>");
			for(int i = 0; i < achievements.size(); i++) {
				if(achievements.get(i).getStatus()){ 
					out.println("<tr><td>" + achievements.get(i).getText() + "</td></tr>");
				}
			}
			out.println("</table>");
		}
		
	%>
	</div>
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