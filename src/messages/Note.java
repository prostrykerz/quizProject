package messages;

import users.User;

public class Note extends Message {
	
	public Note(User sender, User receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

//	@Override
//	String getMessage() {
//		return message;
//	}
	
	

}
