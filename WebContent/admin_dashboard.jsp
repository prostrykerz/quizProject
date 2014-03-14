<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	<h2><a href="create_announcement.jsp">Create Announcement</a></h2>
	<h2><a href="delete_announcement.jsp">Delete Announcement</a></h2>
	<h2><a href="clear_quiz_history.jsp">Clear Quiz History</a></h2>
	<h2><a href="remove_user.jsp">Remove User</a></h2>
	<h2><a href="remove_quiz.jsp">Remove Quiz</a></h2>
	<h2><a href="promote_user.jsp">Promote User to Admin</a></h2>
</body>
</html>