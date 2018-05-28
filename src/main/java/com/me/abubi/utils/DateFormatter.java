package com.me.abubi.utils;

import java.text.SimpleDateFormat;

public interface DateFormatter {

	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
	public static final SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEE dd/MM/yy");
	public static final SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yy");
	public static final SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/yyyy");

}
