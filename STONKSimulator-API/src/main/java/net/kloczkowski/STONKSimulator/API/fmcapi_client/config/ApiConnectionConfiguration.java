package net.kloczkowski.STONKSimulator.API.fmcapi_client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class ApiConnectionConfiguration {

    public static final String BASE_URL = "https://financialmodelingprep.com/api/v3";
    public static final String API_KEY = "0c6f2662564ae2d10f833e44c7e4d918";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .rootUri(BASE_URL)
                .build();
    }
}
