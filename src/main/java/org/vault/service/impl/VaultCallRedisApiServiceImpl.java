package org.vault.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vault.config.rest.RedisRestTemplateConfig;
import org.vault.config.rest.VaultRestTemplateConfig;
import org.vault.dto.VaultAuthDto;
import org.vault.service.VaultCallRedisApiService;

import static org.vault.constant.EndpointsConstant.GET_VAULT_AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaultCallRedisApiServiceImpl implements VaultCallRedisApiService {
    private final VaultRestTemplateConfig restTemplate;
    private final HttpEntity httpEntity;

    @Override
    public ResponseEntity<VaultAuthDto> getVaultKey() {
        log.info("into getVaultKey");
        return restTemplate.vaultRestTemplate().getForEntity(GET_VAULT_AUTH.getPath(), VaultAuthDto.class);
    }

    @Override
    public ResponseEntity<String> getMongoConfig(String pathMongo) {
        log.info("Service into getMongoConfig with pathTo : {}",pathMongo);
        return restTemplate.vaultRestTemplate().exchange(pathMongo, HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> getBotFatherConfig(String pathBotFather) {
        log.info("into getBotFatherConfig with pathTo : {}",pathBotFather);
        return restTemplate.vaultRestTemplate().exchange(pathBotFather, HttpMethod.GET, httpEntity, String.class);

    }

   private String buildUrlToMongoConfig(String path) {
        String newPath = restTemplate.getVaultAuthConfig().getVaultAddressWithVersion().concat(path);
        log.info(newPath);
        return newPath;
    }

}
