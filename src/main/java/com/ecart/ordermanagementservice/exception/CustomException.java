package com.ecart.ordermanagementservice.exception;

public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String status;

	public String message;
	
	public CustomException(String status,String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

}
