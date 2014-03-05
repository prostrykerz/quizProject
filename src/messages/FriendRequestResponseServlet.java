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
 * Servlet implementation class FriendRequestResponseServlet
 */
@WebServlet("/FriendRequestResponseServlet")
public class FriendRequestResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestResponseServlet() {
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
		User user = (User) session.getAttribute("user");
		String requesterusername = request.getParameter("user");
		String accept = request.getParameter("accept");
		String error = validations(user, requesterusername, manager);
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
			User requester = manager.getUserByUsername(requesterusername);
			String json = "";
			if(accept.equals("true")) {
				requester.addFriend(user);
				user.addFriend(requester);
				user.deleteFriendRequest(requester);
				json = "{ \"msg\": \"Friend Request Accepted\"}";
			}
			else {
				user.deleteFriendRequest(requester);
				json = "{ \"msg\": \"Friend Request Denied\"}";
			}
			response.getWriter().write(json);
		}
	}
	
	private String validations(User user, String requesterusername, AccountManager manager) {
		if(user == null) return "Please Login";
		if(!manager.userExists(requesterusername)) return "No user found by name of: " + requesterusername;
		boolean requestExists = false;
		for(Message fr : user.getFriendRequests()) {
			if(fr.getSender().getUsername().equals(requesterusername)) requestExists = true;
		}
		if(!requestExists)return "No friend request found";
		return "";
	}

}
