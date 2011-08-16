package de.uni_potsdam.hpi.wfapp2011.proposals.client.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * Comment is a dataclass that encapsulates messages, authors and dates.
 * It further provides helper methods for rendering.  
 * 
 * @author Katrin Honauer, Josefine Harzmann
 */
public class Comment implements IsSerializable{

	private int id;	
	private String message = "";
	private Person author;
	private Date date;
	private String dateAndTimeString;

	// standard constructor (necessary for IsSerializable)
	public Comment() {
		setDate(new Date());
	}

	public Comment(Person author, String message){
		setAuthor(author);
		setMessage(message);
		setDate(new Date());
	}
	
	// helper-methods for rendering
	public String getDateAndTimeStringOr(String defaultString){
		if ((dateAndTimeString != null) && (dateAndTimeString.trim().length() > 0)){
			return "am " + dateAndTimeString+": ";
		}
		return defaultString;
	}
	
	public String getAuthorOr(String defaultString){
		if ((author != null) && (author.getName().trim().length()>0)){
			return author.getName()+ " schrieb ";
		}
		return defaultString;
	}	
	
	// standard getters and setters	
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
	
	public void setDateAndTimeString(String dateStr){
		dateAndTimeString = dateStr;
	}
	
	public String getDateAndTimeString(){
		return dateAndTimeString;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
