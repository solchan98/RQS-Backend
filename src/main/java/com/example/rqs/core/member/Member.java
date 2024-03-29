package com.example.rqs.core.member;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private String description;

    private String avatar;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected Member() {}

    private Member(String email, String password, String nickname, String avatar) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Member newMember(String email, String password, String nickname) {
        return new Member(email, password, nickname, "");
    }

    public static Member newMember(String email, String password, String nickname, String avatar) {
        return new Member(email, password, nickname, avatar);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatar(String avatar) {
        this.avatar = avatar;
        this.updatedAt = LocalDateTime.now();
    }
}
