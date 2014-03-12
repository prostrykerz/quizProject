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
		achievements = Collections.unmodifiableMap(temp);
	}
	
	public static String getText(int code) {return achievements.get(code);}
	public static String getIndex(int code) {
		if(code == 0) return "one";
		else if(code == 1) return "two";
		else if(code == 2) return "three";
		else if(code == 3) return "four";
		else if(code == 4) return "five";
		else if(code == 5) return "six";
		return "err";
	}
}
