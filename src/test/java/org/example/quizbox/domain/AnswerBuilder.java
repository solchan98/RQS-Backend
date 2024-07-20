package org.example.quizbox.domain;

public class AnswerBuilder {

    private static Long DEFAULT_ID = 0L;

    private Long id;

    private String content;

    private boolean correct = false;

    public static AnswerBuilder builder() {
        AnswerBuilder answerBuilder = new AnswerBuilder();
        answerBuilder.id = --DEFAULT_ID;
        return answerBuilder;
    }

    public AnswerBuilder id(long id) {
        this.id = id;
        return this;
    }

    public AnswerBuilder content(String value) {
        this.content = value;
        return this;
    }

    public AnswerBuilder correct(boolean correct) {
        this.correct = correct;
        return this;
    }

    public Answer buildForQuiz() {
        return Answer.forQuizAnswer(id, content, correct);
    }

    public Answer buildForSubmit() {
        return Answer.forSubmitAnswer(id, content);
    }
}
