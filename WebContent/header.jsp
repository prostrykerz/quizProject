<%@ page import="users.User" %>

<% User user = (User) session.getAttribute("user"); %>

<div id="navbar">
<div class="container">
	<ul>
		<li><a href="/quizProject/index.jsp" class="navbar_link">Qurious</a></li>
		<li class="divider"></li>
		<% if(user == null) {
			out.println("<li><a href='/quizProject/login.jsp' class='navbar_link'>Sign In</a></li>");
			out.println("<li class='divider'></li>");
			out.println("<li><a href='/quizProject/create_account.jsp' class='navbar_link'>Sign Up</a></li>");
			out.println("<li class='divider'></li>");
		}
		%>
		<% if(user != null) {
			out.println("<li><a href=\"Quizzes.jsp\" class=\"navbar_link\">Quizzes</a></li>");
			out.println("<li class='divider'></li>");
			out.println("<li><a href=\"find_users.jsp\" class=\"navbar_link\">Find Users</a></li>");
			out.println("<li class='divider'></li>");
			if(user.isAdmin()) {
				out.println("<li><a href='admin_dashboard.jsp' class='navbar_link'>Admin</a></li>");
				out.println("<li class='divider'></li>");
			}
			out.println("<li><a href='/quizProject/messages.jsp' class='navbar_link'>Messages</a></li>");
			out.println("<li class='divider'></li>");
			out.println("<li><a href='/quizProject/user.jsp?username=" + user.getUsername() + "' class='navbar_link'>" + user.getUsername() + "</a></li>");
			out.println("<li class='divider'></li>");
			out.println("<li><a href=\"LogOutServlet\" class=\"navbar_link\">Sign Out</a></li>");
		}
		%>
	</ul>
	</div>
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
