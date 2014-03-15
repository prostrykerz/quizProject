package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Question;
import models.Quiz;

/**
 * Servlet implementation class PracticeQuizServlet
 */
@WebServlet("/PracticeQuizServlet")
public class PracticeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PracticeQuizServlet() {
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
		Integer id = Integer.parseInt(request.getParameter("quiz_id"));
		Quiz q = new Quiz(id);
		ArrayList<Question> qArr = q.getQuestionArr();
		request.setAttribute("questionArr", qArr);
		Boolean onePage = (Boolean) q.getInfoMap().get("onePage");
		if(onePage){
			RequestDispatcher rd = request.getRequestDispatcher("practiceQuizOnePage.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd = request.getRequestDispatcher("practiceQuizMultiPage.jsp");
			rd.forward(request, response);
		}
	}

}
