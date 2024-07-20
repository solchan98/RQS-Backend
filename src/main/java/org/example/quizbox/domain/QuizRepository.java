package org.example.quizbox.domain;

import java.util.List;

public interface QuizRepository {

    List<Quiz> findAllByGroupId(long groupId);
}
