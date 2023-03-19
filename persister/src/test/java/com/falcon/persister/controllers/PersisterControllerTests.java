package com.falcon.persister.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.falcon.persister.repositories.ContentTimeRepository;
import com.falcon.persister.services.impl.PersisterServiceImpl;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersisterController.class,PersisterServiceImpl.class})
@WebMvcTest(controllers = PersisterController.class)
public class PersisterControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ContentTimeRepository contentTimeRepository;
	
	@Test
	public void testService() throws Exception {
		mockMvc.perform(get("/persister/test")).andExpect(status().isOk()).andExpect(content().string("Persister Service is Running!!!"));
	}
	
	@Test
	public void testGetAllData() throws Exception{
		mockMvc.perform(get("/persister/get-all-data")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllDataWithSort() throws Exception{
		mockMvc.perform(get("/persister/get-all-data?sortField=content&sortDirection=asc")).andExpect(status().isOk());
	}
		
	@Test
	public void testNotFound() throws Exception{	
		mockMvc.perform(get("/persister/tester")).andExpect(status().isNotFound());		
	}
	
	@Test
	public void testBadRequests() throws Exception{
		mockMvc.perform(get("/persister/get-temporal-data/messageTime")).andExpect(status().isBadRequest());
		mockMvc.perform(get("/persister/get-all-data?sortField=content&sortDirection=des")).andExpect(status().isBadRequest());
	}
	
}
