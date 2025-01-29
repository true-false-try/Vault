package org.vault.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisResponseDto {
    List<String> unseals;
    String rootToken;

    /*@Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Unseal {
        @JsonProperty("key-1")
        String keyOne;
        @JsonProperty("key-2")
        String keyTwo;
        @JsonProperty("key-3")
        String keyThree;
    }*/


}