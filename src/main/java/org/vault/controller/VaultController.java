package org.vault.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.vault.dto.VaultAuthDto;
import org.vault.service.VaultCallRedisApiService;


@RestController
@RequestMapping("api/vault")
@RequiredArgsConstructor
public class VaultController {

    private final VaultCallRedisApiService vaultCallRedisApiService;

    @GetMapping("getMongoConfig")
    @ResponseBody
    public ResponseEntity<String> getMongoConfig(@RequestParam(name = "path-mongo") String pathMongo) {
        return vaultCallRedisApiService.getMongoConfig(pathMongo);
    }

    @GetMapping("getVaultKeys")
    @ResponseBody
    public ResponseEntity<VaultAuthDto> getVaultKey() {
      return vaultCallRedisApiService.getVaultKey();
    }

    @GetMapping("getBotFatherCredentials")
    @ResponseBody
    public ResponseEntity<String> getBotFatherCredentials(@RequestParam(name = "path-bot-father") String pathBotFather) {
        return vaultCallRedisApiService.getBotFatherConfig(pathBotFather);
    }
}

