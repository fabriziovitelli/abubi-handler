package com.me.abubi.utils;

import java.util.ArrayList;
import java.util.Date;

public class Day implements Comparable<Day>{

	private ArrayList<Entry> entries;
	private Date date;
	
	public Day() {
		entries = new ArrayList<>();
		date = new Date();
	}
	
	public Day(Date time) {
		entries = new ArrayList<>();
		date = time;
	}
	
	public void addEntry(String user) {
		Entry entry = new Entry(user, new Date());
		entries.add(entry);
	}
	
	public Entry removeLastEntry() {
		return entries.remove(entries.size()-1);
	}
	
	public ArrayList<Entry> getEntries() { return entries; }
	
	public void setEntries(ArrayList<Entry> entries) { this.entries = entries; }

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }
	
	@Override
	public int compareTo(Day o) {
		return date.compareTo(o.date);
	}
	
	public String toString() {
		return String.format("day=%s entries=%s", DateFormatter.dayFormatter.format(date), entries);
	}

}
