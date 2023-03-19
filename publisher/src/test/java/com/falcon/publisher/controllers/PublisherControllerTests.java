package com.falcon.publisher.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.falcon.publisher.beans.PublishData;
import com.falcon.publisher.kafka.producer.KafkaProducer;
import com.falcon.publisher.services.impl.PublishServiceImpl;
import com.falcon.publisher.util.Commons;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PublisherController.class, PublishServiceImpl.class,KafkaProducer.class,PublishData.class})
@WebMvcTest(controllers=PublisherController.class)
public class PublisherControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	KafkaTemplate<String, PublishData> kafkaTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	PublishData publishData;
	
	@Test
	public void testServiceStatus() throws Exception{
		mockMvc.perform(get("/publisher/test")).andExpect(status().isOk()).andExpect(content().string("Publisher Service is Running!!!"));
	}
	
	@Test
	public void testPublishData() throws Exception{
		publishData = PublishData.builder().content("TestPublishData").timestamp(Commons.getZonedDateTime("2018-10-08 04:12:12+0000")).build();
		
		mockMvc.perform( post("/publisher/publish")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(publishData)
						.getBytes(StandardCharsets.UTF_8)
						)
				.accept(MediaType.APPLICATION_JSON)				
				).andExpect(status().isOk()).andExpect(content().string("Data Processed Successfully"));
	}
	
	@Test
	public void testNotFound() throws Exception{
		mockMvc.perform(get("/publisher/tester")).andExpect(status().isNotFound());		
	}
}
