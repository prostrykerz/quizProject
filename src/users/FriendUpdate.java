package users;

import java.util.Date;

import databases.UserTable;

import models.Achievement;
import models.Quiz;
import models.QuizHistory;

public class FriendUpdate {
	Object event;
	int type, uid;
	Date time;
	public FriendUpdate(Object event, int type) {
		this.event = event;
		this.type = type;
		if(type == 1) {
			QuizHistory qh = (QuizHistory) event;
			this.uid = qh.getUserId();
			time = qh.getCreatedAt();
		}
		else if(type == 2) {
			Quiz q = (Quiz) event;
			this.uid = q.getOwner().getId();
			time = q.getCreatedAt();
		}
		else if(type == 3) {
			Achievement a = (Achievement) event;
			this.uid = a.getUserId();
			time = a.getWhen();
		}
	}
	
	public String getType() {
		if(type == 1) return "Quiz Attempt";
		if(type == 2) return "Quiz Created";
		if(type == 3) return "Achievement Received";
		return "";
	}
	
	public String getText() {
		StringBuilder sb = new StringBuilder();
		User u = UserTable.getUser(uid);
		if(type == 1) {
			QuizHistory qh = (QuizHistory) event;
			Quiz q = new Quiz(qh.getQuizId());
			sb.append("<a href='user.jsp?username=" + u.getUsername() + "'> " + u.getUsername() + "</a> tried ");
			sb.append("<a href=quizSummary?id='" + qh.getQuizId()+ "'>"  +  q.getTitle() + "</a>");
		}
		if(type == 2) {
			Quiz q = (Quiz) event;
			sb.append("<a href='user.jsp?username=" + u.getUsername() + "'> " + u.getUsername() + "</a> created ");
			sb.append("<a href=quizSummary?id='" + q.getId()+ "'>"  +  q.getTitle() + "</a>");
		}
		if(type == 3) {
			Achievement a = (Achievement) event;
			sb.append("<a href='user.jsp?username=" + u.getUsername() + "'> " + u.getUsername() + "</a> received the ");
			sb.append(a.getText() + " achievement");
		}
		return sb.toString();
	}
	
	public long getTime() {
		return time.getTime();
	}
	
	public int getUserId() {
		return uid;
	}
}
