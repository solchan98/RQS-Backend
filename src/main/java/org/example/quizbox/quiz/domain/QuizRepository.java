package org.example.quizbox.quiz.domain;

import java.util.List;

public interface QuizRepository {

    Quiz save(Quiz quiz);

    List<Quiz> findAllByGroupId(long groupId);
}
