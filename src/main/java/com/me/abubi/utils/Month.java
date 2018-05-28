package com.me.abubi.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Month implements Serializable,Comparable<Month> {
	
	private static final long serialVersionUID = 4320694085376912130L;
	
	private ArrayList<Day> days;
	private String monthName;
	private int monthIndex;

	private Day currentDay;
	private int currentPosition;

	public Month() {
		days = new ArrayList<>(31);
		Calendar now = Calendar.getInstance();
		monthName = new SimpleDateFormat("MMMM").format(now.getTime());
		monthIndex = now.get(Calendar.MONTH)+1;
		currentPosition = 0;
	}
	
	public Month(String monthName) {
		this(getIntFromName(monthName));
	}
	
	public Month(int monthIndex) {
		this();
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MONTH, monthIndex-1);
		monthName = new SimpleDateFormat("MMMM").format(now.getTime());
		this.monthIndex = monthIndex;
	}

	public static int getIntFromName(String monthName){
		try {
			Date date = new SimpleDateFormat("MMMM").parse(monthName);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH)+1;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	public Day addDay() {
		Day day = new Day(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		if( contains(day.getDayNumber())  ) {
			throw new RuntimeException("Day is already present");
		}
		days.add(day);
		currentPosition++;
		currentDay = day;
		return currentDay;
	}

	public Day addDay(Integer dayNumber) {
		Day day = new Day(dayNumber);
		days.add(day);
		Collections.sort(days);
		currentPosition = days.indexOf(day);
		currentDay = day;
		return currentDay;
	}
	
	public void addDay(Day d) {
		days.add(d);
		Collections.sort(days);
		currentPosition = days.indexOf(d);
		currentDay = d;
	}
	
	public boolean hasPrevious(Day d) {
		return ! days.isEmpty() && days.get(0).getDayNumber()<d.getDayNumber(); 
	}
	
	public boolean hasNext(Day d) {
		return ! days.isEmpty() && days.get(days.size()-1).getDayNumber()>d.getDayNumber(); 
	}

	public void removeDay(Day d) {
		days.remove(d);
		if( hasPrevious(d) ) 
			currentDay = previousDay();
		else if( hasNext(d))
			currentDay = nextDay();
	}

	public ArrayList<Day> getDays() {
		return days;
	}

	public void setDays(ArrayList<Day> days) {
		this.days = days;
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
	
	public Day getCurrentDay() {
		return currentDay;
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
	
	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	
	public int getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(int monthIndex) {
		this.monthIndex = monthIndex;
	}
	
	public void clear() {
		currentDay=null;
		currentPosition=0;
		days.clear();
	}
	
	
	public boolean contains(int dayNumber) {
		return days.contains(new Day(dayNumber));
	}
	
	public int getTotalEntrance() {
		int count = 0;
		for(Day d: days) {
			count+=d.getEntries().size();
		}
		return count;
	}
	
	@Override
	public int compareTo(Month o) {
		return monthIndex-o.monthIndex;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if( !(o instanceof Month) ) return false;
		if( o==this ) return true;
		Month m = (Month)o;
		return m.monthIndex==monthIndex;
	}
	
	@Override
	public String toString() {
		return String.format("month = %s, days = [%s]", monthName, days);
	}

}
