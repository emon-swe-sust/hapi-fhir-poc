package com.example.hapifhir;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APIContact {

    public static String createEncounterFreeSHR(String body,String id, String token, String client_id, String from) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5001/v2/patients/"+ id +"/encounters";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", token);
        headers.set("client_id", client_id);
        headers.set("From", from);

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        String response = responseEntity.getBody();

        return response;
    }
}
