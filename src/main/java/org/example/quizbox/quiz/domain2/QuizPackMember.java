package org.example.quizbox.quiz.domain2;

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

    private long memberId;

    @Enumerated(EnumType.STRING)
    private QuizPackMemberRole role;

    public static QuizPackMember createAdmin(long memberId) {
        return new QuizPackMember(null, memberId, QuizPackMemberRole.ADMIN);
    }

    public boolean hasRole(QuizPackMemberRole role) {
        return this.role == role;
    }
}
