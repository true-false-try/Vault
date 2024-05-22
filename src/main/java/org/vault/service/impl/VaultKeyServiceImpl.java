package org.vault.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vault.exception.KeyValueReaderException;
import org.vault.service.VaultKeyService;

import java.io.*;

@Slf4j
@Service
public class VaultKeyServiceImpl implements VaultKeyService {

    @Override
    public String getKey() {
        String key;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/vault/keys/root-token"));
            key = reader.readLine();
            log.info("The key got success : " + key);

        } catch (IOException ex) {
            log.error(ex.getMessage() + " | " + new KeyValueReaderException().getMessage());
            return new KeyValueReaderException().getMessage();
        }

        return key;
    }
}
