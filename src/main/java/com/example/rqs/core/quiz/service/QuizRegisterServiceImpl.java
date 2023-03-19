package com.example.rqs.core.quiz.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.repository.QuizRepository;
import com.example.rqs.core.quiz.service.dtos.CreateQuiz;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizRegisterServiceImpl implements QuizRegisterService {

    private final SpaceMemberReadService smReadService;
    private final SpaceMemberAuthService smAuthService;

    private final QuizRepository quizRepository;

    @Override
    public QuizResponse createQuiz(CreateQuiz createQuiz) {
        SpaceMember spaceMember = getSpaceMember(createQuiz.getMember().getMemberId(), createQuiz.getSpaceId());
        checkIsCreatable(spaceMember);

        Quiz quiz = Quiz.newQuiz(spaceMember, createQuiz.getQuestion(), createQuiz.getCreateAnswers(), createQuiz.getType(), createQuiz.getHint());
        quizRepository.save(quiz);
        return QuizResponse.of(quiz);
    }

    @Override
    public QuizResponse createChildQuiz(CreateQuiz createQuiz, Long parentQuizId) {
        SpaceMember spaceMember = getSpaceMember(createQuiz.getMember().getMemberId(), createQuiz.getSpaceId());
        checkIsCreatable(spaceMember);

        Quiz parentQuiz = quizRepository
                .findById(parentQuizId)
                .orElseThrow(() -> new BadRequestException("해당 퀴즈가 존재하지 않습니다."));
        if (!parentQuiz.isSameType(createQuiz.getType())) {
            throw new BadRequestException("부모와 같은 타입의 퀴즈만 생성할 수 있습니다.");
        }
        Quiz quiz = Quiz.newChildQuiz(spaceMember, createQuiz.getQuestion(), createQuiz.getCreateAnswers(), "child", createQuiz.getHint());

        quizRepository.save(quiz);
        parentQuiz.addChildId(quiz.getQuizId());
        return QuizResponse.of(quiz);
    }

    private SpaceMember getSpaceMember(Long memberId, Long spaceId) {
        return smReadService
                .getSpaceMember(memberId, spaceId)
                .orElseThrow(ForbiddenException::new);
    }

    private void checkIsCreatable(SpaceMember spaceMember) {
        boolean isCreatable = smAuthService.isCreatableItem(spaceMember);
        if(!isCreatable) {
            throw new ForbiddenException();
        }
    }
}
