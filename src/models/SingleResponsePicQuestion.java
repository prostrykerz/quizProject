package models;

import java.util.ArrayList;

// This class represents a picture question with a single response from a text field. 

public class SingleResponsePicQuestion extends Question {
	private String pictureURL = null;
	private String pictureTitle = null;
	
	public SingleResponsePicQuestion(String questionText, String pictureURL, ArrayList<String> correctAnswers, int position) {
		super(questionText, correctAnswers, position);
		this.pictureURL = pictureURL;
		this.infoMap.put("pictureURL", pictureURL);
	}
	
	public String getPicture() {
		return pictureURL;
	}
	
	public String getPictureTitle() {
		return pictureTitle;
	}
}
