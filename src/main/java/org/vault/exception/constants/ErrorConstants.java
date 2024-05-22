package org.vault.exception.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.vault.exception.annotation.CustomException;
@Getter
@CustomException
@AllArgsConstructor
public enum ErrorConstants {
    ERROR_READ_KEY("Didn't read key, because we don't have file.txt which we're trying parse");

    private final String exception;
}
