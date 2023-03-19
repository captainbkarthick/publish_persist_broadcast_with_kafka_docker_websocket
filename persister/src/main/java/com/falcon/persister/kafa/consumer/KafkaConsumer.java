package com.falcon.persister.kafa.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.falcon.persister.beans.PublishData;
import com.falcon.persister.services.PersisterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

	@Autowired
	private PersisterService persisterService;

	@KafkaListener(topics = "${persister.kafka.topic}", groupId = "ContentTimestamp")
	public void listen(ConsumerRecord<String, PublishData> record) throws Exception {
		log.info("received payload='{}' to topic='{}'", record.value(), record.topic());
		persisterService.handleConsumedData(new ObjectMapper().readValue(String.valueOf(record.value()), PublishData.class));
	}
}
