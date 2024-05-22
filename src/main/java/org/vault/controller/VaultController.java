package org.vault.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vault.exception.KeyValueReaderException;
import org.vault.service.VaultKeyService;
import org.vault.util.BuildUrlPath;

import java.io.IOException;

@RestController
@RequestMapping("/vault")
@RequiredArgsConstructor
public class VaultController {

    private final VaultKeyService vaultKeyService;

    @GetMapping("/getMongoConfig")
    @ResponseBody
    public ResponseEntity<String> getMongoConfig(@RequestParam(name = "token-mongo") String tokenMongo,
                                                 @RequestParam(name = "path-mongo") String pathMongo) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Vault-Token", tokenMongo);

        ResponseEntity<String> responseEntity = makeRequest(BuildUrlPath.buildUrl(pathMongo), HttpMethod.GET, headers, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body("Error fetching MongoDB configuration from Vault");
        }
    }

    @GetMapping("/getKey")
    @ResponseBody
    public String getVaultKey() throws KeyValueReaderException, IOException {
      return vaultKeyService.getKey();
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

