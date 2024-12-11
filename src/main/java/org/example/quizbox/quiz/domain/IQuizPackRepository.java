package org.example.quizbox.quiz.domain;

import java.util.Optional;

public interface IQuizPackRepository {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);
}
