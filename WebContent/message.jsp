<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="users.User" %>
<%@ page import="users.AccountManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	AccountManager manager = (AccountManager) application.getAttribute("manager");
	String username = request.getParameter("user");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="index.jsp"></jsp:param> 
	</jsp:include>
	<h2>Message </h2>
	<%
		String error = (String) request.getParameter("error");
		if(error != null) {
			out.println("<div>" + error +  "</div>");
		}
	%>
	<div id="errors" style="display:none"></div>
	<div id="content">
		<form action="MessageServlet" id="msg_form" method="post">
			Receiver: <input type="text" name="receiver" value="<%= username %>"/>
			<br />
			Message: <textarea name="message" placeholder="Enter Message Here"></textarea>
			<br />
			<button type="button" id="send_btn">Send Message</button>
		</form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#send_btn").click(function() {
				$.post("MessageServlet",$('#msg_form').serialize(), function(responseJson) {
					var response = $.parseJSON(responseJson);
					if(response.error) {
						$('#errors').show();
						$('#errors').html("ERROR: " + response.error);
					}
					else $('#content').html(response.msg);
				});
			});
		});
	</script>
</body>
</html>