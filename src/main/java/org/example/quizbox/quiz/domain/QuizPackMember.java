package org.example.quizbox.quiz.domain;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class QuizPackMember {

    private final long memberId;

    private Long quizPackMemberId;

    private Set<QuizPackMemberRole> roles;

    public QuizPackMember(long memberId, Set<QuizPackMemberRole> roles) {
        this.memberId = memberId;
        this.roles = roles;
    }

    public QuizPackMember(long memberId, long quizPackMemberId, Set<QuizPackMemberRole> roles) {
        this.memberId = memberId;
        this.quizPackMemberId = quizPackMemberId;
        this.roles = roles;
    }

    public static QuizPackMember createAdmin(long memberId) {
        return new QuizPackMember(memberId, QuizPackMemberRole.allRoles());
    }

    public boolean hasRole(QuizPackMemberRole role) {
        return roles.contains(role);
    }
}
