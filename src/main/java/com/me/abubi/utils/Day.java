package com.me.abubi.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Day implements Comparable<Day>, Serializable {

	private static final long serialVersionUID = 3775411110499983460L;

	private ArrayList<Entry> entries;
	private int dayNumber;

	public Day(Integer dayNumber) {
		entries = new ArrayList<>();
		this.dayNumber = dayNumber;
	}

	public void addEntry(String user) {
		Entry entry = new Entry(user, new Date());
		entries.add(entry);
	}

	public void addEntry(Entry e) {
		entries.add(e);
	}

	public Entry removeLastEntry() {
		if (!entries.isEmpty())
			return entries.remove(entries.size() - 1);
		return null;
	}

	public Entry removeEntry(int index) {
		return entries.remove(index);
	}

	public ArrayList<Entry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<Entry> entries) {
		this.entries = entries;
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	@Override
	public int compareTo(Day o) {
		return dayNumber - o.dayNumber ;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Day)) return false;
		if (o == this) return true;
		Day d = (Day) o;
		return d.dayNumber==dayNumber;
	}

	public String toString() {
		return String.format("day = %d, entries = [%s]", dayNumber, entries);
	}

}
