package com.github.Ox0017.holidays;

import com.github.Ox0017.holidays.model.dto.HolidaysDto;
import com.github.Ox0017.holidays.model.exception.HolidayApiException;

import java.util.Collection;

public interface HolidayApiClient {
	/**
	 * <p>Get all holidays</p>
	 *
	 * @return all available holidays
	 * @throws HolidayApiException on connection error, timeouts, deserialize error
	 *                             HolidayApiStatusCodeException on non 2xx statusCode
	 */
	HolidaysDto getHolidays() throws HolidayApiException;

	/**
	 * <p>Get holidays for a specific year</p>
	 *
	 * @param year of requested holidays
	 * @return all holidays for the requested year
	 * @throws HolidayApiException on connection error, timeouts, deserialize error
	 *                             HolidayApiStatusCodeException on non 2xx statusCode
	 */
	HolidaysDto getHolidays(final Integer year) throws HolidayApiException;

	/**
	 * <p>Get holidays for specific years</p>
	 *
	 * @param years of requested holidays
	 * @return all holidays for the requested years
	 * @throws HolidayApiException on connection error, timeouts, deserialize error
	 *                             HolidayApiStatusCodeException on non 2xx statusCode
	 */
	HolidaysDto getHolidays(final Collection<Integer> years) throws HolidayApiException;

}
