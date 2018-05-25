package com.me.abubi.utils;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable{
	
	private static final long serialVersionUID = 4946254064503890369L;
	private String user;
	private Date date;
	
	public Entry( String user, Date timestamp) {
		this.user=user;
		this.date=timestamp;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
