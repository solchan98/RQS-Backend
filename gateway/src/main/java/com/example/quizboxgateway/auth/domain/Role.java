package com.example.quizboxgateway.auth.domain;

public enum Role {
    ADMIN,
    USER,
    GUEST // oauth 회원가입 진행 완료 후 USER로 변경
}
