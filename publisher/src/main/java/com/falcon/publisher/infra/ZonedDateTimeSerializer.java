package com.falcon.publisher.infra;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.falcon.publisher.util.Formats;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	@Override
	public void serialize(ZonedDateTime zonedTime, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeString(zonedTime.format(Formats.CONTENT_TS_FORMAT).toString());
	}

}
