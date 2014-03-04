package models;

import java.util.ArrayList;

public class MultiChoicePicQuestion extends SingleResponsePicQuestion {
	ArrayList<String> answerChoices;
	
	public MultiChoicePicQuestion(String questionText, String pictureURL, ArrayList<String> correctAnswers, ArrayList<String> answerChoices, int position) {
		super(questionText, pictureURL, correctAnswers, position);
		this.answerChoices = answerChoices;
	}
	
	public ArrayList<String> getChoices() {
		return answerChoices;
	}
}
