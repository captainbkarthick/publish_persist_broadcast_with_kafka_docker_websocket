package com.falcon.publisher.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.falcon.publisher.beans.PublishData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaProducer {
	
	@Value("${publisher.kafka.topic}")
	private String topic;
		
	@Autowired
    private KafkaTemplate<String, PublishData> kafkaTemplate;
	
	public boolean publishMessage(PublishData data) {
		
		log.info("Publishing data ='{}' to topic='{}'", data, topic);
		try {
			kafkaTemplate.send(topic, data);
		} catch (Exception e) {
			log.error("Publish Failed due to {}",e.getMessage());
			return false;
		}		
		return true;
	}
}
