package org.vault.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.vault.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final VaultAuthConfig vaultAuthConfig;
    private final static String VAULT_TOKEN_NAME = "X-Vault-Token";

    @Bean
    public RestTemplate defaultRestTemplate() {
        return restTemplateBuilder()
                .endpoint(vaultAuthConfig.vaultEndpoint())
                .build();
    }

    @Bean
    public HttpEntity httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(VAULT_TOKEN_NAME, vaultAuthConfig.getRootToken());
        return new HttpEntity(headers);
    }

    @PostConstruct
    private RestTemplateBuilder restTemplateBuilder() {
        return RestTemplateBuilder.builder();
    }


    public String getVaultAddressWithVersion() {
        return vaultAuthConfig.getVaultAddressWithVersion();
    }

}
