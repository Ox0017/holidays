package com.github.Ox0017.holidays.model.exception;

public class HolidayApiException extends Exception {

	public HolidayApiException(final String message) {
		super(message);
	}

	public HolidayApiException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public HolidayApiException(final Throwable cause) {
		super(cause);
	}

}
