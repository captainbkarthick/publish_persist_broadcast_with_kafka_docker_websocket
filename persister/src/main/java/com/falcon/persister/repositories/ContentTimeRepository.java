package com.falcon.persister.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.falcon.persister.entities.ContentTimeEntity;

@Repository
public interface ContentTimeRepository extends JpaRepository<ContentTimeEntity,Integer>{
	
	/* ALL */
	public List<ContentTimeEntity> findAll(Sort sortCondition);
		
	/* EQUALS */
	public List<ContentTimeEntity> findByMessageId(int messageId, Sort sortcondition);	
	public List<ContentTimeEntity> findByContent(String content, Sort sortcondition);
	public List<ContentTimeEntity> findByMessageTime(ZonedDateTime messageTime, Sort sortcondition);
	public List<ContentTimeEntity> findByRowCreatedTime(ZonedDateTime messageTime, Sort sortcondition);
	public List<ContentTimeEntity> findByLastUpdatedTime(ZonedDateTime messageTime, Sort sortcondition);

	/* LESS THAN OR EQUAL TO A TIMESTAMP */	
	public List<ContentTimeEntity> findByMessageTimeLessThanEqual(ZonedDateTime messageTime,Sort sortcondition);
	public List<ContentTimeEntity> findByRowCreatedTimeLessThanEqual(ZonedDateTime rowCreatedTime,Sort sortcondition);
	public List<ContentTimeEntity> findByLastUpdatedTimeLessThanEqual(ZonedDateTime lastUpdatedTime,Sort sortcondition);
	
	/* GREATER THAN OR EQUAL TO A TIMESTAMP */
	public List<ContentTimeEntity> findByMessageTimeGreaterThanEqual(ZonedDateTime messageTime, Sort sortcondition);
	public List<ContentTimeEntity> findByRowCreatedTimeGreaterThanEqual(ZonedDateTime rowCreatedTime, Sort sortcondition);
	public List<ContentTimeEntity> findByLastUpdatedTimeGreaterThanEqual(ZonedDateTime lastUpdatedTime, Sort sortcondition);
	
	
	/* BETWEEN TWO TIMESTAMPS */
	public List<ContentTimeEntity> findByMessageTimeBetween(ZonedDateTime fromMessageTime,ZonedDateTime toMessageTime, Sort sortcondition);
	public List<ContentTimeEntity> findByRowCreatedTimeBetween(ZonedDateTime fromRowCreatedTime,ZonedDateTime toRowCreatedTime, Sort sortcondition);
	public List<ContentTimeEntity> findByLastUpdatedTimeBetween(ZonedDateTime fromLastUpdatedTime,ZonedDateTime toLastUpdatedTime, Sort sortcondition);
	
}
