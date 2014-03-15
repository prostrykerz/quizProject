<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="admin.Announcement" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Announcement</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	
	<div class="container" style="margin-top: 10px;">
		<h1>Delete Announcement</h1>
		<form action="DeleteAnnouncementServlet" method="post">
		<select name="announcement">
			<%
				ArrayList<Announcement> announcements = (ArrayList<Announcement>) application.getAttribute("announcements");
				for(Announcement a : announcements) {
					out.println("<option>");
					out.println(a.getText());
					out.println("</option>");
				}
			%>
		</select>
		<button type="submit">Delete Announcement</button>
	</form>
	</div>
</body>
</html>