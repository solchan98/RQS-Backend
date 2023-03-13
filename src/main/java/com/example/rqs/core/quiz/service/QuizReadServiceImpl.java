package com.example.rqs.core.quiz.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.repository.QuizRepository;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.quiz.service.dtos.ReadQuiz;
import com.example.rqs.core.quiz.service.dtos.ReadQuizzes;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.service.SpaceReadService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizReadServiceImpl implements QuizReadService {

    private final SpaceReadService spaceReadService;
    private final SpaceMemberReadService smReadService;

    private final QuizRepository quizRepository;

    @Override
    public QuizResponse getQuiz(ReadQuiz readQuiz) {
        Quiz quiz = quizRepository.findById(readQuiz.getItemId()).orElseThrow(BadRequestException::new);

        boolean isGuest = Objects.isNull(readQuiz.getMember());
        if (!quiz.getSpace().isVisibility()) {
            if (isGuest) throw new ForbiddenException();
            this.checkIsSpaceMember(readQuiz.getMember(), quiz.getSpace().getSpaceId());
        }

        return QuizResponse.of(quiz);
    }

    @Override
    public List<QuizResponse> getQuizzes(ReadQuizzes readQuizzes) {
        Space space = spaceReadService
                .getSpace(readQuizzes.getSpaceId())
                .orElseThrow(BadRequestException::new);

        boolean isGuest = Objects.isNull(readQuizzes.getMember());
        if (!space.isVisibility()) {
            if (isGuest) throw new ForbiddenException();
            this.checkIsSpaceMember(readQuizzes.getMember(), space.getSpaceId());
        }

        return quizRepository.getQuizzes(readQuizzes.getSpaceId(), readQuizzes.getLastId());
    }

    @Override
    public List<Long> getQuizIds(Long spaceId, String type) {
        return quizRepository.getQuizIds(spaceId, type);
    }

    private void checkIsSpaceMember(Member member, Long spaceId) throws ForbiddenException {
        smReadService.getSpaceMember(member.getMemberId(), spaceId).orElseThrow(ForbiddenException::new);
    }
}
