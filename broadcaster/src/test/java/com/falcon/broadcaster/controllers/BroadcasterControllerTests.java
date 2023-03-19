package com.falcon.broadcaster.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.falcon.broadcaster.services.impl.BroadcasterServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BroadcasterController.class,BroadcasterServiceImpl.class,SimpMessagingTemplate.class})
@WebMvcTest(controllers = BroadcasterController.class)
public class BroadcasterControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	MessageChannel messageChannel;

	@Test
	public void testService() throws Exception {
		mockMvc.perform(get("/broadcaster/test")).andExpect(status().isOk()).andExpect(content().string("Broadcaster Service is Running!!!"));
	}
}
