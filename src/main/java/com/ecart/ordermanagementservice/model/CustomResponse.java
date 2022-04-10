package com.ecart.ordermanagementservice.model;

public class CustomResponse {
	
	protected String status;
	
	protected String message;
	
	public CustomResponse() {	
	}
	
	public CustomResponse(String message) {
		this.message = message;		
	}
	
	public CustomResponse(String status, String message) {
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

	public void setMessage(String message) {
		this.message = message;
	}
	
}
