package com.me.abubi.utils;

import java.util.*;

public class Day {

	private ArrayList<Entry> entries;
	
	public Day() {
		entries = new ArrayList<>();
	}
	
	public void addEntry(String user) {
		Entry entry = new Entry(user, new Date());
	}
	
	public ArrayList<Entry> getEntries() { return entries; }
	
	public void setEntries(ArrayList<Entry> days) { this.entries = days; }

}
