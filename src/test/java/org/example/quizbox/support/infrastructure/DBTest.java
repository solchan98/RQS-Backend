package org.example.quizbox.support.infrastructure;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest(showSql = false)
@ComponentScan(basePackages = {"org.example.quizbox.quiz.infrastructure", "org.example.quizbox.game.infrastructure"})
public @interface DBTest {
}
