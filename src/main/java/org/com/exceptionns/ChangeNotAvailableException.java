package org.com.exceptionns;

public class ChangeNotAvailableException extends RuntimeException {
	private String message;

	public ChangeNotAvailableException(String string) {
		this.message = string;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
