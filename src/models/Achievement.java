package models;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import databases.AchievementTable;

public class Achievement {
	private static final Map<Integer, String> achievements;
	static {
		Map<Integer, String> temp = new HashMap<Integer, String>();
		temp.put(0, "Amateur Author");
		temp.put(1, "Prolific Author");
		temp.put(2, "Prodigious Author");
		temp.put(3, "Quiz Machine");
		temp.put(4, "I am the Greatest");
		temp.put(5, "Practice Makes Perfect");
		
		// Achievements appearing on an increasing amount of leader boards. 
		
		temp.put(6, "Bachelor"); 	
		temp.put(7, "Master");
		temp.put(8, "Doctor");
		
		// Achievement for having attained highest number of leader board appearances.  
		temp.put(9, "Salutatorian");
		
		// Achievement for having attained second highest number of leader board appearances.  
		temp.put(10, "Valedictorian");
		
		// Achievement for having attained top spot on at least 5 quizzes.   
		temp.put(11, "Rhodes Scholar");
		
		// Achievement for having attained top spot on at least 10 quizzes. 
		temp.put(12, "Professor");
		
		achievements = Collections.unmodifiableMap(temp);
	}
	
	boolean awarded;
	Date when;
	int uid, code;
	public Achievement(int uid, int code, boolean awarded, Date when) {
		this.uid = uid;
		this.code = code;
		this.awarded = awarded;
		this.when = when;
	}
	
	public int getUserId() {
		return uid;
	}
	
	public void awardAchievement() {
		awarded = true;
	}
	public boolean getStatus() {
		return awarded;
	}
	public Date getWhen() {
		return when;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getText() {return achievements.get(this.code);}
	
	public static String getIndex(int code) {
		if(code == 0) return "one";
		if(code == 1) return "two";
		if(code == 2) return "three";
		if(code == 3) return "four";
		if(code == 4) return "five";
		if(code == 5) return "six";
		return "err";
	}
	
	public static long getTime(int uid, int code) {
		String index = getIndex(code);
		Achievement a = AchievementTable.getAchievement(uid, index);
		return a.getWhen().getTime();
	}
}
