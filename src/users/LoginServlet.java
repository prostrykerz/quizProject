package users;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		AccountManager manager = (AccountManager) context.getAttribute("manager");
		HashMap<String, Integer> cookieMap = (HashMap<String, Integer>) context.getAttribute("cookieMap");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rememberString = request.getParameter("remember");
		User attempted_user = manager.getUserByUsername(username);
		
		if(manager.passwordMatches(username, password)) {
			if(rememberString.equals("on")) {
				Cookie rememberCookie = new Cookie("remember_me","true");
				String token = getToken(manager.getUserByUsername(username));
				Cookie tokenCookie = new Cookie("token",token);
				rememberCookie.setMaxAge(60*60*24*1);
				tokenCookie.setMaxAge(60*60*24*1);
				response.addCookie(rememberCookie);
				response.addCookie(tokenCookie);
				cookieMap.put(token, attempted_user.getId());
			}
			session.setAttribute("user", attempted_user);
			response.sendRedirect("user.jsp");
		}
		
		else {
			RequestDispatcher dispatch = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", "Incorrect username/password");
			dispatch.forward(request, response);
		}
	}
	
	
	
	public String getToken(User u) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			String saltString = u.getId() + "SUPER SECRET SALT VALUE";
			digest.update(saltString.getBytes("UTF-8"));
			byte[] hash = digest.digest();
			return Base64.encodeBase64String(hash);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
