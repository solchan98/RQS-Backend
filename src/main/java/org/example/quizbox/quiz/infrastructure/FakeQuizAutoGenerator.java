package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult;
import org.example.quizbox.quiz.domain.QuizAutoCreateTaskManager;
import org.example.quizbox.tag.domain.Tag;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult.AddTaskStatus.FAILED;

@Component
public class FakeQuizAutoGenerator implements QuizAutoCreateTaskManager {

    @Override
    public AddQuizAutoCreateTaskResult addTask(long memberId, String title, Set<Tag> tags) {
        return new AddQuizAutoCreateTaskResult(memberId, null, FAILED, "현재 작업 불가능");
    }
}
