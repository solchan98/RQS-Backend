package org.example.quizbox.quiz.infrastructure;

import org.example.quizbox.quiz.domain.*;
import org.example.quizbox.support.QuizPackBuilder;
import org.example.quizbox.support.infrastructure.DBTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;

@DBTest
public class QuizQueryRepositoryTest {

    @Autowired
    private QuizPackRepository quizPackRepository;

    @Autowired
    private QuizQueryRepository quizQueryRepository;

    @Test
    void getById() {
        long adminId = 1L;
        QuizPack quizPack = quizPackBuilder()
                .quizPackMembers(new QuizPackMember(adminId, QuizPackMemberRole.ADMIN))
                .build();
        QuizPackMember admin = quizPack.getQuizPackMemberBy(adminId);
        Quiz quiz = new Quiz(admin, new QuizContent("test quiz 1"), Set.of(
                Option.trueOption("1"), Option.falseOption("2")
        ));
        quizPack.addQuiz(quiz);
        quizPackRepository.save(quizPack);

        assertThat(quizQueryRepository.getById(quiz.getId())).isEqualTo(quiz);
    }
}
