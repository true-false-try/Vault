package org.vault.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.vault.config.VaultConfig;

import java.util.Objects;

@RestController
@RequestMapping("/vault")
@RequiredArgsConstructor
public class VaultController {

    private final VaultConfig vaultConfig;

    @GetMapping("/getMongoConfig")
    @ResponseBody
    public ResponseEntity<String> getMongoConfig() {
        String url = "http://0.0.0.0:8200/v1/database/mongodb";

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Vault-Token", "s.UC4iVrBRUGfzwafz5zmlYjiP");

        ResponseEntity<String> responseEntity = makeRequest(url, HttpMethod.GET, headers, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body("Error fetching MongoDB configuration from Vault");
        }
    }

    private <T> ResponseEntity<T> makeRequest(String url, HttpMethod method, HttpHeaders headers, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the headers in the HttpEntity
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Pass the HttpEntity with headers in the request
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);

        return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

}

