package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

 public class Question {
	String questionText;
	ArrayList<String> correctAnswers;
	int position;
	HashMap<String, Object> infoMap;
	
	public Question(String questionText, ArrayList<String> correctAnswers, int position) {
		this.questionText = questionText;
		this.correctAnswers = correctAnswers; 
		this.position = position;
		this.infoMap = new HashMap<String, Object>();
		this.infoMap.put("questionText", questionText);
		this.infoMap.put("correctAnswers", correctAnswers);
		this.infoMap.put("position", position);
	}
	
	public String getQuestion() {
		return this.questionText;
	}
	
	public ArrayList<String> getCorrectAnswers(){
		return this.correctAnswers;
	}
	
	public int getPostion() {
		return this.position;
	}
	
	public boolean checkAnswer(String prospAnswer) {
		if (correctAnswers.contains(prospAnswer)) return true;
		return false;
	}
	
	public JSONObject getJSON(){
		HashMap<String, Object> qObj = new HashMap<String, Object>(this.infoMap);
		for (Map.Entry<String, Object> entry : qObj.entrySet()){
			if (entry.getValue() instanceof ArrayList){
				JSONArray arr = new JSONArray((ArrayList)entry.getValue());
				entry.setValue(arr);
			}
		}
		qObj.put("class", this.getClass().toString());
		JSONObject jsonObj = new JSONObject(qObj);
//		System.out.println(jsonObj.toString());
		return jsonObj;
	}
}

