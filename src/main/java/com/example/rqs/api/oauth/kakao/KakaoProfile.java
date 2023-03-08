package com.example.rqs.api.oauth.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoProfile {
    private Long id;
    private String connected_at;
    private Properties properties;
}
