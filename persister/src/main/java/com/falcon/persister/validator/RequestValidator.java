package com.falcon.persister.validator;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.util.StringUtils;

import com.falcon.persister.util.Constants;
import com.falcon.persister.util.Formats;

public class RequestValidator {

	public Optional<String> validateGetAllMessages(String sortField, String sortDirection) {
		StringBuilder errorBuilder = new StringBuilder();

		validateSortDirection(errorBuilder, sortDirection);
		validateRequestParameterFieldName(errorBuilder, sortField,"Sort Field");

		if (errorBuilder.length() > 0) {
			return Optional.of(errorBuilder.toString());
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<String> validateGetMessagesByDate(String requestFieldName,String fromDate,String toDate,String sortField, String sortDirection) {
				
		StringBuilder errorBuilder = new StringBuilder();
		
		if(!StringUtils.hasText(fromDate) && !StringUtils.hasText(toDate)) {
			errorBuilder.append("Either 'FromDate' or 'ToDate' is needed.").append("\n");
		}
		
		boolean isDateField = isValidRequestDateFieldName(requestFieldName);
		
		if (!isDateField) {
			errorBuilder.append("Not a valid DateField.").append("\n");
		}
		
		validateRequestParameterFieldName(errorBuilder, requestFieldName,"Date Field");		
		validateDateFormat(errorBuilder, fromDate, "From Date");
		validateDateFormat(errorBuilder, toDate, "TO Date");
		validateSortDirection(errorBuilder, sortDirection);		
		
		if (errorBuilder.length() > 0) {
			return Optional.of(errorBuilder.toString());
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<String> validateGetRecordsForField(String requestFieldName, String fieldValue, String sortField, String sortDirection) {
		StringBuilder errorBuilder = new StringBuilder();
		
		validateRequestParameterFieldName(errorBuilder, requestFieldName,"Field Name");	
		validateSortDirection(errorBuilder, sortDirection);
		validateRequestParameterFieldName(errorBuilder, sortField,"Sort Field");
		if(requestFieldName.endsWith("Time"))
			validateDateFormat(errorBuilder, fieldValue, "Input Value");
		
		if (errorBuilder.length() > 0) {
			return Optional.of(errorBuilder.toString());
		} else {
			return Optional.empty();
		}
	}
	
	private void validateDateFormat(StringBuilder builder, String dateStr, String description) {
		try {
			if (StringUtils.hasText(dateStr)) {
				ZonedDateTime.parse(dateStr, Formats.CONTENT_TS_FORMAT);
			}
		} catch (Exception e) {
			builder.append("Invalid Date Format for :").append(description).append("\n");
		}
	}

	private void validateSortDirection(StringBuilder builder, String sortDirection) {
		switch(sortDirection.toUpperCase()) {
			case "ASC","DESC":
				return;
			default:
				builder.append("Invalid 'SortDirection' Parameter").append("\n");
		}
	}
	
	private void validateRequestParameterFieldName(StringBuilder builder, String reqParamField, String description) {
		if(!isValidRequestField(reqParamField))			
			builder.append("Invalid '").append(description).append("' Parameter").append("\n");
	}
		
	private boolean isValidRequestField(String requestFieldName) {
		if(isValidRequestNonDateFieldName(requestFieldName) || isValidRequestDateFieldName(requestFieldName)) 
			return true;
		else
			return false;					
	}
	
	private boolean isValidRequestNonDateFieldName(String requestFieldName) {
		return switch (requestFieldName) {
			case Constants.MESSAGE_ID, 
			     Constants.CONTENT:
				yield true;
			default:
				yield false;
		};
	}
	
	private boolean isValidRequestDateFieldName(String requestFieldName) {
		return switch (requestFieldName) {
			case Constants.MESSAGE_TIME, 
			     Constants.ROW_CREATED_TIME, 
			     Constants.LAST_UPDATED_TIME:
				yield true;
			default:
				yield false;
		};
	}
}
