package com.github.Ox0017.holidays.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidaysDto {

	@JsonProperty("status")
	private String status;

	@JsonProperty("feiertage")
	private List<HolidayDto> holidays;

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return "success".equalsIgnoreCase(this.status);
	}

	public List<HolidayDto> getHolidays() {
		return this.holidays;
	}

	public void setHolidays(final List<HolidayDto> holidays) {
		this.holidays = holidays;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("HolidaysDto [");
		sb.append("status='").append(this.status).append('\'');
		sb.append(", holidays=").append(this.holidays);
		sb.append(']');
		return sb.toString();
	}

}
