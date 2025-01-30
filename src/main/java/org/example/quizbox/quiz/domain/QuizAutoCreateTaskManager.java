package org.example.quizbox.quiz.domain;

import org.example.quizbox.tag.domain.Tag;

import java.util.Set;

public interface QuizAutoCreateTaskManager {

    AddQuizAutoCreateTaskResult addTask(long memberId, String title, Set<Tag> tags);

}
