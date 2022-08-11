package com.example.rqs.core.space;

import com.example.rqs.core.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SpaceMember {

    @Id
    private String spaceMemberId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    @ManyToOne
    private Space space;

    private LocalDateTime joinedAt;

    private String role;
}
