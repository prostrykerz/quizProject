package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
import models.QuizHistory;
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
		String json = "{\"data\":" + request.getParameter("data") + "}";
		System.out.println(json);
		JSONObject outer = new JSONObject(json);
		JSONObject inner = outer.getJSONObject("data");
		
		JSONArray correctAnswers = inner.getJSONArray("correctAnswers");
		JSONArray attemptedAnswers = inner.getJSONArray("attemptedAnswers");
		JSONArray type = inner.getJSONArray("type");
		
		ArrayList<Integer> scoreArr = new ArrayList<Integer>();
		int totalScore = 0;
		
		for (int i=0; i<correctAnswers.length(); i++){
			JSONArray answerKeyArr = correctAnswers.getJSONArray(i);
			JSONArray attemptedKeyArr = attemptedAnswers.getJSONArray(i);
			
			if(type.getString(i).equals("1")||type.getString(i).equals("2")||type.getString(i).equals("7")) {
				boolean correct = false;
				for (int j=0; j<answerKeyArr.length(); j++){
					if (answerKeyArr.getString(j).equals(attemptedKeyArr.getString(0))){
						correct = true;
						break;
					}
				}
				if (correct) scoreArr.add(1);
				else scoreArr.add(0);
				totalScore++;
			}
			else if(type.getString(i).equals("3") || type.getString(i).equals("4")){
				
				boolean correct = true;
				int score = 0;
				for (int j=0; j<answerKeyArr.length(); j++){
					if (answerKeyArr.getBoolean(j)!=attemptedKeyArr.getBoolean(j)){
						correct = false;
						break;
					}	
				}
				if (correct) score=1;
				scoreArr.add(score);
				totalScore++;
			}
			else if(type.getString(i).equals("5") || type.getString(i).equals("6")){
				
				int score = 0;
				for (int j=0; j<answerKeyArr.length(); j++){
					if (answerKeyArr.getBoolean(j)) totalScore++;
					if (answerKeyArr.getBoolean(j)!=attemptedKeyArr.getBoolean(j)) continue;
					if (answerKeyArr.getBoolean(j)) score++;
				}
				scoreArr.add(score);
			}

			System.out.println(scoreArr);
		}
		
		
		String title = inner.getString("title");
		int time = inner.getInt("time");
		int score = 0;
		for (int i=0; i<scoreArr.size(); i++){
			score+=scoreArr.get(i);
		}
		//Change 1 to the quiz object
		QuizHistory.save(user, 1, score, time);
//		String description = inner.getString("description");
//		boolean isRandom = false;
//		boolean isOnePage = true;
//		boolean hasImmediateFeedback = false;
//		boolean practiceMode = false;
//		//make user
//		String creator = user.getUsername();
//		Quiz quiz = new Quiz(questions, title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, creator);
//		user.addQuiz(quiz);
//		response.getWriter().write("{\"msg\": \"Success\"}");
		
		JSONObject result = new JSONObject();
		
		result.put("title", title);
		result.put("score", score);
		result.put("totalScore", totalScore);
		result.put("time", time);
		
		response.getWriter().write(result.toString());
//		RequestDispatcher rd = request.getRequestDispatcher("scoreSummary.jsp");
//		rd.forward(request, response);
	}

}
