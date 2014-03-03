package models;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;


public class Quiz {
	
	private ArrayList<Question> questionArr;
	private ArrayList<String> answerArr;
	private String title;
	private boolean isRandom;
	private boolean isOnePage;
	private boolean hasImmediateFeedback;
	private boolean practiceMode;
	private int score;
	private int completionTime;
	
	public Quiz(ArrayList<Question> questionArr, String title, 
			String description, boolean isRandom, 
			boolean isOnePage, boolean hasImmediateFeedback, boolean practiceMode) {
		this.questionArr = questionArr;
		this.answerArr = new ArrayList<String>(questionArr.size());
		this.title = title;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.hasImmediateFeedback = hasImmediateFeedback;
		this.practiceMode = practiceMode;
		this.score = 0;
		this.completionTime = 0;
		
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

}
