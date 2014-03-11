package admin;

import databases.AnnouncementTable;

public class Announcement {
	private String text;
	private int id;
	public Announcement(String text) {
		this.text = text;
		this.id = AnnouncementTable.save(text);
	}
	
	public void delete() {
		AnnouncementTable.delete(id);
	}
	
	public String getText() {return text;}
	public int getId() {return id;}
}
