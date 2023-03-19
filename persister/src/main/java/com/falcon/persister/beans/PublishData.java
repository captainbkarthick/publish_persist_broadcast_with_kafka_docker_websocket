package com.falcon.persister.beans;

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
public class PublishData {

	private String content;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeSerializer.class)	
	private ZonedDateTime timestamp;

}
