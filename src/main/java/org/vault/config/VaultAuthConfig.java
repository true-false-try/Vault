package org.vault.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.vault.dto.RedisResponseDto;
import org.vault.dto.VaultAuthDto;

import java.util.Map;

@Slf4j
@Getter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class VaultAuthConfig implements EnvironmentPostProcessor, Ordered {
    private RestTemplate restTemplateRedis;

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

    public static final int ORDER = ConfigDataEnvironmentPostProcessor.ORDER - 1;
    public static final String VAULT_URL = "http://localhost:8200";
    public static final String REDIS_URL = "http://localhost:8080/api/redis/getVaultAuth";

    private RestTemplate redisRestTemplate;
    private RestTemplate vaultRestTemplate;

    public VaultAuthConfig(
            @Qualifier("redisRestTemplate") RestTemplate redisRestTemplate,
            @Qualifier("vaultRestTemplate") RestTemplate vaultRestTemplate) {
        this.redisRestTemplate = redisRestTemplate;
        this.vaultRestTemplate = vaultRestTemplate;
    }



    @Override
    @SneakyThrows
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        RedisResponseDto redisResponseDto = getVaultAuthFromRedis();

        if (redisResponseDto == null) {
            log.error("Не удалось получить данные из Redis для unseal и rootToken.");
            return;
        }

        // Выполняем unseal для каждого ключа
        for (String unsealKey : redisResponseDto.getUnseals()) {
            unsealVault(unsealKey);
        }

        // Аутентификация в Vault через rootToken
        authenticateWithVault(redisResponseDto.getRootToken());
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000); // таймаут на подключение
        factory.setReadTimeout(3000);    // таймаут на чтение
        return factory;
    }

    private RedisResponseDto getVaultAuthFromRedis() throws HttpResponseException {
        try {
            return redisRestTemplate.getForObject(REDIS_URL, RedisResponseDto.class);
        } catch (Exception ex) {
            log.error("Exception with Redis response: ", ex);
            throw new HttpResponseException(400, ex.getMessage());
        }
    }

    private void unsealVault(String unsealKey) {
        String unsealUrl = VAULT_URL + "/v1/sys/unseal";
        try {
            Map<String, String> body = Map.of("key", unsealKey);
            vaultRestTemplate.postForEntity(unsealUrl, body, Void.class);
            log.info("Успешно выполнили unseal с ключом: {}", unsealKey);
        } catch (Exception e) {
            log.error("Ошибка при выполнении unseal: ", e);
        }
    }

    private void authenticateWithVault(String rootToken) {
        String loginUrl = VAULT_URL + "/v1/auth/token/lookup-self";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Vault-Token", rootToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            vaultRestTemplate.exchange(loginUrl, HttpMethod.GET, entity, String.class);
            log.info("Аутентификация через root-токен прошла успешно.");
        } catch (Exception e) {
            log.error("Ошибка при аутентификации в Vault: ", e);
        }
    }
}
