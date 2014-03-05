package messages;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
		String receiverusername = request.getParameter("receiver");
		String message = request.getParameter("message");
		String error = validations(user, receiverusername, manager);
		if(error != "") {
			//send back to message form
		}
		else {
			//create and send message then forward
		}
//		if(manager.userExists(username)) {
//			RequestDispatcher dispatch = request.getRequestDispatcher("create_account.jsp");
//			request.setAttribute("error", "Username taken");
//			dispatch.forward(request, response);
//		}
//		else {
//			RequestDispatcher dispatch = request.getRequestDispatcher("user.jsp");
//			request.setAttribute("username", username);
//			dispatch.forward(request, response);
//		}
	}

	private String validations(User user, String receiverusername, AccountManager manager) {
		if(user == null) return "Please Login";
		if(!manager.userExists(receiverusername)) return "No user found by name of: " + receiverusername;
		return "";
	}
}
