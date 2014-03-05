package models;

import java.util.ArrayList;

 public class Question {
	String questionText;
	ArrayList<String> correctAnswers;
	int position;
	
	public Question(String questionText, ArrayList<String> correctAnswers, int position) {
		this.questionText = questionText;
		this.correctAnswers = correctAnswers; 
		this.position = position;
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
}

