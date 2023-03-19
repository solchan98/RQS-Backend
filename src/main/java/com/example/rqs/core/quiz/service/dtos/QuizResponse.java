package com.example.rqs.core.quiz.service.dtos;

import com.example.rqs.core.quiz.Answer;
import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
public class QuizResponse {

    private Long quizId;
    private Long childId;
    private Long spaceId;
    private String question;
    private Boolean isRoot;
    private String type;
    private SpaceMemberResponse spaceMemberResponse;
    private List<AnswerResponse> answerResponses;
    private String hint;
    private LocalDateTime createdAt;

    public QuizResponse(Long quizId, Long spaceId, Long childId, String question, Boolean isRoot, String type, SpaceMember spaceMember, String hint, LocalDateTime createdAt) {
        this.quizId = quizId;
        this.spaceId = spaceId;
        this.childId = childId;
        this.question = question;
        this.type = type;
        this.isRoot = isRoot;
        this.spaceMemberResponse = SpaceMemberResponse.of(spaceMember);
        this.answerResponses = null;
        this.hint = hint;
        this.createdAt = createdAt;
    }

    public QuizResponse(Long quizId, Long spaceId, Long childId, String question, Boolean isRoot, String type, SpaceMember spaceMember, List<Answer> answers, String hint, LocalDateTime createdAt) {
        this.quizId = quizId;
        this.spaceId = spaceId;
        this.childId = childId;
        this.question = question;
        this.isRoot = isRoot;
        this.type = type;
        this.spaceMemberResponse = SpaceMemberResponse.of(spaceMember);
        this.answerResponses = answers.stream().map(AnswerResponse::of).collect(Collectors.toList());
        this.hint = hint;
        this.createdAt = createdAt;
    }

    public static QuizResponse of(Quiz quiz) {
        return new QuizResponse(
                quiz.getQuizId(),
                quiz.getSpace().getSpaceId(),
                quiz.getChildId(),
                quiz.getQuestion(),
                quiz.getIsRoot(),
                quiz.getType(),
                quiz.getSpaceMember(),
                quiz.getAnswers(),
                quiz.getHint(),
                quiz.getCreatedAt()
        );
    }

}
