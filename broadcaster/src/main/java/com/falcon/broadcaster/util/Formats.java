package com.falcon.broadcaster.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Formats {
	public static final DateTimeFormatter CONTENT_TS_FORMAT = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ssZ").toFormatter();
}
