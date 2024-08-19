package org.vault.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisResponseDto {
    Unseals unseals;
    String rootToken;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Unseals {
        @JsonProperty("key-1")
        String keyOne;
        @JsonProperty("key-2")
        String keyTwo;
        @JsonProperty("key-3")
        String keyThree;
    }


}
