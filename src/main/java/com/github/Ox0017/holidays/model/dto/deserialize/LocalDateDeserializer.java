package com.github.Ox0017.holidays.model.dto.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
		try {
			return LocalDate.parse(jsonParser.getValueAsString());
		}
		catch (final DateTimeParseException ex) {
			// no-op
		}
		return null;
	}

}
