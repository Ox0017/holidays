package com.github.Ox0017.holidays.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Ox0017.holidays.HolidayApiClient;
import com.github.Ox0017.holidays.model.dto.HolidaysDto;
import com.github.Ox0017.holidays.model.exception.HolidayApiException;
import com.github.Ox0017.holidays.model.exception.HolidayApiStatusCodeException;
import com.github.Ox0017.holidays.model.client.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class HolidayApiClientImpl implements HolidayApiClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HolidayApiClientImpl.class);

	private final String baseUrl;

	private final HttpClient httpClient;

	private final ObjectMapper objectMapper;

	/**
	 * Initializes a default API client (2000ms timeout) and base url
	 */
	public HolidayApiClientImpl() {
		this(defaultHttpClientBuilder()
				.disableAuthCaching()
				.disableAutomaticRetries()
				.disableRedirectHandling()
				.build());
	}

	private static HttpClientBuilder defaultHttpClientBuilder() {
		final int timeoutMillis = 2000;
		final RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder.setConnectTimeout(timeoutMillis);
		requestBuilder.setConnectionRequestTimeout(timeoutMillis);
		requestBuilder.setSocketTimeout(timeoutMillis);

		final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
		return httpClientBuilder;
	}

	/**
	 * Initializes the API client with default base url
	 *
	 * @param httpClient client to be used for all requests
	 */
	public HolidayApiClientImpl(final HttpClient httpClient) {
		this(httpClient, "https://get.api-feiertage.de");
	}

	/**
	 * Initialize the API client with the provided httpClient and with a custom base url
	 *
	 * @param httpClient client to be used for all requests
	 * @param baseUrl    base url for all requests
	 */
	public HolidayApiClientImpl(final HttpClient httpClient, final String baseUrl) {
		if (baseUrl == null) {
			throw new IllegalArgumentException("BaseUrl is null");
		}
		if (httpClient == null) {
			throw new IllegalArgumentException("HttpClient is null");
		}

		this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
		this.httpClient = httpClient;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
	}

	@Override
	public HolidaysDto getHolidays() throws HolidayApiException {
		final HttpUriRequest request = RequestBuilder.get(this.baseUrl).build();
		final Response response = this.executeRequest(request);
		return this.deserializeResponse(response, HolidaysDto.class);
	}

	@Override
	public HolidaysDto getHolidays(final Integer year) throws HolidayApiException {
		if (year == null) {
			return this.getHolidays();
		}

		return this.getHolidays(singletonList(year));
	}

	@Override
	public HolidaysDto getHolidays(final Collection<Integer> years) throws HolidayApiException {
		if (years == null || years.isEmpty()) {
			return this.getHolidays();
		}

		final HttpUriRequest request = RequestBuilder.get(this.baseUrl)
				.addParameter("years", years.stream().map(String::valueOf).collect(Collectors.joining(",")))
				.build();
		final Response response = this.executeRequest(request);
		return this.deserializeResponse(response, HolidaysDto.class);
	}

	private Response executeRequest(final HttpUriRequest request) throws HolidayApiException {
		LOGGER.trace("Executing {} request for {}", request.getMethod(), request.getURI().toString());

		final Response response = new Response();
		try {
			final long timestamp = System.currentTimeMillis();
			final HttpResponse httpResponse = this.httpClient.execute(request);
			LOGGER.debug("Request execution {} {} took {} ms", request.getMethod(), request.getURI().toString(), System.currentTimeMillis() - timestamp);

			final StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine != null) {
				response.setStatusCode(statusLine.getStatusCode());
				response.setStatusPhrase(statusLine.getReasonPhrase());
			}

			if (httpResponse.getEntity() != null && httpResponse.getEntity().getContent() != null) {
				response.setResponseBody(IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
				LOGGER.trace("Server Response {} {} with body: {}", response.getStatusCode(), response.getStatusPhrase(), response.getResponseBody());
			}
			else {
				LOGGER.trace("Server Response {} {} without body", response.getStatusCode(), response.getStatusPhrase());
			}
			if (httpResponse instanceof CloseableHttpResponse) {
				((CloseableHttpResponse) httpResponse).close();
			}
		}
		catch (final Exception ex) {
			throw new HolidayApiException(ex);
		}

		return response;
	}

	private <T> T deserializeResponse(final Response response, final Class<T> clazz) throws HolidayApiException {
		if (response == null) {
			return null;
		}

		final String body = response.getResponseBody();
		if (response.is2xxSuccessful()) {
			if (body != null && !body.isEmpty()) {
				return this.deserializeResponse(body, clazz);
			}
			else {
				LOGGER.warn("No response body to deserialize for {}", clazz.getSimpleName());
			}
		}
		else if (response.getStatusCode() != null) {
			LOGGER.debug("Server returned statusCode {}: {}", response.getStatusCode(), response.getStatusPhrase());
			throw new HolidayApiStatusCodeException(response.getStatusCode());
		}
		return null;
	}

	private <T> T deserializeResponse(final String value, final Class<T> clazz) throws HolidayApiException {
		if (value == null || clazz == null) {
			return null;
		}

		try {
			return this.objectMapper.readValue(value, clazz);
		}
		catch (final JsonProcessingException ex) {
			throw new HolidayApiException("Could not deserialize response", ex);
		}
	}

}
