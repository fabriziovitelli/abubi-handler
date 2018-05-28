package com.me.abubi.utils;

import java.awt.Color;
import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable{
	
	private static final long serialVersionUID = 4946254064503890369L;

	public static final Color kColor = Color.RED;
	public static final Color pColor = Color.YELLOW;
	public static final Color fColor = Color.BLUE;

	public static final String kNick = "k";
	public static final String pNick = "p";
	public static final String fNick = "f";
	
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
	
	public boolean equals(Object o) {
		if( !(o instanceof Entry) ) return false;
		if( o==this ) return true;
		Entry e = (Entry)o;
		return e.date.equals(date) && e.user.equals(user);
	}
	
	public String toString() {
		return String.format("Entry: user=%s date=%s", user, DateFormatter.timeFormatter.format(date));
	}

}
