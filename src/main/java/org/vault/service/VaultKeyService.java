package org.vault.service;

import org.vault.exception.KeyValueReaderException;

import java.io.IOException;

public interface VaultKeyService {
    String getKey() throws IOException, KeyValueReaderException;
}
