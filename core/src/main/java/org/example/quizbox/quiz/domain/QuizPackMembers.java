package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizPackMembers {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_pack_id")
    private Set<QuizPackMember> values = new HashSet<>();

    public QuizPackMembers(Set<QuizPackMember> values) {
        this.values = new HashSet<>(values);
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

    public Set<QuizPackMember> readonlyValues() {
        return new HashSet<>(values);
    }

    public void add(QuizPackMember quizPackMember) {
        values.add(quizPackMember);
    }
}
