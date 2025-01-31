package org.example.quizbox.auth.config;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    /*
     * TODO: 상세 설정 추가 필요
     * */
    @Bean(name = "nodeClient")
    public RestClient getNodeServerRestClient() {



        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(30));

        return RestClient.builder()
                .baseUrl("http://localhost:5001")
                .defaultRequest(request -> request
                        .header("Authorization", "Bearer hello")
                        .header("Content-Type", "application/json"))
                .requestFactory(ClientHttpRequestFactories.get(clientHttpRequestFactorySettings))
                .build();
    }
}
