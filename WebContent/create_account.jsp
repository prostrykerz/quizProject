<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="create_account.jsp"></jsp:param> 
	</jsp:include>
	
	<div id="login_box">
		<h2>Create Account</h2>
		<hr />
		<form action="CreateAccountServlet" method="post">
		<%
			String error = (String) request.getAttribute("error");
			if(error != null) {
				out.println("<div><h2>" + error +  "<h2></div>");
				out.println("<br />");
			}
		%>
			<label>User Name:</label> <input type="text" name="username" />
			<br />
			<br />
			<label>Password:</label> <input type="password" name="password" />
			<br />
			<button type="submit">Create Account</button>
		</form>
	</div>
</body>
</html>