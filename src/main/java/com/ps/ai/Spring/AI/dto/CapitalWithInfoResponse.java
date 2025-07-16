package com.ps.ai.Spring.AI.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CapitalWithInfoResponse(@JsonPropertyDescription String place,
                                      @JsonPropertyDescription Integer population,
                                      @JsonPropertyDescription String region,
                                      @JsonPropertyDescription String language,
                                      @JsonPropertyDescription String currency) {
}
