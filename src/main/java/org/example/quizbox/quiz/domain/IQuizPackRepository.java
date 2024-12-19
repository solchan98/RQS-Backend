package org.example.quizbox.quiz.domain;

import org.example.quizbox.common.presentation.Pagination;

import java.util.List;
import java.util.Optional;

public interface IQuizPackRepository {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);

    List<QuizPack> findAllBy(long memberId);

    List<QuizPack> findAllBy(Pagination pageable);

    List<QuizPack> findAllBy(long memberId, Pagination pageable);

    List<QuizPack> findAll();
}
