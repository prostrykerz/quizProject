package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import messages.Message;
import models.Quiz;
import databases.MessageTable;

import users.AccountManager;
import users.User;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
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
		String uidS = request.getParameter("uid");
		String qidS = request.getParameter("qid");
		int uid = Integer.parseInt(uidS);
		int qid = Integer.parseInt(qidS);
		Quiz quiz = new Quiz(qid);
		User u = manager.getUserById(uid);
		if(u != null) {
			String text = constructChallenge(user, u, quiz);
			Message msg = MessageTable.save(user, u, text);
			u.addMessage(msg);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("quizSummary.jsp?id=" + qid);
		dispatch.forward(request, response);
	}
	
	private String constructChallenge(User sender, User receiver, Quiz q) {
		StringBuilder sb = new StringBuilder();
		sb.append(sender.getUsername());
		sb.append(" got a INSERT SCORE on ");
		sb.append("<b><a href=\"quizSummary.jsp?id=" + q.getId() + "\">");
		sb.append(q.getTitle());
		sb.append("</a></b>");
		sb.append(" Can you beat me?");
		return sb.toString();
	}
}
