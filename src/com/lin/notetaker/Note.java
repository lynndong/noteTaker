package com.lin.notetaker;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
	
	public Note(String title, String note, Date date) {
		super();
		this.title = title;
		this.note = note;
		this.date = date;
	}
	
	private String title;
	private String note;
	private Date date;
	
	public Date getDate() {
		return date;
	}
	public String getNote() {
		return note;
	}
	public String getTitle() {
		return title;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
