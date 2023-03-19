package com.falcon.persister.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.falcon.persister.entities.ContentTimeEntity;
import com.falcon.persister.util.Commons;
import com.falcon.persister.util.Constants;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ContentTimeRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	ContentTimeRepository contentTimeRepository;
	
	List<ContentTimeEntity> entityList;

	@BeforeEach
	 public  void setupData() {	
		/*
		 The test data from schema.sql should also be considered
		 * */
		entityManager.persist(ContentTimeEntity.builder().content("foxtrot").messageTime(Commons.getZonedDateTime("2017-10-05 13:12:12+0530")).build());
		entityManager.persist(ContentTimeEntity.builder().content("abccba").messageTime(Commons.getZonedDateTime("2018-06-06 06:00:12+0630")).build());
		entityManager.persist(ContentTimeEntity.builder().content("abrakadabra").messageTime(Commons.getZonedDateTime("2018-10-07 17:12:12-1030")).build());
		entityManager.persist(ContentTimeEntity.builder().content("India").messageTime(Commons.getZonedDateTime("2018-10-08 04:12:12+0000")).build());
		entityManager.persist(ContentTimeEntity.builder().content("foxtrot").messageTime(Commons.getZonedDateTime("2021-05-15 21:12:12+0730")).build());
	 }

	@Test
	public void testPersisting() throws Exception {
		entityManager.persist(ContentTimeEntity.builder().content("Test Message").messageTime(Commons.getZonedDateTime("2021-05-12 18:12:12+0530")).build());
	}
	
	@Test
	public void findByMessageId() throws Exception{
		entityList = contentTimeRepository.findByMessageId(1, null);		
		assertEquals(entityList.stream().map(entity-> entity.getMessageId()).findFirst().get(),1);
	}
		
	@Test
	public void findByContent() throws Exception{
		entityList = contentTimeRepository.findByContent("foxtrot", null);
		assertEquals(entityList.stream().count(),2);
	}
	
	@Test
	public void findByMessageTime() throws Exception{
		entityList = contentTimeRepository.findByMessageTime(Commons.getZonedDateTime("2018-10-07 17:12:12-1030"), null);
		assertEquals(entityList.stream().map(entity->entity.getContent()).findFirst().get(),"abrakadabra");
	}	

	@Test
	public void findByMessageTimeLessThanEqual() throws Exception{		
		entityList = contentTimeRepository.findByMessageTimeLessThanEqual(Commons.getZonedDateTime("2018-10-07 17:12:12-1030"), Sort.by(getSortOrder("content", "asc")));		
		assertEquals(entityList.stream().map(entity->entity.getContent()).findFirst().get(),"abccba");
	}
		
	@Test
	public void findByMessageTimeGreaterThanEqual() throws Exception{		
		entityList = contentTimeRepository.findByMessageTimeGreaterThanEqual(Commons.getZonedDateTime("2018-10-08 04:12:12+0000"), Sort.by(getSortOrder("messageTime", "desc")));		
		assertEquals(entityList.stream().map(entity->entity.getMessageTime()).findFirst().get(),Commons.getZonedDateTime("2021-05-15 21:12:12+0730"));
	}
	
	@Test
	public void findByMessageTimeBetween() throws Exception{		
		entityList = contentTimeRepository.findByMessageTimeBetween(Commons.getZonedDateTime("2017-10-05 13:12:12+0530"), Commons.getZonedDateTime("2018-10-08 04:12:12+0000"), null);		
		assertEquals(entityList.stream().count(),4);
	}	

	
	private Order getSortOrder(String sortField,String sortDirection) {
		return new Order(Sort.Direction.fromString(sortDirection),Constants.TBL_DATA_ENTITY_NAMES.get(sortField));
	}
	
	public void printAll(List<ContentTimeEntity> entityList) {
		System.out.println("******************************************************************" );
		entityList.stream().forEach(System.out::println);
		System.out.println("******************************************************************" );
	}
}
