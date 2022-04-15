package com.ecart.ordermanagementservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.ecart.ordermanagementservice.model.CustomResponse;
import com.google.gson.Gson;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomResponse> handleException(Exception ex) {
    CustomResponse response = new CustomResponse("error", ex.getLocalizedMessage());
    return new ResponseEntity<CustomResponse>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<CustomResponse> handleCustomException(CustomException ex) {
    return new ResponseEntity<CustomResponse>(new CustomResponse(ex.getStatus(), ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(RestConnectorException.class)
  public ResponseEntity<CustomResponse> handleRestConnectorException(RestConnectorException ex) {
    CustomResponse response = new Gson().fromJson(ex.getResponse(), CustomResponse.class);
    return new ResponseEntity<CustomResponse>(response,HttpStatus.BAD_REQUEST);
  }

}
