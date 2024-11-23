package org.example.quizbox.quiz.domain;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Quiz {

    private Long id;

    private final QuizPackMember creator;

    private QuizContent content;

    private QuizAnswers quizAnswers;

    public Quiz(Long id, QuizPackMember creator, QuizContent content, QuizAnswers quizAnswers) {
        this.id = id;
        this.creator = creator;
        this.content = content;
        this.quizAnswers = quizAnswers;
    }

    public static Quiz create(QuizPackMember creator, QuizContent content, QuizAnswers quizAnswers) {
        return new Quiz(null, creator, content, quizAnswers);
    }

    public boolean isMatched(Set<Long> submitAnswerIds) {
        return quizAnswers.isMatchedCorrectAnswers(submitAnswerIds);
    }

    public boolean isSameContent(Quiz newQuiz) {
        return this.content.equals(newQuiz.content);
    }

    public boolean containsAllAnswers(Set<Long> answerIds) {
        return quizAnswers.containsAll(answerIds);
    }
}
