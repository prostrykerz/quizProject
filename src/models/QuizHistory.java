package models;

import java.util.ArrayList;

import databases.QuizHistoryTable;
import users.User;

public class QuizHistory {
	int uid, qid, score, time;
	
	public QuizHistory(int uid, int qid, int score, int time) {
		this.uid = uid;
		this.qid = qid;
		this.score = score;
		this.time = time;
	}
	
	public static void save(User u, int qid, int score, int time) {
		QuizHistoryTable.save(u.getId(), qid, score, time);
	}
	
	
	public int getUserId() {return uid;}
	public int getScore() {return score;}
	public int getTime() {return time;}
	public int getQuizId() {return qid;}
	public Quiz getQuiz() {return new Quiz(qid);}
}
