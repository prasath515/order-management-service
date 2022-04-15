package com.ecart.ordermanagementservice.exception;

public class RestConnectorException extends RuntimeException{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public String response;
  
  public RestConnectorException(String response) {
    this.response = response;    
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

}
