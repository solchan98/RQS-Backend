package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.domain.QuizPackMemberRole;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class QuizPackMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizPackMemberId;

    private long memberId;

    @ManyToOne
    private QuizPackEntity quizPackEntity;

    private String stringRoles;

    public QuizPackMember toDomain() {
        Set<QuizPackMemberRole> roles = Arrays.stream(this.stringRoles.split(",")).map(QuizPackMemberRole::valueOf)
                .collect(Collectors.toSet());

        return new QuizPackMember(memberId, quizPackMemberId, roles);
    }

    public static QuizPackMemberEntity fromDomain(QuizPackMember quizPackMember) {
        QuizPackMemberEntity quizPackMemberEntity = new QuizPackMemberEntity();

        quizPackMemberEntity.memberId = quizPackMember.getMemberId();
        quizPackMemberEntity.quizPackMemberId = quizPackMember.getQuizPackMemberId();
        quizPackMemberEntity.stringRoles = quizPackMember.getRoles().stream().map(QuizPackMemberRole::name)
                .collect(Collectors.joining(","));

        return quizPackMemberEntity;
    }
}
