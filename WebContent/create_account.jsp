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
	<%
		String error = (String) request.getAttribute("error");
		if(error != null) {
			out.println("<div>" + error +  "</div>");
		}
	%>
	<form action="CreateAccountServlet" method="post">
		User Name: <input type="text" name="username" />
		<br />
		Password: <input type="password" name="password" />
		<br />
		<button type="submit">Create Account</button>
	</form>
</body>
</html>