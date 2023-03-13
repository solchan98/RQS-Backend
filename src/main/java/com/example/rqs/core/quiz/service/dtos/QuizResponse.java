package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QuizResponse {

    private Long quizId;

    private Long spaceId;

    private String question;

    private SpaceMemberResponse spaceMemberResponse;

    private String answer;

    private String hint;

    private LocalDateTime createdAt;

    public QuizResponse(Long quizId, Long spaceId, String question, SpaceMember spaceMember, String answer, String hint, LocalDateTime createdAt) {
        this.quizId = quizId;
        this.spaceId = spaceId;
        this.question = question;
        this.spaceMemberResponse = SpaceMemberResponse.of(spaceMember);
        this.answer = answer;
        this.hint = hint;
        this.createdAt = createdAt;
    }

    public static QuizResponse of(Quiz quiz) {
        return new QuizResponse(
                quiz.getQuizId(),
                quiz.getSpace().getSpaceId(),
                quiz.getQuestion(),
                quiz.getSpaceMember(),
                quiz.getAnswer(),
                quiz.getHint(),
                quiz.getCreatedAt()
        );
    }

}
