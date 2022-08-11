package com.example.random_question_study.core.member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
