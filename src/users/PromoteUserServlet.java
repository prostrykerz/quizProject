package users;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PromoteUserServlet
 */
@WebServlet("/PromoteUserServlet")
public class PromoteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromoteUserServlet() {
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
		HashSet<User> users = manager.getUsers();
		String username = request.getParameter("username");
		Iterator<User> it = users.iterator();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				u.makeAdmin();
			}
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("admin_dashboard.jsp");
		dispatch.forward(request, response);
	}

}
