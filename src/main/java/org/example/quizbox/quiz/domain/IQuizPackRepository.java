package org.example.quizbox.quiz.domain;

import java.util.List;
import java.util.Optional;

public interface IQuizPackRepository {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);

    List<QuizPack> findAllByMemberId(long memberId);

    List<QuizPack> findAll();
}
