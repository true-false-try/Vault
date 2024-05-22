package org.vault.exception;

import org.vault.exception.util.ExceptionUtil;

import static org.vault.exception.constants.ErrorConstants.*;

public class KeyValueReaderException extends Exception {

    public KeyValueReaderException(){
        super(ExceptionUtil.getJsonExceptionMessage(ERROR_READ_KEY));
    }
}
