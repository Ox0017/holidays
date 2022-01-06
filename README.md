# holidays
German Holidays API

Simple API client against https://get.api-feiertage.de

### Usage

Example:

	final HolidayApiClient client = new HolidayApiClientImpl();
	final HolidaysDto holidaysDto = client.getHolidays(2022);
