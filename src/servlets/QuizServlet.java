package servlets;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.FillBlankQuestion;
import models.MultiChoicePicQuestion;
import models.MultiChoiceTextQuestion;
import models.Question;
import models.Quiz;
import models.SingleResponsePicQuestion;
import models.SingleResponseTextQuestion;

import users.AccountManager;
import users.User;

import org.json.*;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Show a specific Quiz
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Create quiz
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		AccountManager manager = (AccountManager) context.getAttribute("manager");
		User user = (User) session.getAttribute("user");
		if(user == null) return;
		System.out.println((String)request.getParameter("questions"));
		JSONObject o = new JSONObject((String)request.getParameter("questions"));
		
		ArrayList<Question> questions = new ArrayList<Question>();
		int counter = 1;
		while(true) {
			String question_type = request.getParameter("mydropdown" + counter);
			
			if(question_type == null) break;
			if(question_type.equals("Text Question-Response")) {
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("test");
				String text = "Question";
				SingleResponseTextQuestion q = new SingleResponseTextQuestion(text, answers, counter);
				questions.add(q);
			}
			else if(question_type.equals("Picture Question-Response")) {
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("test");
				String text = "Question";
				SingleResponsePicQuestion q = new SingleResponsePicQuestion(text, "url", answers, counter);
				questions.add(q);
			}
			else if(question_type.equals("Text Multiple-Choice")) {
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("test");
				String text = "Question";
				MultiChoiceTextQuestion q = new MultiChoiceTextQuestion(text, answers, answers, counter);
				questions.add(q);
			}
			else if(question_type.equals("Picture Multiple-Choice")) {
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("test");
				String text = "Question";
				MultiChoicePicQuestion q = new MultiChoicePicQuestion(text, "url", answers, answers, counter);
				questions.add(q);
			}
			else if(question_type.equals("Fill in the blank")) {
				ArrayList<String> answers = new ArrayList<String>();
				answers.add("test");
				String text = "Question";
				FillBlankQuestion q = new FillBlankQuestion(text, answers, counter);
				questions.add(q);
			}
			else {
				System.out.println("Question type not found");
				return;
			}
			counter++;
		}
		String title = "First Ever Quiz";
		String description = "this is cool if it works...";
		boolean isRandom = false;
		boolean isOnePage = true;
		boolean hasImmediateFeedback = false;
		boolean practiceMode = false;
		String creator = "Travis";
		System.out.println(questions.size());
		Quiz quiz = new Quiz(questions, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
		user.addQuiz(quiz);
	}

}
