package com.ps.ai.Spring.AI.function;

import com.ps.ai.Spring.AI.dto.WeatherQuestion;
import com.ps.ai.Spring.AI.dto.WeatherResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Component
public class WeatherServiceFunction implements Function<WeatherQuestion,WeatherResponse> {

    public static final String WEATHER_URL = "https://api.api-ninjas.com/v1/weather";


    @Value("${spring.ninja.api-key}")
    private String ninjaKey;


    @Override
    @Tool(description = "get weather for lat and lon")
    public WeatherResponse apply(WeatherQuestion question) {
        RestClient restClient = RestClient.builder()
                .baseUrl(WEATHER_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", ninjaKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        return restClient.get().uri(uriBuilder -> {
            System.out.println("Building URI for weather request: " + question);

            uriBuilder.queryParam("lat", question.lat());
            uriBuilder.queryParam("lon", question.lon());

            return uriBuilder.build();
        }).retrieve().body(WeatherResponse.class);
    }

}
