package com.falcon.publisher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falcon.publisher.beans.PublishData;
import com.falcon.publisher.services.PublishService;
import com.falcon.publisher.util.Constants;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

	@Autowired
	private PublishService publishService;
	
	private static final String P_DATA_NAME="Data To Publish";	
	private static final String P_DATA_DESC="This is a JSON object having message and the timestamp in the format: "+Constants.DATE_FORMAT;
	
	@PostMapping("publish")	
	public ResponseEntity<String> publishData(
			@ApiParam(name = P_DATA_NAME, value = P_DATA_DESC)@RequestBody PublishData publishData) {
		boolean result = publishService.processMessage(publishData);
		return ResponseEntity.ok(result ? "Data Processed Successfully" : "Error In processing");
	}
	
	@GetMapping("test")
	public ResponseEntity<String> testConnection(){
		return ResponseEntity.ok("Publisher Service is Running!!!");
	}
}
