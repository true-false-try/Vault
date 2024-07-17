package org.vault.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
@Slf4j
@Getter
@Configuration
public class VaultAuthConfig extends AbstractVaultConfiguration {
    @Value("${vault.key-1.path}")
    private String keyOnePath;

    @Value("${vault.key-2.path}")
    private String keyTwoPath;

    @Value("${vault.root-token}")
    private String rootToken;

    @Value("${vault.unseal}")
    private String unsealUrl;

    @Value("${vault.host}")
    private String host;

    @Value("${vault.port}")
    private Integer port;

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.create(host, port);
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(rootToken);
    }

    @Bean
    public void unsealVault() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String keyOne = readUnseal(keyOnePath);
            String keyTwo = readUnseal(keyTwoPath);

            Map<String, Object> response1 = restTemplate.postForObject(unsealUrl, new UnsealRequest(keyOne), Map.class);
            log.info("{} {}",getClass().getName(), response1);

            Map<String, Object> response2 = restTemplate.postForObject(unsealUrl, new UnsealRequest(keyTwo), Map.class);
            log.info("{} {}",getClass().getName(), response2);

            if (response2 != null && response2.containsKey("sealed") && !(Boolean) response2.get("sealed")) {
                System.out.println("Vault is unsealed successfully.");
            } else {
                throw new RuntimeException("Failed to unseal Vault. Response: " + response2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error unsealing Vault", e);
        }
    }

    private String readUnseal(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class UnsealRequest {
        private final String key;
    }
}
