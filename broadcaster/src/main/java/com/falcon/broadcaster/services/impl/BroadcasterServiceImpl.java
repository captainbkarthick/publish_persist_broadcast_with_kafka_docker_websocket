package com.falcon.broadcaster.services.impl;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.falcon.broadcaster.beans.PublishData;
import com.falcon.broadcaster.services.BroadcasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BroadcasterServiceImpl implements BroadcasterService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Value("${broadcaster.websocket.topic-destination-prefix}${broadcaster.websocket.topic-name}")
	private String destinationTopic;

	@Override
	public boolean handleConsumedData(PublishData publishData) {
		log.info("In Broadcaster Service - Before bradcasting Data");
		messagingTemplate.convertAndSend(destinationTopic, publishData);
		return true;
	}

	@Override
	public boolean testBroadcast() {
		log.info("In Test Broadcaster Service - Before bradcasting Data");
		messagingTemplate.convertAndSend(destinationTopic,
				PublishData.builder().content("Test Broadcast").timestamp(ZonedDateTime.now()).build());
		return true;
	}

}
