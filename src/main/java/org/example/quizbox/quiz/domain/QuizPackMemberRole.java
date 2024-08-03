package org.example.quizbox.quiz.domain;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum QuizPackMemberRole {
    UPDATABLE,
    READABLE;

    public static Set<QuizPackMemberRole> allRoles() {
        return Arrays.stream(QuizPackMemberRole.values()).collect(Collectors.toSet());
    }
}
