package models;

import java.util.ArrayList;

public class FillBlankQuestion extends Question {
	public FillBlankQuestion(String questionText, ArrayList<String> correctAnswers, int position) {
		super(questionText, correctAnswers, position);
	}
	
	public String[] getQuestionParts() {
		return questionText.split("X");
	}
	
}
