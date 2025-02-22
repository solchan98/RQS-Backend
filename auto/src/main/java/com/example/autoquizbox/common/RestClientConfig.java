package com.example.autoquizbox.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean(name = "googleClient")
    public RestClient getGoogleRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMinutes(2).toMillisPart());
        requestFactory.setReadTimeout(Duration.ofMinutes(2).toMillisPart());


        return RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultRequest(request -> request
                        .header("Content-Type", "application/json"))
                .requestFactory(requestFactory) // Set the timeout configuration
                .build();
    }

    @Bean(name = "coreServerClient")
    public RestClient getCoreServerRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMinutes(2).toMillisPart());
        requestFactory.setReadTimeout(Duration.ofMinutes(2).toMillisPart());


        return RestClient.builder()
                .baseUrl("http://localhost:8001")
                .defaultRequest(request -> request
                        .header("Content-Type", "application/json"))
                .requestFactory(requestFactory) // Set the timeout configuration
                .build();
    }
}