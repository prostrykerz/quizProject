package models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	
	public static String getText(int code) {return achievements.get(code);}
	public static String getIndex(int code) {
		if(code == 0) return "Amateur Author";
		else if(code == 1) return "Prolific Author";
		else if(code == 2) return "Prodigious Author";
		else if(code == 3) return "Quiz Machine";
		else if(code == 4) return "I am the Greatest";
		else if(code == 5) return "Practice Makes Perfect";
		return "err";
	}
}
