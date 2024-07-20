package org.example.quizbox.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.quizbox.domain.Answer;

@Data
@AllArgsConstructor
public class AnswerEntity {

    private Long id;

    private String content;

    private boolean correct;

    public Answer toDomain() {
        return Answer.forQuizAnswer(id, content, correct);
    }
}
