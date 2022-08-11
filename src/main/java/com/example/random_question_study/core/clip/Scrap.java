package com.example.random_question_study.core.clip;

import com.example.random_question_study.core.member.Member;
import com.example.random_question_study.core.space.Space;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(ScrapId.class)
public class Scrap {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "spaceId")
    private Space space;

    private LocalDateTime createdAt;
}
