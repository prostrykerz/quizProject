package models;

import java.util.ArrayList;

public class MultiChoiceTextQuestion extends Question {
	private ArrayList<String> answerChoices;
	
	public MultiChoiceTextQuestion(String questionText, ArrayList<String> correctAnswers, ArrayList<String> answerChoices, int position){
		super(questionText, correctAnswers, position);
		this.answerChoices = answerChoices;
	}
	
	public ArrayList<String> getChoices() {
		return answerChoices;
	}
}
