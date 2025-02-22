package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizPackMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizPackMemberId;

    @Column(name = "member_id")
    private long memberId;

    @Enumerated(EnumType.STRING)
    private QuizPackMemberRole role;

    public QuizPackMember(long memberId, QuizPackMemberRole role) {
        this.memberId = memberId;
        this.role = role;
    }

    public boolean hasRole(QuizPackMemberRole role) {
        return this.role == role;
    }
}
