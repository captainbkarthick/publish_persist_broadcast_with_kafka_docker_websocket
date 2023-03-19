package com.falcon.persister.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	private Constants() {
	}

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
	
	/* Valid Request Parameter Values - Begin*/
	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "desc";

	public static final String MESSAGE_ID = "id";
	public static final String CONTENT = "content";
	public static final String MESSAGE_TIME = "messageTime";
	public static final String ROW_CREATED_TIME = "createdTime";
	public static final String LAST_UPDATED_TIME = "updatedTime";
	/* Valid Request Parameter Values - End*/
	
	/* Request Parameter to Database table column mapping */
	public static final Map<String, String> TBL_DATA_ENTITY_NAMES = new HashMap<String, String>();
	
	static {
		TBL_DATA_ENTITY_NAMES.put(MESSAGE_ID, "messageId");
		TBL_DATA_ENTITY_NAMES.put(CONTENT, "content");
		TBL_DATA_ENTITY_NAMES.put(MESSAGE_TIME, "messageTime");
		TBL_DATA_ENTITY_NAMES.put(ROW_CREATED_TIME, "rowCreatedTime");
		TBL_DATA_ENTITY_NAMES.put(LAST_UPDATED_TIME, "lastUpdatedTime");		
	}

}
