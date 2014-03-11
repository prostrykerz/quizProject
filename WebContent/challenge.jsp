<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="users.AccountManager" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	User curuser = (User) session.getAttribute("user");
	String id = request.getParameter("id");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Challenge Quiz</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name=""></jsp:param> 
	</jsp:include>
	<form action="ChallengeServlet" method="post">
		<input type="hidden" name="qid" value="<%=id%>">
		<select name="uid">
			<%
				for(int uid : curuser.getFriends()) {
					out.println("<option value=\"" + uid + "\">");
					for(User u : manager.getUsers()) {
						if(u.getId() == uid) {
							out.println(u.getUsername());
							break;
						}
					}
					out.println("</option>");
				}
			%>
		</select>
		<button type="submit">Challenge Friend</button>
	</form>
	
</body>
</html>