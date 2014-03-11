package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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

import org.json.JSONArray;
import org.json.JSONObject;

import users.AccountManager;
import users.User;

/**
 * Servlet implementation class ScoreQuizServlet
 */
@WebServlet("/ScoreQuizServlet")
public class ScoreQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreQuizServlet() {
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
		//Create quiz
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		AccountManager manager = (AccountManager) context.getAttribute("manager");
		User user = (User) session.getAttribute("user");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(user == null) {
			response.getWriter().write("{\"error\": \"Not Logged In\"}");
			return;
		}
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    System.out.println(parameter);
		}
		String json = "{\"data\":" + request.getParameter("data") + "}";
		System.out.println(json);
//		JSONObject outer = new JSONObject(json);
//		JSONObject inner = outer.getJSONObject("data");
//		
//		ArrayList<Question> questions = new ArrayList<Question>();
//		JSONArray jsonQuestions =inner.getJSONArray("questions");
//		for(int i = 0; i < jsonQuestions.length(); i++) {
//			JSONObject question = jsonQuestions.getJSONObject(i);
//			ArrayList<String> answers = new ArrayList<String>();
//			JSONArray jAnswers = question.getJSONArray("correct_answers");
//			for(int j = 0; j < jAnswers.length(); j++) answers.add(jAnswers.getString(j));
//			String text = question.getString("text");
//			if(question.getString("type").equals("1")) {
//				SingleResponseTextQuestion q = new SingleResponseTextQuestion(text, answers, i);
//				questions.add(q);
//			}
//			else if(question.getString("type").equals("2")) {
//				String pictureURL = question.getString("pictureURL");
//				String pictureTitle = question.getString("pictureTitle");
//				SingleResponsePicQuestion q = new SingleResponsePicQuestion(text, pictureURL, answers, i);
//				questions.add(q);
//			}
//			else if(question.getString("type").equals("3") || question.getString("type").equals("5")) {
//				ArrayList<String> possible_answers = new ArrayList<String>();
//				JSONArray jPossibleAnswers = question.getJSONArray("possible_answers");
//				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
//				MultiChoiceTextQuestion q = new MultiChoiceTextQuestion(text, answers, possible_answers, i);
//				questions.add(q);
//			}
//			else if(question.getString("type").equals("4") || question.getString("type").equals("6")) {
//				String pictureURL = question.getString("pictureURL");
//				String pictureTitle = question.getString("pictureTitle");
//				ArrayList<String> possible_answers = new ArrayList<String>();
//				JSONArray jPossibleAnswers = question.getJSONArray("possible_answers");
//				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
//				MultiChoicePicQuestion q = new MultiChoicePicQuestion(text, pictureURL, answers, possible_answers, i);
//				questions.add(q);
//			}
//			else if(question.getString("type").equals("7")) {
//				FillBlankQuestion q = new FillBlankQuestion(text, answers, i);
//				questions.add(q);
//			}
//		}
//		String title = inner.getString("title");
//		String description = inner.getString("description");
//		boolean isRandom = false;
//		boolean isOnePage = true;
//		boolean hasImmediateFeedback = false;
//		boolean practiceMode = false;
//		//make user
//		String creator = user.getUsername();
//		Quiz quiz = new Quiz(questions, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
//		user.addQuiz(quiz);
		response.getWriter().write("{\"msg\": \"Success\"}");
	}

}
