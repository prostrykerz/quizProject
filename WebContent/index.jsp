<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="messages.Message" %>
<%@ page import="users.AccountManager" %>
<%@ page import="admin.Announcement" %>
<%@ page import="models.Quiz" %>
<%@ page import="models.QuizHistory" %>
<%@ page import="models.Achievement" %>
<%@ page import="databases.QuizTable" %>
<%@ page import="databases.QuizHistoryTable" %>
<%@ page import="databases.AchievementTable" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	User user = (User) session.getAttribute("user");
	//if(user == null) response.sendRedirect("login.jsp");
	ArrayList<Announcement> announcements = (ArrayList<Announcement>) application.getAttribute("announcements");
	AccountManager manager = (AccountManager) application.getAttribute("manager");
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
		
			out.println("<div>");
			out.println("<div id=\"announcements\">");
			out.println("<h2> Announcements </h2>");
			
			out.println("<ul>");
			for(Announcement a : announcements) {
				out.println("<li>" + a.getText() + "</li>");
			}
			out.println("</ul>");
			out.println("</div>");
		
			// Popular Quizzes
			
			out.println("</div><div id=\"content\">");
			out.println("<div id=\"popular_quizzes\">");
			out.println("<h2>Popular Quizzes</h2>");
			
			ArrayList<Quiz> topTenQuizzes = QuizTable.getTopQuizzes(10);
			for(int i = 0; i < topTenQuizzes.size(); i++) {
				out.println(i + 1);
				out.println(". <a href='quizSummary.jsp?id=" + topTenQuizzes.get(i).getId() +"'>" + topTenQuizzes.get(i).getTitle() + "</a> with " + topTenQuizzes.get(i).getTimesTaken() + " attempts<br />");
			}
					
			// Recently Created Quizzes
			
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
			out.println("</ul></div>");
			
			// User's Recently Created Quizzes
			
			ArrayList<Quiz> userRecentQuizzes = user.getQuizzes();
			if (userRecentQuizzes.size() > 0) {
				out.println("<div id=\"recentCreatedQuizzes\">");
				out.println("<h2>" + user.getUsername() + "'s Recently Created Quizzes </h2>");
				out.println("<ul>");
				for (int i = 0; i < 5; i++) {
					if ((userRecentQuizzes.size() - 1) -i >= 0) {
						Quiz recentQuiz = userRecentQuizzes.get((userRecentQuizzes.size() - 1) -i);
						out.println("<li><a href='quizSummary.jsp?id=" + recentQuiz.getId() +"'>" +recentQuiz.getTitle() + "</a></li>");
					}
				}
				out.println("</ul></div>");
			}
				
			// User's Recently Taken Quizzes
			
			out.println("<div id=\"recentCreatedQuizzes\">");
			out.println("<h2>" + user.getUsername() + "'s Recently Taken Quizzes </h2>");
			
			ArrayList<QuizHistory> recentTakenQuizzes = QuizHistoryTable.getUserTakenQuizzes(user);
			if (recentTakenQuizzes.size() > 0) {
				out.println("<ul>");
				for (int i = 0; i < 5; i++) {
					if ((recentTakenQuizzes.size() - 1) -i >= 0) {
						QuizHistory recentQuiz = recentTakenQuizzes.get((recentTakenQuizzes.size() - 1) -i);
						out.println("<li><a href='quizSummary.jsp?id=" + recentQuiz.getQuizId() +"'>" +recentQuiz.getQuiz().getTitle()  + "</a></li>");
					}
				}
				out.println("</ul></div>");
			} else out.println("You haven't taken any quizzes yet!");
			
			// User's Achievments
			
			boolean[] achievments = AchievementTable.getAchievements(user.getId());
			if (achievments.length > 0) {
				out.println("<div id=\"recentCreatedQuizzes\">");
				out.println("<h2>" + user.getUsername() + "'s Achievements </h2>");
				out.println("<ul>");
				for (int i = 0; i < achievments.length; i++) {
					if (achievments[i]) {
						out.println("<li>"+Achievement.getText(i)+"</li>");
					}
				}
				out.println("</ul></div>");
			}
			
			out.println("</div></div>");
		}
		%>
	<script>
		$(document).ready(function() {
			
		});
	</script>
</body>
</html>
