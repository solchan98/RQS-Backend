package org.example.quizbox.quiz.domain;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizPackRepository {
    QuizPack save(QuizPack quizPack);

    Optional<QuizPack> findById(long quizPackId);


    Optional<Quiz> findQuizByIdAndQuizId(long quizPackId, long quizId);

    Optional<QuizPackMember> findQuizPackMemberByIdAndMemberId(long quizPackId, long memberId);
}
