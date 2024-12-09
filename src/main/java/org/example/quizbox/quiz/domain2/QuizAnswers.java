package org.example.quizbox.quiz.domain2;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
public class QuizAnswers {

    private static final long MIN_SIZE_OF_ANSWER = 2;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public boolean isMatchedCorrectAnswers(Set<Long> submitAnswerIds) {
        return false;
    }

    public Set<Answer> answers() {
        return new HashSet<>(answers);
    }

    public boolean containsAll(Set<Long> submitAnswerIds) {
        Set<Long> quizAnswerIds = answers().stream()
                .map(Answer::getId)
                .collect(Collectors.toSet());

        return quizAnswerIds.containsAll(submitAnswerIds);
    }

    public Set<Answer> correctAnswers() {
        return null;
    }

    public Set<Answer> incorrectAnswers() {
        return null;
    }
}
