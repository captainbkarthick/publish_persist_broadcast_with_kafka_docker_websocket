package com.falcon.broadcaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falcon.broadcaster.services.BroadcasterService;

@RestController
@RequestMapping("/broadcaster")
public class BroadcasterController {

	@Autowired
	private BroadcasterService broadcasterService;

	@GetMapping("test")
	public ResponseEntity<String> testConnection() {
		return ResponseEntity.ok("Broadcaster Service is Running!!!");
	}

	@GetMapping("testBroadcast")
	public ResponseEntity<String> testBroadcast() {
		broadcasterService.testBroadcast();
		return ResponseEntity.ok("Broadcasted Successfully!!");
	}
}
