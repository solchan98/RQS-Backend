package org.example.quizbox.quiz.domain;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizPackRepository {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);

}
