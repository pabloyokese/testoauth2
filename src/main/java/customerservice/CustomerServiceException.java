package customerservice;

import org.springframework.http.HttpStatus;

public class CustomerServiceException extends RuntimeException {

	private static final long serialVersionUID = 8631466448974323851L;

	private final HttpStatus httpStatus;
	private final String message;

	public CustomerServiceException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public String getMessage() {
		return message;
	}
}
