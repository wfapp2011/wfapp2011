package de.uni_potsdam.hpi.wfapp2011.data;

import java.util.Date;

public class Comment {

	private String message = "";
	private Person author;
	private Date date;
	
	public Comment(Person author, String message){
		setAuthor(author);
		setMessage(message);
		setDate(new Date());
	}
	
	public Comment() {
		setDate(new Date());
	}

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
