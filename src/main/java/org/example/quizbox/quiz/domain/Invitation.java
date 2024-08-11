package org.example.quizbox.quiz.domain;

import java.util.Set;

public interface Invitation {

    long memberId();

    long quizPackId();

    Set<QuizPackMemberRole> roles();

}
