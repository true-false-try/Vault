package org.vault.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@NoArgsConstructor
public class BuildUrlPath {
    private static String vaultAddress;
    private static  String vaultVersion;
    @Autowired
    public BuildUrlPath(@Value("${vault.addr}") String vaultAddress, @Value("${vault.version}") String vaultVersion) {
        BuildUrlPath.vaultAddress = vaultAddress;
        BuildUrlPath.vaultVersion = vaultVersion;
        if (vaultAddress == null) {
            throw new IllegalStateException("vaultAddr is null");
        }
    }

    public static String buildUrl(String path) {
        return vaultAddress.concat(vaultVersion).concat(path);
    }
}