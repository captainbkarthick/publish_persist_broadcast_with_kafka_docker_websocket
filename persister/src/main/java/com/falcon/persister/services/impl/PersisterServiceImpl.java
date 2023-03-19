package com.falcon.persister.services.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.falcon.persister.beans.PublishData;
import com.falcon.persister.dto.ContentTimeDTO;
import com.falcon.persister.entities.ContentTimeEntity;
import com.falcon.persister.repositories.ContentTimeRepository;
import com.falcon.persister.services.PersisterService;
import com.falcon.persister.util.Commons;
import com.falcon.persister.util.Constants;

@Service
public class PersisterServiceImpl implements PersisterService {

	@Autowired
	private ContentTimeRepository contentTimeRepository;

	@Override
	public boolean handleConsumedData(PublishData publishData) {
		ContentTimeEntity ctEntity = ContentTimeEntity.builder()
				.content(publishData.getContent())
				.messageTime(publishData.getTimestamp()).build();
		contentTimeRepository.save(ctEntity);
		return true;
	}

	@Override
	public Optional<List<ContentTimeDTO>> getAllMessagesSorted(String sortField, String sortDirection) {		
		return Optional.of(buildMessages(contentTimeRepository.findAll(Sort.by(getSortOrder(sortField, sortDirection)))));
	}
	
	@Override
	public Optional<List<ContentTimeDTO>> getRecordsForField(String requestFieldName, String fieldValue, String sortField, String sortDirection) {
		Order sortCondition = getSortOrder(sortField, sortDirection);
		ZonedDateTime time=null;
		
		if(requestFieldName.endsWith("Time")) {		
			time = getZonedDateTime(fieldValue);
		}
		List<ContentTimeEntity> messageList = null;
		
		if(Constants.MESSAGE_ID.equals(requestFieldName)) {
			messageList=contentTimeRepository.findByMessageId(Integer.parseInt(fieldValue), Sort.by(sortCondition));
		}else if(Constants.CONTENT.equals(requestFieldName)) {
			messageList=contentTimeRepository.findByContent(fieldValue, Sort.by(sortCondition));
		}else if(Constants.MESSAGE_TIME.equals(requestFieldName)) {
			messageList=contentTimeRepository.findByMessageTime(time, Sort.by(sortCondition));
		}else if(Constants.ROW_CREATED_TIME.equals(requestFieldName)) {
			messageList=contentTimeRepository.findByRowCreatedTime(time, Sort.by(sortCondition));
		}else if(Constants.LAST_UPDATED_TIME.equals(requestFieldName)) {
			messageList=contentTimeRepository.findByLastUpdatedTime(time, Sort.by(sortCondition));
		}
		
		return ObjectUtils.isEmpty(messageList) ? 
				Optional.empty() : 
					Optional.of(buildMessages(messageList));
	}
	
	@Override
	public Optional<List<ContentTimeDTO>> getAllMessagesByDate(String requestFieldName, String fromDateStr, String toDateStr, String sortField, String sortDirection) {
		Order sortCondition = getSortOrder(sortField, sortDirection);
		ZonedDateTime fromDate = getZonedDateTime(fromDateStr);
		ZonedDateTime toDate = getZonedDateTime(toDateStr);
		
		List<ContentTimeEntity> messageList = null;
		
		if(Constants.MESSAGE_TIME.equals(requestFieldName)) {
			if (!ObjectUtils.isEmpty(fromDate) && !ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByMessageTimeBetween(fromDate, toDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(fromDate)) {
				messageList = contentTimeRepository.findByMessageTimeGreaterThanEqual(fromDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByMessageTimeLessThanEqual(toDate, Sort.by(sortCondition));
			}
			 
		} else  if (Constants.ROW_CREATED_TIME.equals(requestFieldName)){
			
			if (!ObjectUtils.isEmpty(fromDate) && !ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByRowCreatedTimeBetween(fromDate, toDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(fromDate)) {
				messageList = contentTimeRepository.findByRowCreatedTimeGreaterThanEqual(fromDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByRowCreatedTimeLessThanEqual(toDate, Sort.by(sortCondition));
			}
		} else if (Constants.LAST_UPDATED_TIME.equals(requestFieldName)) {
			
			if (!ObjectUtils.isEmpty(fromDate) && !ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByLastUpdatedTimeBetween(fromDate, toDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(fromDate)) {
				messageList = contentTimeRepository.findByLastUpdatedTimeGreaterThanEqual(fromDate, Sort.by(sortCondition));
				
			} else if (!ObjectUtils.isEmpty(toDate)) {
				messageList = contentTimeRepository.findByLastUpdatedTimeLessThanEqual(toDate, Sort.by(sortCondition));
			}
		}
		
		return ObjectUtils.isEmpty(messageList) ? 
				Optional.empty() : 
					Optional.of(buildMessages(messageList));
		
	}
	
	private ZonedDateTime getZonedDateTime(String dateString) {
		if (StringUtils.hasText(dateString))
			return Commons.getZonedDateTime(dateString);
		else
			return null;
	}
	
	private Order getSortOrder(String sortField,String sortDirection) {
		return new Order(Sort.Direction.fromString(sortDirection),Constants.TBL_DATA_ENTITY_NAMES.get(sortField));
	}
	
	private List<ContentTimeDTO> buildMessages(List<ContentTimeEntity> messages){
		return messages.stream().map(this::buildMessage).collect(Collectors.toList());
	}
	
	private ContentTimeDTO buildMessage(ContentTimeEntity message) {
		return ContentTimeDTO.builder()
				.messageId(message.getMessageId())
				.content(message.getContent())
				.messageTime(message.getMessageTime())
				.longest_palindrome_size(getLongestPalindromeLength(message.getContent()))
				.rowCreatedTime(message.getRowCreatedTime())
				.lastUpdatedTime(message.getLastUpdatedTime())
				.build();		
	}
	
	@SuppressWarnings("unused")
	private int getLongestPalindromeLength(String message) {
	    int strLength = message.length();	 
	    int maxLength = 1, start = 0;	 
	    
	    for (int i = 0; i < strLength; i++) {
	        for (int j = i; j < strLength; j++) {
	            int flag = 1;
	 
	            // Check palindrome
	            for (int k = 0; k < (j - i + 1) / 2; k++)
	                if (message.charAt(i + k) != message.charAt(j - k))
	                    flag = 0;
	 
	            // Palindrome
	            if (flag!=0 && (j - i + 1) > maxLength) {
	                start = i;
	                maxLength = j - i + 1;
	            }
	        }
	    }  
	    return maxLength;			
	}	
}
