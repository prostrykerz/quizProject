<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/quizProject/css/foundation.css" type="text/css">
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
</head>
<body>
	<form action="LoginServlet" method="post">
		User Name: <input type="text" name="username" />
		<br />
		Password: <input type="password" name="password" />
		<br />
		<button type="submit">Login</button>
	</form>
	<a href="create_account.jsp">Create New Account</a>
</body>
</html>