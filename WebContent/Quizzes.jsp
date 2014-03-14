<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Quiz</title>
<link rel="stylesheet" href="/quizProject/css/style.css" type="text/css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp">
	    <jsp:param value="active" name="admin.jsp"></jsp:param> 
	</jsp:include>
	
	<div id="quizzesPortal"> 
			<a class="figure" href="/quizProject/quizList.jsp">
    			<img id="takeQuizPic" src="images/takeQuiz.png" alt="Lightbulb" width="200" height="200">
    			<p>Take a quiz</p>
			</a>   
			<a class="figure" href="/quizProject/quizCreation.jsp">
    			<img id="buildQuizPic" src="images/buildQuiz.png" alt="Lightbulb" width="200" height="200">
    			<p>Make a quiz</p>
			</a>	
	</div>
</body>
</html>