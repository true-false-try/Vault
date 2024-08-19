package org.vault.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class VaultAuthDto {
    private Map<String, String> unseals;
    private String rootToken;
}
