package org.example.quizbox.domain;

import java.util.Collection;
import java.util.Optional;

public interface GameQuizzes {

    boolean isEmpty();

    int size();

    boolean existsNextQuiz();

    Optional<Quiz> nextQuiz();

    Collection<Quiz> values();

    boolean isSubmittableAnswers(long quizId);

    int remainQuizCount();

    Optional<Long> lastSubmittedQuizId();
}
