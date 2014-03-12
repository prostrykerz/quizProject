package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import models.FillBlankQuestion;
import models.MultiChoicePicQuestion;
import models.MultiChoiceTextQuestion;
import models.Question;
import models.SingleResponsePicQuestion;
import models.SingleResponseTextQuestion;

import databases.MyDBInfo;

public class QuizModelTable extends Database {

	private static int NUM_COLS = 8;
	private int quizID;
	
	public QuizModelTable(int quizID){
		this.quizID = quizID;
		table = new ArrayList[NUM_COLS];
		for(int i=0; i<NUM_COLS; i++){
			if (i==5) table[i] = new ArrayList<Boolean>();
			else if (i==0 || i==3 || i==6) table[i] = new ArrayList<Integer>();
			else table[i] = new ArrayList<String>();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = createGetQuizQuery();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Integer q_id = rs.getInt("q_id");
				String q_text = rs.getString("q_text");
				String q_url = rs.getString("q_url");
				Integer a_id = rs.getInt("a_id");
				String a_text = rs.getString("a_text");
				Boolean a_correct = rs.getBoolean("a_correct");
				Integer position = rs.getInt("position");
				String q_type = rs.getString("q_type");
				table[0].add(q_id);
				table[1].add(q_text);
				table[2].add(q_url);
				table[3].add(a_id);
				table[4].add(a_text);
				table[5].add(a_correct);
				table[6].add(position);
				table[7].add(q_type);
			}
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String createGetQuizQuery(){
		String query = "SELECT * FROM(";
		query+= "SELECT t2.q_id, t2.q_text, NULL AS q_url, ";
		query+= "t2.a_id, t2.a_text, NULL AS a_correct, t2.position, \"singleresponsetextquestion\" AS q_type ";
		query+= "FROM quizzes AS t1, singleresponsetextquestion AS t2 ";
		query+= "WHERE t1.p_id="+this.quizID;
		query+= " AND t1.p_id=t2.quiz_id ";
		query+= "UNION SELECT t2.q_id, t2.q_text, t2.q_url, ";
		query+= "t2.a_id, t2.a_text, NULL as a_correct, t2.position, \"singleresponsepicquestion\" AS q_type ";
		query+= "FROM quizzes AS t1, singleresponsepicquestion AS t2 ";
		query+= "WHERE t1.p_id="+this.quizID;
		query+= " AND t1.p_id=t2.quiz_id ";
		query+= "UNION SELECT t2.q_id, t2.q_text, NULL AS q_url, ";
		query+= "t2.a_id, t2.a_text, t2.a_correct, t2.position, \"multichoicetextquestion\" AS q_type ";
		query+= "FROM quizzes AS t1, multichoicetextquestion AS t2 ";
		query+= "WHERE t1.p_id="+this.quizID;
		query+= " AND t1.p_id=t2.quiz_id ";
		query+= "UNION SELECT t2.q_id, t2.q_text, t2.q_url, ";
		query+= "t2.a_id, t2.a_text, t2.a_correct, t2.position, \"multichoicepicquestion\" AS q_type ";
		query+= "FROM quizzes AS t1, multichoicepicquestion AS t2 ";
		query+= "WHERE t1.p_id="+this.quizID;
		query+= " AND t1.p_id=t2.quiz_id ";
		query+= "UNION SELECT t2.q_id, t2.q_text, NULL AS q_url, ";
		query+= "t2.a_id, t2.a_text, NULL AS a_correct, t2.position, \"fillblankquestion\" AS q_type ";
		query+= "FROM quizzes AS t1, fillblankquestion AS t2 ";
		query+= "WHERE t1.p_id="+this.quizID;
		query+= " AND t1.p_id=t2.quiz_id";
		query+= ")t3 ORDER BY t3.q_id;";
		return query;
	}
	
	public ArrayList<Question> getQuestions(){
		ArrayList<Question> qArr = new ArrayList<Question>();
		for (int i=0; i<table[0].size(); i++){
//			if (prevQID == (Integer)table[0].get(i)) continue;
			String q_type = (String)table[7].get(i);
			if (q_type.equals("singleresponsetextquestion")){
				String questionText = (String)table[1].get(i);
				ArrayList<String> correctAnswers = new ArrayList<String>();
				int q_id = (Integer)table[0].get(i);
				while (i<table[0].size()){
					int compare_id = (Integer)table[0].get(i);
					if (q_id!=compare_id){
						break;
					}
					correctAnswers.add((String)table[4].get(i));
					i++;
				}
				i--;
				int position = (Integer)table[6].get(i);
				qArr.add(new SingleResponseTextQuestion(questionText, correctAnswers, position));
			}
			else if (q_type.equals("singleresponsepicquestion")){
				String questionText = (String)table[1].get(i);
				String q_url = (String)table[2].get(i);
				ArrayList<String> correctAnswers = new ArrayList<String>();
				int q_id = (Integer)table[0].get(i);
				while (i<table[0].size()){
					int compare_id = (Integer)table[0].get(i);
					if (q_id!=compare_id){
						break;
					}
					correctAnswers.add((String)table[4].get(i));
					i++;
				}
				i--;
				int position = (Integer)table[6].get(i);
				qArr.add(new SingleResponsePicQuestion(questionText, q_url, correctAnswers, position));
			}
			else if (q_type.equals("fillblankquestion")){
				String questionText = (String)table[1].get(i);
				ArrayList<String> correctAnswers = new ArrayList<String>();
				int q_id = (Integer)table[0].get(i);
				while (i<table[0].size()){
					int compare_id = (Integer)table[0].get(i);
					if (q_id!=compare_id){
						break;
					}
					correctAnswers.add((String)table[4].get(i));
					i++;
				}
				i--;
				int position = (Integer)table[6].get(i);
				qArr.add(new FillBlankQuestion(questionText, correctAnswers, position));
			}
			else if (q_type.equals("multichoicetextquestion")){
				String questionText = (String)table[1].get(i);
				ArrayList<String> answerChoices = new ArrayList<String>();
				ArrayList<String> correctAnswers = new ArrayList<String>();
				int q_id = (Integer)table[0].get(i);
				while (i<table[0].size()){
					int compare_id = (Integer)table[0].get(i);
					if (q_id!=compare_id){
						break;
					}
					answerChoices.add((String)table[4].get(i));
					if ((Boolean)table[5].get(i)) correctAnswers.add((String)table[4].get(i));
					i++;
				}
				i--;
				int position = (Integer)table[6].get(i);
				qArr.add(new MultiChoiceTextQuestion(questionText, correctAnswers, answerChoices, position));
			}
			else if (q_type.equals("multichoicepicquestion")){
				String questionText = (String)table[1].get(i);
				String q_url = (String)table[2].get(i);
				ArrayList<String> answerChoices = new ArrayList<String>();
				ArrayList<String> correctAnswers = new ArrayList<String>();
				int q_id = (Integer)table[0].get(i);
				while (i<table[0].size()){
					int compare_id = (Integer)table[0].get(i);
					if (q_id!=compare_id) break;
					answerChoices.add((String)table[4].get(i));
					if ((Boolean)table[5].get(i)) correctAnswers.add((String)table[4].get(i));
					i++;
				}
				i--;
				int position = (Integer)table[6].get(i);
				qArr.add(new MultiChoicePicQuestion(questionText, q_url, correctAnswers, answerChoices, position));
			}
			
		}
		
		return qArr;
	}
	
	public HashMap<String, Object> getQuizInfo(){
		HashMap<String, Object> info = new HashMap<String, Object>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String query = createGetQuizInfoQuery();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Integer p_id = rs.getInt("p_id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Boolean random = rs.getBoolean("random");
				Boolean onePage = rs.getBoolean("onePage");
				Boolean immediateFeedback = rs.getBoolean("immediateFeedback");
				Boolean practiceMode = rs.getBoolean("practiceMode");
				Integer score = rs.getInt("score");
				Integer time = rs.getInt("time");
				String creator = rs.getString("creator");
				
				info.put("quiz_id", p_id);
				info.put("title", name);
				info.put("description", description);
				info.put("random", random);
				info.put("onePage", onePage);
				info.put("immediateFeedback", immediateFeedback);
				info.put("practiceMode", practiceMode);
				info.put("score", score);
				info.put("time", time);
				info.put("creator", creator);
			}
			con.close();
			try {
	            AbandonedConnectionCleanupThread.shutdown();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return info;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String createGetQuizInfoQuery(){
		String query = "SELECT * FROM quizzes ";
		query+= "WHERE p_id="+this.quizID+";";
		return query;
	}
}
