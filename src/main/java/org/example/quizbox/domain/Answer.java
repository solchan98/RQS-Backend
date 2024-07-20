package org.example.quizbox.domain;

import static org.example.quizbox.domain.AnswerType.QUIZ;
import static org.example.quizbox.domain.AnswerType.SUBMIT;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = {"id"})
@Getter
public class Answer {

    private Long id;

    private String content;

    private Boolean correct;

    private AnswerType answerType;

    private Answer(Long id, String content, Boolean correct, AnswerType answerType) {
        this.id = id;
        this.content = content;
        this.correct = correct;
        this.answerType = answerType;

        if (answerType == SUBMIT) {
            this.correct = null;
        }
    }

    public static Answer forQuizAnswer(long id, String content, boolean correct) {
        return new Answer(id, content, correct, QUIZ);
    }

    public static Answer forSubmitAnswer(long id, String content) {
        return new Answer(id, content, null, SUBMIT);
    }

}

enum AnswerType {
    QUIZ,
    SUBMIT
}
