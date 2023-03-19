package com.falcon.persister.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.falcon.persister.dto.ContentTimeDTO;
import com.falcon.persister.services.PersisterService;
import com.falcon.persister.util.Constants;
import com.falcon.persister.validator.RequestValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Persister Controller")
@RestController
@RequestMapping("/persister")
public class PersisterController {
	
	public PersisterController() {
		requestValidator = new RequestValidator();
	}
	
	private static final String DEFAULT_SORT_FIELD = Constants.MESSAGE_TIME;
	private static final String DEFAULT_SORT_DIRECTION = Constants.ASCENDING;
	
	private static final String M_GET_ALL_MESSAGES_DESC =  "This method gets all data from the database by sorting based on the query parameter, 'sortField'. The order of sorting is based on the query parameter, 'sortDirection'.";	
	private static final String M_GET_MESSAGES_BY_DATE_DESC = "This method is returns the filtered data based on the provided Date Criteria. \n\n NOTE: Atleast one of 'fromDate' or 'toDate' needs to be provided .\n Valid Date Format:  " + Constants.DATE_FORMAT;	
	private static final String M_GET_RECORDS_FOR_FIELD_DESC = "This method is returns the row(s) matching the given 'Field Name' in the path parameter to the 'value' provided in the query parameter. \n\n NOTE: Valid Date Format:  " + Constants.DATE_FORMAT;
	
	
	
	private static final String P_ALL_REQUEST_FIELDS =  "id, content, messageTime, createdTime, updatedTime";	
	private static final String P_DATE_REQUEST_FIELDS =  "messageTime, createdTime, updatedTime";
	private static final String P_SORT_DIRECTION_FIELDS =  "asc, desc";
	
	private static final String P_SORT_FIELD_NAME = "sortField";	
	private static final String P_SORT_FIELD_DESC = "The Field Name with which the data to be sorted. \n Valid options are: "+ P_ALL_REQUEST_FIELDS;		   
	
	private static final String P_SORT_DIRECTION_NAME = "sortDirection";	
	private static final String P_SORT_DIRECTION_DESC = "The order in which the returned data to be sorted. \n Valid options are: "+ P_SORT_DIRECTION_FIELDS;	
	
	private static final String P_REQ_FIELD_NAME =  "fieldName";
	private static final String P_REQ_FIELD_DESC =  "Name of the field to be matched in database \n Valid options are: "+ P_ALL_REQUEST_FIELDS;
	private static final String P_REQ_FIELD_EXAMPLE =  "content";
			
	private static final String P_FIELD_VALUE_NAME =  "value";
	private static final String P_FIELD_VALUE_DESC =  "The value to match the fieldName in database to fetch data";
	private static final String P_FIELD_VALUE_EXAMPLE =  "Abrakadaarbraa";
	
	private static final String P_DATE_FIELD_NAME =  "fieldName";
	private static final String P_DATE_FIELD_DESC =  "Name of the Date field to be matched in database. \n Valid options are: "+ P_DATE_REQUEST_FIELDS;
	
	private static final String P_FROM_DATE_NAME =  "fromDate";
	private static final String P_FROM_DATE_DESC =  "Data will be fetched for the fieldName having value GREATER than or equal to this date.";
	private static final String P_FROM_DATE_EXAMPLE =  "2020-08-04 08:49:08+0530";

	private static final String P_TO_DATE_NAME =  "toDate";
	private static final String P_TO_DATE_DESC =  "Data will be fetched for the fieldName having value LESSER than or equal to this date.";
	private static final String P_TO_DATE_EXAMPLE =  "2021-05-08 07:30:04+0530";
	
	RequestValidator requestValidator;
	
	@Autowired
	PersisterService persisterService;

	@GetMapping("test")
	public ResponseEntity<String> testConnection() {
		return ResponseEntity.ok("Persister Service is Running!!!");
	}

	@GetMapping("get-all-data")
	@ApiOperation(value = "Get All Messages", notes = M_GET_ALL_MESSAGES_DESC)
	public ResponseEntity<Object> getAllMessages(
			@ApiParam(name = P_SORT_FIELD_NAME, value = P_SORT_FIELD_DESC, example = DEFAULT_SORT_FIELD) @RequestParam(value = "sortField", defaultValue = DEFAULT_SORT_FIELD) String requestFieldName,
			@ApiParam(name = P_SORT_DIRECTION_NAME, value = P_SORT_DIRECTION_DESC, example = DEFAULT_SORT_DIRECTION) @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {

		Optional<String> validationError = requestValidator.validateGetAllMessages(requestFieldName, sortDirection);

		if (validationError.isPresent()) {
			return ResponseEntity.badRequest().body(validationError.get());
		}

		Optional<List<ContentTimeDTO>> messageList;
		messageList = persisterService.getAllMessagesSorted(requestFieldName, sortDirection);

		return messageList.isPresent() ? ResponseEntity.ok(messageList.get())
				: ResponseEntity.badRequest().body("Unknown Error");
	}
	
	
	@GetMapping("get-temporal-data/{fieldName}")
	@ApiOperation(value = "Get Messages for specified Date Criteria", notes = M_GET_MESSAGES_BY_DATE_DESC)
	public ResponseEntity<Object> getMessagesByDate(
			@ApiParam(name = P_DATE_FIELD_NAME, value = P_DATE_FIELD_DESC) @PathVariable(value = "fieldName") String requestFieldName,
			@ApiParam(name = P_FROM_DATE_NAME, value = P_FROM_DATE_DESC, example = P_FROM_DATE_EXAMPLE) @RequestParam(value = "fromDate", required = false) String fromDate,
			@ApiParam(name = P_TO_DATE_NAME, value = P_TO_DATE_DESC, example = P_TO_DATE_EXAMPLE) @RequestParam(value = "toDate", required = false) String toDate,
			@ApiParam(name = P_SORT_FIELD_NAME, value = P_SORT_FIELD_DESC, example = DEFAULT_SORT_FIELD) @RequestParam(value = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
			@ApiParam(name = P_SORT_DIRECTION_NAME, value = P_SORT_DIRECTION_DESC, example = DEFAULT_SORT_DIRECTION) @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {

		Optional<String> validationError = requestValidator.validateGetMessagesByDate(requestFieldName, fromDate,
				toDate, sortField, sortDirection);

		if (validationError.isPresent()) {
			return ResponseEntity.badRequest().body(validationError.get());
		}

		Optional<List<ContentTimeDTO>> messageList = persisterService.getAllMessagesByDate(requestFieldName, fromDate,
				toDate, sortField, sortDirection);
		return messageList.isPresent() ? ResponseEntity.ok(messageList.get())
				: ResponseEntity.badRequest().body("No Data Found");
	}
	
	@GetMapping("get-records/{fieldName}")
	@ApiOperation(value = "Get Messages for a field having a matching value", notes = M_GET_RECORDS_FOR_FIELD_DESC)
	public ResponseEntity<Object> getRecordsForField(
			@ApiParam(name = P_REQ_FIELD_NAME, value = P_REQ_FIELD_DESC, example = P_REQ_FIELD_EXAMPLE) @PathVariable(value = "fieldName") String requestFieldName,
			@ApiParam(name = P_FIELD_VALUE_NAME, value = P_FIELD_VALUE_DESC, example = P_FIELD_VALUE_EXAMPLE) @RequestParam(value = "value", required = true) String fieldValue,
			@ApiParam(name = P_SORT_FIELD_NAME, value = P_SORT_FIELD_DESC, example = DEFAULT_SORT_FIELD) @RequestParam(value = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
			@ApiParam(name = P_SORT_DIRECTION_NAME, value = P_SORT_DIRECTION_DESC, example = DEFAULT_SORT_DIRECTION) @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {

		Optional<String> validationError = requestValidator.validateGetRecordsForField(requestFieldName, fieldValue,
				sortField, sortDirection);

		if (validationError.isPresent()) {
			return ResponseEntity.badRequest().body(validationError.get());
		}

		Optional<List<ContentTimeDTO>> messageList = persisterService.getRecordsForField(requestFieldName, fieldValue,
				sortField, sortDirection);
		return messageList.isPresent() ? ResponseEntity.ok(messageList.get())
				: ResponseEntity.badRequest().body("No Data Found");
	}
	
	
}