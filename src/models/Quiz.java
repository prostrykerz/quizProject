package models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import databases.FillBlankTable;
import databases.MultiChoicePicTable;
import databases.MultiChoiceTextTable;
import databases.QuizHistoryTable;
import databases.QuizModelTable;
import databases.QuizTable;
import databases.SingleResponsePicTable;
import databases.SingleResponseTextTable;


public class Quiz {
	
	private ArrayList<Question> questionArr;

	private Integer quizID;
	private HashMap<String, Object> infoMap;

	private QuizModelTable quizMDB;
	
	public Quiz(ArrayList<Question> questionArr, String title, 
			String description, boolean isRandom, 
			boolean isOnePage, boolean hasImmediateFeedback, boolean practiceMode, String creator) {
		this.questionArr = questionArr;
		
		this.infoMap = new HashMap<String, Object>();
		
		this.infoMap.put("title", title);
		this.infoMap.put("description", description);
		this.infoMap.put("random", isRandom);
		this.infoMap.put("onePage", isOnePage);
		this.infoMap.put("immediateFeedback", hasImmediateFeedback);
		this.infoMap.put("practiceMode", practiceMode);
		this.infoMap.put("score", 0);
		this.infoMap.put("time", 0);
		this.infoMap.put("creator", creator);
		this.infoMap.put("timesTaken", 0);
		
		this.quizID = QuizTable.addToDatabase(title, description, isRandom, isOnePage, hasImmediateFeedback, practiceMode, 0, 0, creator);
		this.infoMap.put("quiz_id", this.quizID);
		this.quizMDB = new QuizModelTable(this.quizID);
		this.storeQuestions();
	}
	
	public Quiz(int quizID){
		this.quizID = quizID;
		this.quizMDB = new QuizModelTable(this.quizID);
		this.questionArr = this.quizMDB.getQuestions();

		this.infoMap = new HashMap<String,Object>(this.quizMDB.getQuizInfo());

		if (this.infoMap==null) System.out.println("ono");
	}
	
	public HashMap<String, Object> getInfoMap(){
		return this.infoMap;
	}
	
	public ArrayList<Question> getQuestionArr(){
		return this.quizMDB.getQuestions();
	}
	
	private void storeQuestions(){
		for (int i=0; i<this.questionArr.size(); i++){
			Question q = this.questionArr.get(i);
			if(this.questionArr.get(i) instanceof FillBlankQuestion){
				FillBlankQuestion quest = (FillBlankQuestion) q;
				ArrayList<String> answers = quest.getCorrectAnswers();
				for (int j=0; j<answers.size(); j++){
					FillBlankTable.addToDatabase(i+1, quest.getQuestion(), j+1, answers.get(j), i+1, this.quizID);
				}
			}
			else if(this.questionArr.get(i) instanceof MultiChoicePicQuestion){
				MultiChoicePicQuestion quest = (MultiChoicePicQuestion) q;
				ArrayList<String> correctAnswers = quest.getCorrectAnswers();
				ArrayList<String> possibleAnswers = quest.getChoices();
				for (int j=0; j<possibleAnswers.size(); j++){
					Boolean correct = false;
					for (int k=0; k<correctAnswers.size(); k++){
						if (possibleAnswers.get(j).equals(correctAnswers.get(k))) correct = true;
					}
					MultiChoicePicTable.addToDatabase(i+1, quest.getQuestion(), quest.getPicture(), j+1, 
							possibleAnswers.get(j), correct, i+1, this.quizID);
				}
			}
			else if(this.questionArr.get(i) instanceof MultiChoiceTextQuestion){
				
				MultiChoiceTextQuestion quest = (MultiChoiceTextQuestion) q;
				ArrayList<String> correctAnswers = quest.getCorrectAnswers();
				ArrayList<String> possibleAnswers = quest.getChoices();
				for (int j=0; j<possibleAnswers.size(); j++){
					Boolean correct = false;
					for (int k=0; k<correctAnswers.size(); k++){
						if (possibleAnswers.get(j).equals(correctAnswers.get(k))) correct = true;
					}
					MultiChoiceTextTable.addToDatabase(i+1, quest.getQuestion(), j+1, possibleAnswers.get(j), 
							correct, i+1, this.quizID);
				}
			}
			else if(this.questionArr.get(i) instanceof SingleResponseTextQuestion){
				SingleResponseTextQuestion quest = (SingleResponseTextQuestion) q;
				ArrayList<String> answers = quest.getCorrectAnswers();
				for (int j=0; j<answers.size(); j++){
					SingleResponseTextTable.addToDatabase(i+1, quest.getQuestion(), j+1, answers.get(j), i+1, this.quizID);
				}
			}
			else if(this.questionArr.get(i) instanceof SingleResponsePicQuestion){
				SingleResponsePicQuestion quest = (SingleResponsePicQuestion) q;
				ArrayList<String> answers = quest.getCorrectAnswers();
				for (int j=0; j<answers.size(); j++){
					SingleResponsePicTable.addToDatabase(i+1, quest.getQuestion(), quest.getPicture(), j+1, answers.get(j), i+1, this.quizID);
				}
			}
		}
	}
	
	static public void loadXML(String path){
		try {
			File fXmlFile = new File("Resources/cities.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			Element quiz = doc.getDocumentElement();
			System.out.println("Root element :" + quiz.getNodeName());
			System.out.println("IsRandom  :" + quiz.getAttribute("random"));
			System.out.println("One-Page :" + quiz.getAttribute("one-page"));
			System.out.println("Practice Mode :" + quiz.getAttribute("practice-mode"));
			
			Node title = quiz.getElementsByTagName("title").item(0);
			Node description = quiz.getElementsByTagName("description").item(0);
			
			System.out.println("Title :" + title.getTextContent());
			System.out.println("Description :" + description.getTextContent());
			 
			
			NodeList questions = doc.getElementsByTagName("question");
		 
			System.out.println("----------------------------");
		 
			for (int i = 0; i < questions.getLength(); i++) {
				Node qNode = questions.item(i);
				System.out.println("\n");
				if (qNode.getNodeType() == Node.ELEMENT_NODE) {
					Element qElement = (Element) qNode;
					if (qElement.getAttribute("type").equals("picture-response")){
						System.out.println("Question Type : " + qElement.getAttribute("type"));
						Node answerListNode = qElement.getElementsByTagName("answer-list").item(0);
						if (answerListNode!=null){
							NodeList answers = ((Element)answerListNode).getElementsByTagName("answer");
							for (int j = 0; j < answers.getLength(); j++) {
								Node aNode = answers.item(j);
								if (aNode.getNodeType() == Node.ELEMENT_NODE) {
									Element aElement = (Element) aNode;
									System.out.println("Answer : " + aElement.getTextContent());
								}
							}
						}
						else{
							Node aNode = qElement.getElementsByTagName("answer").item(0);
							if (aNode.getNodeType() == Node.ELEMENT_NODE) {
								Element aElement = (Element) aNode;
								System.out.println("Answer : " + aElement.getTextContent());
							}
						}
					}
				}
			}
	    } catch (Exception e) {
			e.printStackTrace();
	    }
	}

	public int getId() {return (Integer) infoMap.get("quiz_id");}
	public String getTitle() {return (String) infoMap.get("title");}
	public int getTimesTaken() {return (Integer) infoMap.get("timesTaken");}
	
	//returns user id of highest scorer
	public int getHighestScorer() {
		int id = (Integer) infoMap.get("quiz_id");
		ArrayList<QuizHistory> attempts = QuizHistoryTable.getQuizAttempts(id);
		int highest = 0;
		int user_id = 0;
		for(QuizHistory qh : attempts) {
			if(qh.getScore() > highest) {
				highest = qh.getScore();
				user_id = qh.getUserId();
			}
		}
		return user_id;
	}
	
}
