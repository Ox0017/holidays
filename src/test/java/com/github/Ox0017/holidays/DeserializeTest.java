package com.github.Ox0017.holidays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Ox0017.holidays.model.dto.HolidayDto;
import com.github.Ox0017.holidays.model.dto.HolidaysDto;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class DeserializeTest extends TestSupport {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	static {
		OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
	}

	@Test
	public void testDeserialize_HolidaysDto() throws JsonProcessingException {
		// given
		final String json = this.readJson("holidays.json");

		// when
		final HolidaysDto holidaysDto = OBJECT_MAPPER.readValue(json, HolidaysDto.class);

		// then
		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
		assertThat(holidaysDto.getHolidays()).hasSize(20)
				.extracting(HolidayDto::getDate, HolidayDto::getName, HolidayDto::isAllStates)
				.containsExactly(
						tuple(LocalDate.parse("2022-01-01"), "Neujahr", true),
						tuple(LocalDate.parse("2022-01-06"), "Heilige Drei Könige", false),
						tuple(LocalDate.parse("2022-03-08"), "Internationaler Frauentag", false),
						tuple(LocalDate.parse("2022-04-15"), "Karfreitag", true),
						tuple(LocalDate.parse("2022-04-17"), "Ostersonntag", false),
						tuple(LocalDate.parse("2022-04-18"), "Ostermontag", true),
						tuple(LocalDate.parse("2022-05-01"), "Tag der Arbeit", true),
						tuple(LocalDate.parse("2022-05-26"), "Christi Himmelfahrt", true),
						tuple(LocalDate.parse("2022-06-05"), "Pfingstsonntag", false),
						tuple(LocalDate.parse("2022-06-06"), "Pfingstmontag", true),
						tuple(LocalDate.parse("2022-06-16"), "Fronleichnam", false),
						tuple(LocalDate.parse("2022-08-08"), "Augsburger Friedensfest", false),
						tuple(LocalDate.parse("2022-08-15"), "Mariä Himmelfahrt", false),
						tuple(LocalDate.parse("2022-09-20"), "Weltkindertag", false),
						tuple(LocalDate.parse("2022-10-03"), "Tag der deutschen Einheit", true),
						tuple(LocalDate.parse("2022-10-31"), "Reformationstag", false),
						tuple(LocalDate.parse("2022-11-01"), "Allerheiligen", false),
						tuple(LocalDate.parse("2022-11-16"), "Buß- und Bettag", false),
						tuple(LocalDate.parse("2022-12-25"), "1. Weihnachtstag", true),
						tuple(LocalDate.parse("2022-12-26"), "2. Weihnachtstag", true)
				);
	}

}
