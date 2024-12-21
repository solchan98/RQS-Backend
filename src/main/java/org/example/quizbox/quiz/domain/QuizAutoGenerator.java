package org.example.quizbox.quiz.domain;

import java.util.Set;
import org.example.quizbox.tag.domain.Tags;

public interface QuizAutoGenerator {

    Set<Quiz> generate(QuizPackMember creator, Tags tags, int hopeCount);

}
