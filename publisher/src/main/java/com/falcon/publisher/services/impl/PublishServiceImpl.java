package com.falcon.publisher.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.falcon.publisher.beans.PublishData;
import com.falcon.publisher.kafka.producer.KafkaProducer;
import com.falcon.publisher.services.PublishService;

@Service
public class PublishServiceImpl implements PublishService{

	@Autowired
	private KafkaProducer producer;
	
	@Override
	public boolean processMessage(PublishData publishData) {		
		return sendMessage(publishData);	
	}
	
	public boolean validate(PublishData publishData) {		
		return false;
	}
	
	public boolean sendMessage(PublishData publishData) {
		producer.publishMessage(publishData);
		return true;
	}
}
