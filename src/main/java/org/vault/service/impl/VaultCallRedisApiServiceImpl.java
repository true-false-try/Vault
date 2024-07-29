package org.vault.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vault.config.RestTemplateConfig;
import org.vault.dto.VaultAuthDto;
import org.vault.service.VaultCallRedisApiService;

import static org.vault.constant.EndpointsConstant.GET_VAULT_AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaultCallRedisApiServiceImpl implements VaultCallRedisApiService {
    private final RestTemplateConfig restTemplateConfig;
    private final HttpEntity httpEntity;

    @Override
    public ResponseEntity<VaultAuthDto> getVaultKey() {
        log.info("into getVaultKey");
        return restTemplateConfig.defaultRestTemplate().getForEntity(GET_VAULT_AUTH.getPath(), VaultAuthDto.class);
    }

    @Override
    public ResponseEntity<String> getMongoConfig(String pathMongo) {
        log.info("into getMongoConfig with pathTo : {}",pathMongo);
        return restTemplateConfig.defaultRestTemplate().exchange(buildUrlToMongoConfig(pathMongo), HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> getBotFatherConfig(String pathBotFather) {
        log.info("into getBotFatherConfig with pathTo : {}",pathBotFather);
        return restTemplateConfig.defaultRestTemplate().exchange(buildUrlToMongoConfig(pathBotFather), HttpMethod.GET, httpEntity, String.class);

    }

    private String buildUrlToMongoConfig(String path) {
        String newPath = restTemplateConfig.getVaultAddressWithVersion().concat(path);
        log.info(newPath);
        return newPath;
    }

}
