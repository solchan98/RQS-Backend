package org.example.quizbox.domain;

import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Quiz {

    private Long id;

    private final QuizContent content;

    private final Answers answers;

    public Quiz(Long id) {
        this.id = id;
        this.content = QuizContent.blank();
        this.answers = new Answers(Set.of());
    }

    public Quiz(Long id, QuizContent content, Answers answers) {
        this.id = id;
        this.content = content;
        this.answers = answers;
    }

    public boolean isMatched(Answers selectedAnswer) {
        return answers.isMatchedCorrectAnswers(selectedAnswer);
    }
}
