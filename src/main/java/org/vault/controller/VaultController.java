package org.vault.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.vault.config.VaultAuthConfig;
import org.vault.dto.VaultAuthDto;
import org.vault.util.BuildUrlPath;

import static org.vault.constant.EndpointsConstant.GET_VAULT_AUTH;

@RestController
@RequestMapping("api/vault")
@RequiredArgsConstructor
public class VaultController {

    private final RestTemplate restTemplate;
    private final VaultAuthConfig vaultAuthConfig;

    @GetMapping("getMongoConfig")
    @ResponseBody
    public ResponseEntity<String> getMongoConfig(@RequestParam(name = "path-mongo") String pathMongo) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Vault-Token", vaultAuthConfig.getRootToken());

        ResponseEntity<String> responseEntity = makeRequest(BuildUrlPath.buildUrl(pathMongo), HttpMethod.GET, headers, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body("Error fetching MongoDB configuration from Vault");
        }
    }

    @GetMapping("getKey")
    @ResponseBody
    public ResponseEntity<VaultAuthDto> getVaultKey() {
      return restTemplate.getForEntity(GET_VAULT_AUTH.getPath(), VaultAuthDto.class);
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

