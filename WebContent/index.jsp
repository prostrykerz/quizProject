<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="admin.Announcement" %>
<%@ page import="models.Quiz" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="models.QuizHistory" %>
<%@ page import="models.Achievement" %>
<%@ page import="databases.QuizTable" %>
<%@ page import="databases.QuizHistoryTable" %>
<%@ page import="databases.AchievementTable" %>
<%@ page import="java.util.*" %>
<%@ page import="users.FriendUpdate" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	User user = (User) session.getAttribute("user");
	//if(user == null) response.sendRedirect("login.jsp");
	ArrayList<Announcement> announcements = (ArrayList<Announcement>) application.getAttribute("announcements");
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	HashMap<String, Integer> cookieMap = (HashMap<String, Integer>) application.getAttribute("cookieMap");
	Cookie rCookie = null;
	Cookie tCookie = null;
	if(request != null) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals("remember_me")) rCookie = c;
				if(c.getName().equals("token"))tCookie = c;
			}
			if(rCookie != null) {
				if(cookieMap.containsKey(tCookie.getValue())) {
					session.setAttribute("user", manager.getUserById(cookieMap.get(tCookie.getValue())));
					user = (User) session.getAttribute("user");
				}
			}
		}
	}
	
%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Qurious</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="index.jsp"></jsp:param> 
	</jsp:include>
	<% if(user == null) {%>
		<div id="welcome"> 
			<img src="images/Lightbulb.gif" alt="Lightbulb" width="200" height="200">
			<p id="welcomeButtons">
				<a href="/quizProject/login.jsp">
    				<button>Sign In</button>
				</a> 
				<a href="/quizProject/create_account.jsp">
    				<button>Sign Up</button>
				</a>
			</p>
		</div>
	<% } %>

	<% if(user != null) {
		
			// Announcements
			if(announcements.size() > 0) {
				out.println("<div id=\"announcements\">");
				out.println("<h2> Announcements </h2>");
				for(Announcement a : announcements) {
					out.println("<h2 style='margin: 10px; border: 2px solid #FFB6C1'>" + a.getText() + "</h2>");
				}
				out.println("</div>");
			}
			ArrayList<Message> messages = user.getFriendRequests();
			ArrayList<Message> otherMessages = user.getMessages();
			for(int i = 0; i < otherMessages.size(); i++) messages.add(otherMessages.get(i));
			if(messages.size() > 0) {
				out.println("<table class='performance_table'>");
				out.println("<tr><td>From:</td><td>Message</td></tr>");
				for(int i = 0; i < 3; i++) {
					if(i == messages.size()) break;
					User u = manager.getUserById(messages.get(i).getSender());
					out.println("<tr>");
					out.println("<td width='20%'>" + u.getUsername() + "</td>");
					out.println("<td width='80%'>" + messages.get(i).getMessage() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
			out.println("<br />");
			
			/*ArrayList<FriendUpdate> updates = user.getRecentUpdates(5);
			if(updates.size() > 0) {
				out.println("<table class='performance_table'>");
				out.println("<tr><td>Type<1/td><td>Updates</td></tr>");
				for(int i = 0; i < 3; i++) {
					if(i == updates.size()) break;
					out.println("<tr>");
					out.println("<td width='20%'>" + updates.get(i).getType() + "</td>");
					out.println("<td width='80%'>" + updates.get(i).getText() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
			}
			out.println("<br />");
			*/
			
			
			out.println("<div id=\"content\">");
			out.println("<table class='performance_table'><tr><td>");
			// Popular Quizzes
			out.println("<div id=\"popular_quizzes\">");
			out.println("<h2>Popular Quizzes</h2>");
			
			ArrayList<Quiz> topTenQuizzes = QuizTable.getTopQuizzes(10);
			for(int i = 0; i < topTenQuizzes.size(); i++) {
				out.println(i + 1);
				out.println(". <a href='quizSummary.jsp?id=" + topTenQuizzes.get(i).getId() +"'>" + topTenQuizzes.get(i).getTitle() + "</a> with " + topTenQuizzes.get(i).getTimesTaken() + " attempts<br />");
			}
			out.println("</div></td>");
			
			// Recently Created Quizzes
			out.println("<td>");
			out.println("<div id=\"recentCreatedQuizzes\">");
			out.println("<h2> Recently Created Quizzes </h2>");
			
			ArrayList<Quiz> recentCreatedQuizzes = QuizTable.getAllQuizzes();
			out.println("<ul>");
			for (int i = 0; i < 5; i++) {
				if ((recentCreatedQuizzes.size() - 1) -i >= 0) {
					Quiz recentQuiz = recentCreatedQuizzes.get((recentCreatedQuizzes.size() - 1) -i);
					out.println("<li><a href='quizSummary.jsp?id=" + recentQuiz.getId() +"'>" +recentQuiz.getTitle() + "</a></li>");
				}
			}
			out.println("</ul></div></td>");
			
			// User's Recently Created Quizzes
			
			ArrayList<Quiz> userRecentQuizzes = user.getQuizzes();
			if (userRecentQuizzes.size() > 0) {
				out.println("<td>");
				out.println("<div id=\"recentCreatedQuizzes\">");
				out.println("<h2>" + user.getUsername() + "'s Recently Created Quizzes </h2>");
				out.println("<ul>");
				for (int i = 0; i < 5; i++) {
					if ((userRecentQuizzes.size() - 1) -i >= 0) {
						Quiz recentQuiz = userRecentQuizzes.get((userRecentQuizzes.size() - 1) -i);
						out.println("<li><a href='quizSummary.jsp?id=" + recentQuiz.getId() +"'>" +recentQuiz.getTitle() + "</a></li>");
					}
				}
				out.println("</ul></div></td>");
			}
				
			// User's Recently Taken Quizzes
			ArrayList<QuizHistory> recentTakenQuizzes = QuizHistoryTable.getUserTakenQuizzes(user);
			if (recentTakenQuizzes.size() > 0) {
				out.println("<td>");
				out.println("<div id=\"recentCreatedQuizzes\">");
				out.println("<h2>" + user.getUsername() + "'s Recently Taken Quizzes </h2>");
				out.println("<ul>");
				for (int i = 0; i < 5; i++) {
					if ((recentTakenQuizzes.size() - 1) -i >= 0) {
						QuizHistory recentQuiz = recentTakenQuizzes.get((recentTakenQuizzes.size() - 1) -i);
						out.println("<li><a href='quizSummary.jsp?id=" + recentQuiz.getQuizId() +"'>" +recentQuiz.getQuiz().getTitle()  + "</a></li>");
					}
				}
				out.println("</ul></div></td>");
			} else out.println("You haven't taken any quizzes yet!");
			
			// User's Achievments
			
			ArrayList<Achievement> achievements = AchievementTable.getAchievements(user.getId());
			if (achievements.size() > 0) {
				out.println("<td>");
				out.println("<div id=\"userAchievements\">");
				out.println("<h2>" + user.getUsername() + "'s Achievements </h2>");
				out.println("<ul>");
				for (int i = 0; i < achievements.size(); i++) {
					if (achievements.get(i).getStatus()) {
						out.println("<li>"+ achievements.get(i).getText()+"</li>");
					}
				}
				out.println("</ul></div></td>");
			}
			out.println("</tr></table>");
			out.println("</div></div>");
		}
		%>
	<script>
		$(document).ready(function() {
			
		});
	</script>
</body>
</html>
