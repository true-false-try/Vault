package org.vault.config.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.vault.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.vault.config.VaultAuthConfig;

import javax.annotation.PostConstruct;
@Getter
@Configuration
@RequiredArgsConstructor
public class VaultRestTemplateConfig {
    private final VaultAuthConfig vaultAuthConfig;

    private final static String VAULT_TOKEN_NAME = "X-Vault-Token";

    @Bean
    public RestTemplate vaultRestTemplate() {
        return restTemplateBuilder()
                .endpoint(vaultAuthConfig.vaultEndpoint())
                .build();
    }

    @PostConstruct
    private RestTemplateBuilder restTemplateBuilder() {
        return RestTemplateBuilder.builder();
    }

    @Bean
    public HttpEntity httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(VAULT_TOKEN_NAME, vaultAuthConfig.getRootToken());
        return new HttpEntity(headers);
    }
}
