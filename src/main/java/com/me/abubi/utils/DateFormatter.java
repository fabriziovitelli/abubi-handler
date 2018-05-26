package com.me.abubi.utils;

import java.text.SimpleDateFormat;

public interface DateFormatter {

	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss.SSS");
	public static final SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEE dd/MM/yy");
	public static final SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/yyyy");

}
