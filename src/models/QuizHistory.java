package models;

import java.util.ArrayList;
import java.util.Date;

import databases.QuizHistoryTable;
import users.User;

public class QuizHistory {
	int uid, qid, score, time;
	Date at;
	public QuizHistory(int uid, int qid, int score, int time, Date at) {
		this.uid = uid;
		this.qid = qid;
		this.score = score;
		this.time = time;
		this.at = at;
	}
	
	public static void save(User u, int qid, int score, int time) {
		QuizHistoryTable.save(u.getId(), qid, score, time);
	}
	
	
	public int getUserId() {return uid;}
	public int getScore() {return score;}
	public int getTime() {return time;}
	public int getQuizId() {return qid;}
	public Quiz getQuiz() {return new Quiz(qid);}
	public Date getCreatedAt() {return at;}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"score\": " + score + ",");
		sb.append("\"time\": " + time + ",");
		sb.append("\"at\": \"" + at + "\",");
		Date now = new Date();
		long time_ago = (now.getTime() - at.getTime()) / 1000;
		sb.append("\"time_ago\": " + time_ago);
		sb.append("}");
		return sb.toString();
	}
}
