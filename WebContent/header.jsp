<%@ page import="users.User" %>

<div id="navbar">
	<ul>
		<li><a href="index.jsp" class="navbar_link">Quiztopia</a></li>
		<li class="divider"></li>
		<li><a href="login.jsp" class="navbar_link">LOGIN</a></li>
		<li class="divider"></li>
		<li><a href="create_account.jsp" class="navbar_link">Sign Up</a></li>
		<li class="divider"></li>
		<li><a href="#" class="navbar_link">Quizzes</a></li>
		<li class="divider"></li>
		<li><a href="#" class="navbar_link">Admin</a></li>
		<li class="divider"></li>
		<li><a href="user.jsp" class="navbar_link">
			<% 
				User user = (User) session.getAttribute("user");
				if(user != null) out.println(user.getUsername());
				else out.println("Not logged in");
			%>
		</a></li>
	</ul>
</div>

<script type="text/javascript">
	<%
		String active = (String) request.getAttribute("active");
		if(active == null) active = "index.jsp";
	%>
	$(document).ready(function() {
		$(".navbar_link").each(function(i, obj) {
			if($(obj).attr("href") == "<%= active %>") $(obj).addClass("active");
		});
	});
</script>