package org.vault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {
    @Value("${vault.key-1}")
    private String keyOne;
    @Value("vault.key-2")
    private String keyTwo;
    @Value("${vault.root-token}")
    private String rootToken;

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.create("0.0.0.0", 8200);
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(rootToken);
    }
}
