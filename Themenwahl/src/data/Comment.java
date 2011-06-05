package data;

import java.util.Date;

public class Comment {

	private String message;
	private Person author;
	private Date date;
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
	public Person getAuthor() {
		return author;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
