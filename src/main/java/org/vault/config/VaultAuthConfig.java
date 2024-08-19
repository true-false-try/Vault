package org.vault.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.RestTemplateBuilder;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.client.RestTemplate;
import org.vault.dto.RedisResponseDto;

import javax.annotation.PostConstruct;
import java.net.URI;

@Slf4j
@Getter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class VaultAuthConfig extends AbstractVaultConfiguration {

    @Autowired
    @Qualifier(value = "redisRestTemplate")
    private RestTemplate restTemplate;

    @Value("${vault.version}")
    private String vaultAddressWithVersion;

    @Value("${vault.addr}")
    private String uri;

    @Value("${vault.unseal}")
    private String unsealUrl;
    @Value("${vault.host}")
    private String host;
    @Value("${vault.port}")
    private Integer port;

    private String keyOne;
    private String keyTwo;
    private String keyThree;
    private String rootToken;

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(URI.create(uri));
    }

    @PostConstruct
    public void initializeVaultAuthentication() {
        RedisResponseDto redisResponseDto = callRedis();
        if (redisResponseDto != null) {
            rootToken = redisResponseDto.getRootToken();
            log.info("Vault root token retrieved from Redis API");

            keyOne = redisResponseDto.getUnseals().getKeyOne();
            keyTwo = redisResponseDto.getUnseals().getKeyTwo();
            keyThree = redisResponseDto.getUnseals().getKeyThree();

            // Initialize the Vault authentication using the root token
            clientAuthentication();
        } else {
            throw new RuntimeException("Failed to fetch keys and root token from Redis API");
        }
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        if (rootToken == null) {
            throw new IllegalStateException("Root token is not set. Ensure Redis has been called first.");
        }
        return new TokenAuthentication(rootToken);
    }

    public RedisResponseDto callRedis() {
        String url = "http://localhost:8099/api/redis/getVaultAuth";
        return restTemplate.getForObject(url, RedisResponseDto.class);
    }

}
