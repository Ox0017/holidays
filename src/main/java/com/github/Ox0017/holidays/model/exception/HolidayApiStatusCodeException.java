package com.github.Ox0017.holidays.model.exception;

public class HolidayApiStatusCodeException extends HolidayApiException {

	private final int statusCode;

	public HolidayApiStatusCodeException(final int statusCode) {
		super("Request failed with statusCode " + statusCode);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

}
