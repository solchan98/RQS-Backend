package org.example.quizbox.quiz.domain2;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IQuizPackRepositoryV2 {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);

    Optional<Quiz> findQuizByIdAndQuizId(long quizPackId, long quizId);
}
