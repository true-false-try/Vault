package org.vault.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class BuildUrlPath {
    private static String vaultAddr;
    private static final String VAULT_VERSION_V1 = "/v1";

    @Autowired
    public BuildUrlPath(@Value("${vault.addr}") String vaultAddr) {
        BuildUrlPath.vaultAddr = vaultAddr;
        if (vaultAddr == null) {
            throw new IllegalStateException("vaultAddr is null");
        }
    }

    public static String buildUrl(String path) {
        return vaultAddr.concat(VAULT_VERSION_V1).concat(path);
    }
}