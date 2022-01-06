package com.github.Ox0017.holidays.impl;

import com.github.Ox0017.holidays.HolidayApiClient;
import com.github.Ox0017.holidays.TestSupport;
import com.github.Ox0017.holidays.model.dto.HolidaysDto;
import com.github.Ox0017.holidays.model.exception.HolidayApiException;
import com.github.Ox0017.holidays.model.exception.HolidayApiStatusCodeException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HolidayApiClientImplTest extends TestSupport {

	@Mock
	private HttpClient httpClient;

	private HolidayApiClient client;

	@Captor
	private ArgumentCaptor<HttpUriRequest> requestCaptor;

	@Before
	public void before() {
		this.client = new HolidayApiClientImpl(this.httpClient);
	}

	@Ignore
	@Test
	public void integrationTest() throws HolidayApiException {
		// given
		final HolidayApiClient client = new HolidayApiClientImpl();
		final Integer currentYear = LocalDate.now().getYear();

		// when
		final HolidaysDto holidaysDto = client.getHolidays(currentYear);

		// then
		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays() throws IOException, HolidayApiException {
		// given
		final String json = this.readJson("holidays.json");
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(json));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays();

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays_Null() throws IOException, HolidayApiException {
		// given
		final String json = this.readJson("holidays.json");
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(json));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays((Integer) null);

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays_Year() throws IOException, HolidayApiException {
		// given
		final String json = this.readJson("holidays.json");
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(json));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays(2022);

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/?years=2022", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays_Empty() throws IOException, HolidayApiException {
		// given
		final String json = this.readJson("holidays.json");
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(json));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays(emptyList());

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays_Years() throws IOException, HolidayApiException {
		// given
		final String json = this.readJson("holidays.json");
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(json));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays(asList(2022, 2024));

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/?years=2022%2C2024", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(holidaysDto);
		assertTrue(holidaysDto.isSuccess());
	}

	@Test
	public void testGetHolidays_BadResponse() throws IOException {
		// given
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk("asd"));

		// when
		HolidayApiException exception = null;
		try {
			this.client.getHolidays();
		}
		catch (final HolidayApiException ex) {
			exception = ex;
		}

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertThat(exception).hasMessage("Could not deserialize response");
	}

	@Test
	public void testGetHolidays_NoResponse() throws IOException, HolidayApiException {
		// given
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseOk(null));

		// when
		final HolidaysDto holidaysDto = this.client.getHolidays();

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNull(holidaysDto);
	}

	@Test
	public void testGetHolidays_Timeout() throws IOException {
		// given
		final ConnectTimeoutException timeoutException = new ConnectTimeoutException();
		when(this.httpClient.execute(this.requestCaptor.capture())).thenThrow(timeoutException);

		// when
		HolidayApiException exception = null;
		try {
			this.client.getHolidays();
		}
		catch (final HolidayApiException ex) {
			exception = ex;
		}

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(exception);
		assertEquals(timeoutException, exception.getCause());
	}

	@Test
	public void testGetHolidays_BadRequest() throws IOException, HolidayApiException {
		// given
		when(this.httpClient.execute(this.requestCaptor.capture())).thenReturn(httpResponseBadRequest());

		// when
		HolidayApiStatusCodeException exception = null;
		try {
			this.client.getHolidays();
		}
		catch (final HolidayApiStatusCodeException ex) {
			exception = ex;
		}

		// then
		assertEquals("GET", this.requestCaptor.getValue().getMethod());
		assertEquals("https://get.api-feiertage.de/", this.requestCaptor.getValue().getURI().toString());

		assertNotNull(exception);
		assertEquals(400, (exception.getStatusCode()));
	}

	private static HttpResponse httpResponseOk(final String body) {
		final HttpResponse response;
		if (body != null) {
			response = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK"));
			final BasicHttpEntity entity = new BasicHttpEntity();
			entity.setContent(IOUtils.toInputStream(body, StandardCharsets.UTF_8));
			response.setEntity(entity);
		}
		else {
			response = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 204, "NO CONTENT"));
		}

		return response;
	}

	private static HttpResponse httpResponseBadRequest() {
		return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 400, "Bad Request"));
	}

}
