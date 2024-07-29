package org.vault.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum EndpointsConstant {
    GET_VAULT_AUTH("http://localhost:8099/api/redis/getVaultAuth");

    private final String path;

}
