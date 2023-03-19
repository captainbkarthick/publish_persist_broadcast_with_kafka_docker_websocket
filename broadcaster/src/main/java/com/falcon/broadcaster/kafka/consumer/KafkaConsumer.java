package com.falcon.broadcaster.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.falcon.broadcaster.beans.PublishData;
import com.falcon.broadcaster.services.BroadcasterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

	@Autowired
	private BroadcasterService broadcasterService;

	@KafkaListener(topics = "${broadcaster.kafka.topic}", groupId = "GrpContentTime")
	public void listen(ConsumerRecord<String, PublishData> record) throws Exception {
		log.info("received payload='{}' to topic='{}'", record.value(), record.topic());
		broadcasterService.handleConsumedData(new ObjectMapper().readValue(String.valueOf(record.value()), PublishData.class));
	}
}