package com.me.abubi.utils;

import java.util.ArrayList;
import java.util.Iterator;

public class Test {
	
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);

		Iterator<Integer> it1 = list.iterator();
		Iterator<Integer> it2 = list.iterator();
		
		it1.next();
		it1.next();
		System.out.println(it2.next());
	}

}
