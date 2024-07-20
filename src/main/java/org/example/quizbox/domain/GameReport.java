package org.example.quizbox.domain;

import java.util.Set;

public record GameReport(int quizCount, Set<QuizMatch> quizMatches) {

    public int matchCount() {
        return (int) quizMatches.stream().filter(QuizMatch::matched).count();
    }
}

record QuizMatch(QuizContent content, Answers answers, Answers selectedAnswers, boolean matched) {

    public static QuizMatch of(Quiz quiz, Answers selectedAnswer) {
        return new QuizMatch(quiz.getContent(), quiz.getAnswers(), selectedAnswer, quiz.isMatched(selectedAnswer));
    }

}
