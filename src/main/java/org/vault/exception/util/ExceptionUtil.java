package org.vault.exception.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.vault.exception.constants.ErrorConstants;
import org.vault.exception.annotation.CustomException;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtil {
    private static final String EXCEPTION_DEFAULT = "exception";

    public static String getJsonExceptionMessage(ErrorConstants errorConstants) {
        try {
            if (ErrorConstants.class.isAnnotationPresent(CustomException.class)) {
                Map<String, String> exceptionMap = new HashMap<>();
                exceptionMap.put(EXCEPTION_DEFAULT, errorConstants.getException());

                // create json
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(exceptionMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
