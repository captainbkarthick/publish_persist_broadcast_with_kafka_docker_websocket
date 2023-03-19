package com.falcon.publisher.util;

import java.time.ZonedDateTime;

public class Commons {
	public static ZonedDateTime getZonedDateTime(String dateString) {
		return ZonedDateTime.parse(dateString, Formats.CONTENT_TS_FORMAT);		
	}
}
