package models;

import databases.QuizHistoryTable;
import users.User;

public class QuizHistory {

	public static void save(User u, int qid, int score, int time) {
		QuizHistoryTable.save(u.getId(), qid, score, time);
	}
}
