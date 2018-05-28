package com.me.abubi.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Year implements Serializable{

	private static final long serialVersionUID = -548208203711631642L;

	private ArrayList<Month> months;
	private String yearName;

	private int currentPosition;
	
	private Month currentMonth;

	public Year() {
		yearName = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		months = new ArrayList<>(12);
	}
	
	public Year(String year) {
		yearName = year;
		months = new ArrayList<>(12);
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public ArrayList<Month> getMonths() {
		return months;
	}

	public void setMonths(ArrayList<Month> months) {
		this.months = months;
	}

	public Month addMonth() {
		Month m = new Month();
		if( contains(m.getMonthIndex())  ) {
			throw new RuntimeException("Month is already present");
		}
		months.add(m);
		currentMonth = m;
		currentPosition++;
		return m;
	}
	
	public Month addMonth(int monthIndex) {
		Month m = new Month(monthIndex);
		if( contains(m.getMonthIndex())  ) {
			throw new RuntimeException("Month is already present");
		}
		months.add(m);
		Collections.sort(months);
		currentPosition = months.indexOf(m);
		currentMonth = m;
		return currentMonth;
	}
	
	public Month addMonth(Month m) {
		if( contains(m.getMonthIndex())  ) {
			throw new RuntimeException("Month is already present");
		}
		months.add(m);
		Collections.sort(months);
		currentPosition = months.indexOf(m);
		currentMonth = m;
		return currentMonth;
	}

	public Month removeMonth(int index) {
		return months.remove(index);
	}
	
	public boolean hasPrevious(Month m) {
		return ! months.isEmpty() && months.get(0).getMonthIndex()<m.getMonthIndex(); 
	}
	
	public boolean hasNext(Month m) {
		return ! months.isEmpty() && months.get(months.size()-1).getMonthIndex()>m.getMonthIndex(); 
	}
	
	public Month nextMonth() {
		if( hasNext(currentMonth) ) {
			currentMonth = months.get(++currentPosition);
		}
		return currentMonth;
	}
	
	public Month previousMonth() {
		if( hasPrevious(currentMonth) ) {
			currentMonth = months.get(--currentPosition);
		}
		return currentMonth;
	}
	
	public Month getCurrentMonth() {
		return currentMonth;
	}

	public boolean contains(Integer monthIndex) {
		return months.contains(new Month(monthIndex));
	}

	public boolean equals(Object o) {
		if( !(o instanceof Year) ) return false;
		if( o==this ) return true;
		Year y = (Year)o;
		return y.yearName.equals(yearName);
	}
	
	@Override
	public String toString() {
		return String.format("year = %s, months = [%s]", yearName, months);
	}

}
