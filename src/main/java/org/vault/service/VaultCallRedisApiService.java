package org.vault.service;

import org.springframework.http.ResponseEntity;
import org.vault.dto.VaultAuthDto;

public interface VaultCallRedisApiService {
    ResponseEntity<VaultAuthDto> getVaultKey();
    ResponseEntity<String> getMongoConfig(String pathMongo);


}
