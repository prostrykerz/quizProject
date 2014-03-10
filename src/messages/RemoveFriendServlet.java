package messages;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import users.AccountManager;
import users.User;

/**
 * Servlet implementation class RemoveFriendServlet
 */
@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFriendServlet() {
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
		AccountManager manager = (AccountManager) context.getAttribute("manager");
		HttpSession session = request.getSession();
		User curuser = (User) session.getAttribute("user");
		String username = request.getParameter("user");
		String error = validations(curuser, username, manager);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(error != "") {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"error\": \"" + error + "\"");
			sb.append("}");
			response.getWriter().write(sb.toString());
		}
		else {
			User.removeFriend(curuser, manager.getUserByUsername(username));
			String json = "{ \"msg\": \"Friendship Revoked\"}";
			response.getWriter().write(json);
		}
	}
	
	private String validations(User user, String username, AccountManager manager) {
		if(user == null) return "Please Login";
		if(!manager.userExists(username)) return "No user found by name of: " + username;
		boolean friendExists = false;
		for(Integer id : user.getFriends()) {
			for(User u : manager.getUsers()) {
				if(u.getId() == id) {
					if(u.getUsername().equals(username)) {
						friendExists = true;
						break;
					}
				}
			}
			if(friendExists) break;
		}
		if(!friendExists)return "Not friends";
		return "";
	}
}
