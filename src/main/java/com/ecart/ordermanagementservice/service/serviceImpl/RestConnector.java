package com.ecart.ordermanagementservice.service.serviceImpl;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestConnector {

  @Autowired
  private RestTemplate restTemplate;

  public ResponseEntity<String> serviceCall(String url, HttpMethod method) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<String>(headers);
    return restTemplate.exchange(url, method, entity, String.class);
  }
}
