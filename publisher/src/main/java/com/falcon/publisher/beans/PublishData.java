package com.falcon.publisher.beans;

import java.time.ZonedDateTime;

import com.falcon.publisher.infra.ZonedDateTimeDeSerializer;
import com.falcon.publisher.infra.ZonedDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishData {

	@ApiModelProperty(example = "Sample Text")
	private String content;

	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonDeserialize(using = ZonedDateTimeDeSerializer.class)
	@ApiModelProperty(example = "2021-05-20 20:53:12+0530")
	private ZonedDateTime timestamp;

}
