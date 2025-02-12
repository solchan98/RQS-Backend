package org.example.quizbox.quiz.domain;

public interface QuizAutoCreateTaskManager {

    AddQuizAutoCreateTaskResult addTask(long memberId, String quizPackTitle, String base64File, String fileMineType);

}
