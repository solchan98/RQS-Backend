package org.example.quizbox.quiz.domain;

import java.util.List;
import java.util.Optional;
import org.example.quizbox.common.infrastructure.Pagination;

public interface IQuizPackRepository {

    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);

    List<QuizPack> findAllBy(long memberId);

    List<QuizPack> findAllBy(Pagination pageable, boolean published);

    List<QuizPack> findAllBy(long memberId, Pagination pageable, boolean published);

    List<QuizPack> findAll();
}
