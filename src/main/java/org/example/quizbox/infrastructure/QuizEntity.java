package org.example.quizbox.infrastructure;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.quizbox.domain.Answer;
import org.example.quizbox.domain.Answers;
import org.example.quizbox.domain.Quiz;
import org.example.quizbox.domain.QuizContent;

@Data
@AllArgsConstructor
public class QuizEntity {

    private Long id;
    private String content;
    private Set<AnswerEntity> answerEntities;

    public Quiz toDomain() {
        Set<Answer> answers = Stream.concat(
                answerEntities.stream().filter(AnswerEntity::isCorrect).map(AnswerEntity::toDomain),
                answerEntities.stream().filter(answerEntity -> !answerEntity.isCorrect()).map(AnswerEntity::toDomain)
        ).collect(Collectors.toSet());
        return new Quiz(id, new QuizContent(content), new Answers(answers));
    }
}
