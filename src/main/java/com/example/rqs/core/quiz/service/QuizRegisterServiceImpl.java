package com.example.rqs.core.quiz.service;

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
        SpaceMember spaceMember = smReadService
                .getSpaceMember(createQuiz.getMember().getMemberId(), createQuiz.getSpaceId())
                .orElseThrow(ForbiddenException::new);

        boolean isCreatable = smAuthService.isCreatableItem(spaceMember);
        if(!isCreatable) throw new ForbiddenException();

        Quiz quiz = Quiz.newQuiz(spaceMember, createQuiz.getQuestion(), createQuiz.getAnswer(), createQuiz.getHint());
        quizRepository.save(quiz);
        return QuizResponse.of(quiz);
    }
}
