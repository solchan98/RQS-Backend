package org.example.quizbox.support.infrastructure;

import org.example.quizbox.quiz.infrastructure.QuizAutoCreateTaskManagerImpl;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(showSql = false)
@ComponentScan(
        basePackages = {"org.example.quizbox.quiz.infrastructure", "org.example.quizbox.game.infrastructure"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = QuizAutoCreateTaskManagerImpl.class
        )
)
public @interface DBTest {
}
