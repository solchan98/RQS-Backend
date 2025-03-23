package com.example.quizboxgateway.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean(name = "coreClient")
    public RestClient getCoreRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMinutes(2).toMillisPart());
        requestFactory.setReadTimeout(Duration.ofMinutes(2).toMillisPart());


        return RestClient.builder()
                .baseUrl("http://localhost:8001")
                .defaultRequest(request -> request
                        .header("Content-Type", "application/json"))
                .requestFactory(requestFactory)
                .build();
    }

    @Bean(name = "autoQuizClient")
    public RestClient getAutoQuizRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMinutes(10).toMillisPart());
        requestFactory.setReadTimeout(Duration.ofMinutes(10).toMillisPart());


        return RestClient.builder()
                .baseUrl("http://localhost:8002")
                .defaultRequest(request -> request
                        .header("Content-Type", "application/json"))
                .requestFactory(requestFactory)
                .build();
    }

    @Bean(name = "kakaoOauthClient")
    public RestClient getKakaoOauthRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMinutes(2).toMillisPart());
        requestFactory.setReadTimeout(Duration.ofMinutes(2).toMillisPart());


        return RestClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultRequest(request -> request
                        .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"))
                .requestFactory(requestFactory)
                .build();
    }


}