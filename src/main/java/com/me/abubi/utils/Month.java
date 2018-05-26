package com.me.abubi.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Month {

	private ArrayList<Day> days;
	private Date date;
	
	private Day currentDay;
	
	private int currentPosition;

	public Month() {
		days = new ArrayList<>();
		date = new Date();
		currentPosition=0;
	}

	public Month(Date time) {
		this();
		date = time;
	}

	public Day addNewDay() {
		Day day = new Day(new Date());
		days.add(day);
		currentDay = day;
		currentPosition++;
		return day;
	}

	public Day addNewDay(Date date) {
		Day day = new Day(date);
		days.add(day);
		Collections.sort(days);
		currentDay = day;
		currentPosition = days.indexOf(day);
		return day;
	}

	public void removeDay(Day d) {
		days.remove(d);
		if( hasNextDay() ) {
			currentDay = days.get(currentPosition);
		}else if( hasPreviousDay() ) {
			currentDay = days.get(--currentPosition);
		}
	}

	public ArrayList<Day> getDays() {
		return days;
	}

	public void setDays(ArrayList<Day> days) {
		this.days = days;
	}

	public Day getLastDay() {
		return currentDay;
	}
	
	public boolean hasNextDay() {
		return currentPosition<days.size()-1;
	}
	
	public boolean hasPreviousDay() {
		return currentPosition>0;
	}

	public Day nextDay() {
		if( currentPosition>days.size()-1 ) {
			return currentDay;
		}else {
			currentPosition++;
			currentDay = days.get(currentPosition);
			return currentDay;
		}
	}
	
	public Day previousDay() {
		if( currentPosition<0 ) {
			return currentDay;
		}else {
			currentPosition--;
			currentDay = days.get(currentPosition);
			return currentDay;
		}
	}
	
	public Day goToDay(Integer day) {
		while( currentPosition>day ) { previousDay(); }
		while( currentPosition<day ) { nextDay(); }
		return currentDay;
	}
	
	public Day deleteDay(Integer day) {
		while( currentPosition>day ) { previousDay(); }
		while( currentPosition<day ) { nextDay(); }
		return currentDay;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void clear() {
		currentDay=null;
		currentPosition=0;
		days.clear();
	}

	public String toString() {
		return String.format("month=%s days=%s", DateFormatter.monthFormatter.format(date), days);
	}

}
