package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.quiz.Answer;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class AnswerResponse {
    private final Long answerId;
    private final Long quizId;
    private final String answer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private AnswerResponse(Long answerId, Long quizId, String answer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.answerId = answerId;
        this.quizId = quizId;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AnswerResponse of(Answer answer) {
        return new AnswerResponse(
                answer.getAnswerId(),
                answer.getQuiz().getQuizId(),
                answer.getAnswer(),
                answer.getCreatedAt(),
                answer.getUpdatedAt()
        );
    }

}
