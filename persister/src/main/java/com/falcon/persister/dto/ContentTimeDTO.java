package com.falcon.persister.dto;

import java.time.ZonedDateTime;

import com.falcon.persister.infra.ZonedDateTimeDeSerializer;
import com.falcon.persister.infra.ZonedDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentTimeDTO {

	private int messageId;

	private String content;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeSerializer.class)
	private ZonedDateTime messageTime;

	private int longest_palindrome_size;
	
	
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeSerializer.class)
	private ZonedDateTime rowCreatedTime;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeSerializer.class)
	private ZonedDateTime lastUpdatedTime;
	

}
