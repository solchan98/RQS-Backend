package org.example.quizbox.quiz.domain;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Quiz {

    private Long id;

    private final QuizContent content;

    private final QuizAnswers quizAnswers;

    public Quiz(Long id, QuizContent content, QuizAnswers quizAnswers) {
        this.id = id;
        this.content = content;
        this.quizAnswers = quizAnswers;
    }

    public static Quiz create(QuizContent content, QuizAnswers quizAnswers) {
        return new Quiz(null, content, quizAnswers);
    }

    public boolean isMatched(Set<Long> submitAnswerIds) {
        return quizAnswers.isMatchedCorrectAnswers(submitAnswerIds);
    }

    public boolean isSameContent(Quiz otherQuiz) {
        return this.content.equals(otherQuiz.content);
    }

    public boolean containsAllAnswers(Set<Long> answerIds) {
        return quizAnswers.containsAll(answerIds);
    }
}
