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
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		if(user == null) {
			response.getWriter().write("{error: \"Not Logged In\"}");
			return;
		}
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    System.out.println(parameter);
		}
		String json = "{\"questions\":" + request.getParameter("questions") + "}";
		System.out.println(json);
		JSONObject o = new JSONObject(json);
		
		ArrayList<Question> questions = new ArrayList<Question>();
		JSONArray jsonQuestions = o.getJSONArray("questions");
		for(int i = 0; i < jsonQuestions.length(); i++) {
			JSONObject question = jsonQuestions.getJSONObject(i);
			ArrayList<String> answers = new ArrayList<String>();
			JSONArray jAnswers = question.getJSONArray("correct_answers");
			for(int j = 0; j < jAnswers.length(); j++) answers.add(jAnswers.getString(j));
			String text = question.getString("text");
			if(question.getString("type").equals("1")) {
				SingleResponseTextQuestion q = new SingleResponseTextQuestion(text, answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("2")) {
				String pictureURL = question.getString("pictureURL");
				String pictureTitle = question.getString("pictureTitle");
				SingleResponsePicQuestion q = new SingleResponsePicQuestion(text, pictureURL, answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("3")) {
				ArrayList<String> possible_answers = new ArrayList<String>();
				JSONArray jPossibleAnswers = question.getJSONArray("posible_answers");
				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
				MultiChoiceTextQuestion q = new MultiChoiceTextQuestion(text, answers, possible_answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("4")) {
				String pictureURL = question.getString("pictureURL");
				String pictureTitle = question.getString("pictureTitle");
				ArrayList<String> possible_answers = new ArrayList<String>();
				JSONArray jPossibleAnswers = question.getJSONArray("posible_answers");
				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
				MultiChoicePicQuestion q = new MultiChoicePicQuestion(text, pictureURL, answers, possible_answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("5")) {
				FillBlankQuestion q = new FillBlankQuestion(text, answers, i);
				questions.add(q);
			}
		}
//		int counter = 1;
//		while(true) {
//			String question_type = request.getParameter("mydropdown" + counter);
//			
//			if(question_type == null) break;
//			if(question_type.equals("Text Question-Response")) {
//				ArrayList<String> answers = new ArrayList<String>();
//				answers.add("test");
//				String text = "Question";
//				SingleResponseTextQuestion q = new SingleResponseTextQuestion(text, answers, counter);
//				questions.add(q);
//			}
//			else if(question_type.equals("Picture Question-Response")) {
//				ArrayList<String> answers = new ArrayList<String>();
//				answers.add("test");
//				String text = "Question";
//				SingleResponsePicQuestion q = new SingleResponsePicQuestion(text, "url", answers, counter);
//				questions.add(q);
//			}
//			else if(question_type.equals("Text Multiple-Choice")) {
//				ArrayList<String> answers = new ArrayList<String>();
//				answers.add("test");
//				String text = "Question";
//				MultiChoiceTextQuestion q = new MultiChoiceTextQuestion(text, answers, answers, counter);
//				questions.add(q);
//			}
//			else if(question_type.equals("Picture Multiple-Choice")) {
//				ArrayList<String> answers = new ArrayList<String>();
//				answers.add("test");
//				String text = "Question";
//				MultiChoicePicQuestion q = new MultiChoicePicQuestion(text, "url", answers, answers, counter);
//				questions.add(q);
//			}
//			else if(question_type.equals("Fill in the blank")) {
//				ArrayList<String> answers = new ArrayList<String>();
//				answers.add("test");
//				String text = "Question";
//				FillBlankQuestion q = new FillBlankQuestion(text, answers, counter);
//				questions.add(q);
//			}
//			else {
//				System.out.println("Question type not found");
//				return;
//			}
//			counter++;
//		}
		String title = "HARD CODED TITLE. ADD LATER";
		String description = "HARD CODED DESCRIPTION";
		boolean isRandom = false;
		boolean isOnePage = true;
		boolean hasImmediateFeedback = false;
		boolean practiceMode = false;
		//make user
		String creator = "andrew";
		System.out.println(questions.size());
		Quiz quiz = new Quiz(questions, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
		user.addQuiz(quiz);
		response.getWriter().write("{msg: \"Success\"}");
	}

}
