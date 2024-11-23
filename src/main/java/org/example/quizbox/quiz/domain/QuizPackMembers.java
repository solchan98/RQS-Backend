package org.example.quizbox.quiz.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class QuizPackMembers {

    private final Set<QuizPackMember> values = new HashSet<>();

    public QuizPackMembers(Set<QuizPackMember> values) {
        this.values.addAll(values);
    }

    public QuizPackMembers(QuizPackMember... values) {
        this.values.addAll(Set.of(values));
    }

    public Optional<QuizPackMember> findByMemberId(long memberId) {
        return values.stream().filter(quizPackMember -> quizPackMember.getMemberId() == memberId).findFirst();
    }

    public boolean contains(QuizPackMember quizPackMember) {
        return values.contains(quizPackMember);
    }

    public long size() {
        return values.size();
    }

    public Set<QuizPackMember> values() {
        return new HashSet<>(values);
    }

    public void add(QuizPackMember quizPackMember) {
        values.add(quizPackMember);
    }
}
