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

import org.json.JSONArray;
import org.json.JSONObject;

import databases.QuizHistoryTable;
import databases.QuizTable;

import users.AccountManager;
import users.User;

/**
 * Servlet implementation class UpdateQuizServlet
 */
@WebServlet("/UpdateQuizServlet")
public class UpdateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuizServlet() {
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
		String json = "{\"data\":" + request.getParameter("data") + "}";
		System.out.println(json);
		JSONObject outer = new JSONObject(json);
		JSONObject inner = outer.getJSONObject("data");
		
		ArrayList<Question> questions = new ArrayList<Question>();
		JSONArray jsonQuestions =inner.getJSONArray("questions");
		for(int i = 0; i < jsonQuestions.length(); i++) {
			if(!validate(jsonQuestions.getJSONObject(i))) {
				response.getWriter().write("{\"error\": \"Fill In The Blank Missing **\"}");
			}
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
			else if(question.getString("type").equals("3") || question.getString("type").equals("5")) {
				ArrayList<String> possible_answers = new ArrayList<String>();
				JSONArray jPossibleAnswers = question.getJSONArray("possible_answers");
				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
				MultiChoiceTextQuestion q = new MultiChoiceTextQuestion(text, answers, possible_answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("4") || question.getString("type").equals("6")) {
				String pictureURL = question.getString("pictureURL");
				String pictureTitle = question.getString("pictureTitle");
				ArrayList<String> possible_answers = new ArrayList<String>();
				JSONArray jPossibleAnswers = question.getJSONArray("possible_answers");
				for(int j = 0; j < jPossibleAnswers.length(); j++) possible_answers.add(jPossibleAnswers.getString(j));
				MultiChoicePicQuestion q = new MultiChoicePicQuestion(text, pictureURL, answers, possible_answers, i);
				questions.add(q);
			}
			else if(question.getString("type").equals("7")) {
				FillBlankQuestion q = new FillBlankQuestion(text, answers, i);
				questions.add(q);
			}
		}
		String title = inner.getString("title");
		String description = inner.getString("description");
		String dispType = inner.getString("onePage");
		String order = inner.getString("order");
		String feedBack = inner.getString("feedback");
		String practice = inner.getString("practice");
		boolean isOnePage = false;
		if (dispType.equals("single")) isOnePage = true; 
		boolean isRandom = false;
		if (order.equals("random")) isRandom = true; 
		boolean hasImmediateFeedback = false;
		if (feedBack.equals("yes")) hasImmediateFeedback = true; 
		boolean practiceMode = false;
		if (practice.equals("yes")) practiceMode = true; 
		//make user
		String creator = user.getUsername();
		Quiz quiz = new Quiz(questions, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
		user.addQuiz(quiz);
		awardQuizCreationAchievements(user);
	
		int oldQid = inner.getInt("q_id");
		int oldTime = inner.getInt("oldTime");
		int oldScore = inner.getInt("oldScore");
		int oldTimesTaken = inner.getInt("oldTimesTaken");
		int newQid = quiz.getId();
		QuizTable.setTimeScoreTimesTaken(newQid, oldTime, oldScore, oldQid);
		user.deleteQuiz(oldQid);
		QuizHistoryTable.updateHistory(oldQid,newQid);
		
		
		
		
		
		//TO-DO take quiz id and then replace the quiz id in the quiz table
		//Then delete all old questions. (new questions are already in there)
		//Also update the quizid using the method in the history table.
		
		response.getWriter().write("{\"msg\": \"Success\", \"id\": \"" + quiz.getId() + "\"}");
	}

	private void awardQuizCreationAchievements(User user) {
		if(user.getQuizzes().size() == 1) user.awardAchievement(0);
		else if(user.getQuizzes().size() == 5) user.awardAchievement(1);
		else if(user.getQuizzes().size() == 10) user.awardAchievement(2);
	}
	
	private boolean validate(JSONObject question) {
		if(question.getString("type").equals("7")) {
			String text = question.getString("text");
			if(!text.contains("**")) return false;
		}
		return true;
	}

}
