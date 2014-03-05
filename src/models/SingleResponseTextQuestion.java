package models;

import java.util.ArrayList;

public class SingleResponseTextQuestion extends Question {
	public SingleResponseTextQuestion(String questionText, ArrayList<String> correctAnswers, int position) {
		super(questionText, correctAnswers, position);
	}
}
