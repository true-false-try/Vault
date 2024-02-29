package org.vault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class VaultApplication {
    public static void main(String[] args) {
        SpringApplication.run(VaultApplication.class, args);
    }
}
