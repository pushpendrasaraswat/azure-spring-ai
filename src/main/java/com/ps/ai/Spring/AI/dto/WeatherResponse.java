package com.ps.ai.Spring.AI.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record WeatherResponse(@JsonPropertyDescription BigDecimal windSpeed,
                              @JsonPropertyDescription Integer windDegrees,
                              @JsonPropertyDescription  Integer temp,
                              @JsonPropertyDescription Integer humidity,
                              @JsonPropertyDescription  String sunset,
                              @JsonPropertyDescription  String sunrise,
                              @JsonPropertyDescription  Integer minTemp,
                              @JsonPropertyDescription Integer cloudPct,
                              @JsonPropertyDescription  Integer feelsLike,
                              @JsonPropertyDescription  Integer maxTemp) {
}
