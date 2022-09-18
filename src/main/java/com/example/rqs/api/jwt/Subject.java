package com.example.rqs.api.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    private Long memberId;

    private String email;

    private String nickname;

    private String role;
}
