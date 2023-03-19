package com.falcon.persister.infra;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.falcon.persister.util.Formats;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeSerializer extends JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		return ZonedDateTime.parse(parser.getText(), Formats.CONTENT_TS_FORMAT);
	}
}