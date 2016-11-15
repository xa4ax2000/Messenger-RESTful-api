package org.tutorial.javabrains.messenger.model;

import java.util.Date;

public class Comment {

	/*--------------------------Private Class Variables--------------------------*/
	
	private long id;
	private String comment;
	private Date created;
	private String author;
	
	/*------------------------------Constructor(s)-------------------------------*/
	
	public Comment(){
		
	}
	
	public Comment(long id, String comment, String author){
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.created = new Date();
	}
	
	/*------------------------------Getters/Setters------------------------------*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return comment;
	}

	public void setMessage(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
