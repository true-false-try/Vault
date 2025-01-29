package org.vault.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vault.dto.VaultAuthDto;
import org.vault.service.VaultCallRedisApiService;

import static org.vault.constant.EndpointsConstant.GET_VAULT_AUTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class VaultCallRedisApiServiceImpl implements VaultCallRedisApiService {

    private RestTemplate vaultRestTemplate;
    private HttpEntity httpEntity;

    public VaultCallRedisApiServiceImpl(@Qualifier("vaultRestTemplate") RestTemplate vaultRestTemplate, HttpEntity httpEntity) {
        this.vaultRestTemplate = vaultRestTemplate;
        this.httpEntity = httpEntity;
    }


    @Override
    public ResponseEntity<VaultAuthDto> getVaultKey() {
        log.info("into getVaultKey");
        return vaultRestTemplate.getForEntity(GET_VAULT_AUTH.getPath(), VaultAuthDto.class);
    }

    @Override
    public ResponseEntity<String> getMongoConfig(String pathMongo) {
        log.info("Service into getMongoConfig with pathTo : {}",pathMongo);
        return vaultRestTemplate.exchange(pathMongo, HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> getBotFatherConfig(String pathBotFather) {
        log.info("into getBotFatherConfig with pathTo : {}",pathBotFather);
        return vaultRestTemplate.exchange(pathBotFather, HttpMethod.GET, httpEntity, String.class);

    }

   private String buildUrlToMongoConfig(String path) {
        String newPath = "";
        log.info(newPath);
        return newPath;
    }

}
