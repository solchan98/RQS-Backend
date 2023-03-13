package com.example.rqs.core.quiz.service;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.common.exception.RQSError;
import com.example.rqs.core.quiz.Quiz;
import com.example.rqs.core.quiz.repository.QuizRepository;
import com.example.rqs.core.quiz.service.dtos.DeletedQuizData;
import com.example.rqs.core.quiz.service.dtos.QuizResponse;
import com.example.rqs.core.quiz.service.dtos.UpdateQuiz;
import com.example.rqs.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizUpdateServiceImpl implements QuizUpdateService {

    private final QuizRepository quizRepository;
    private final QuizAuthService quizAuthService;

    @Override
    public QuizResponse updateQuiz(UpdateQuiz updateQuiz) {
        Quiz quiz = quizRepository.findById(updateQuiz.getQuizId()).orElseThrow(BadRequestException::new);

        boolean isCreator = this.quizAuthService.isQuizCreator(updateQuiz.getMember(), quiz);
        if (!isCreator) throw new ForbiddenException();

        quiz.updateContent(updateQuiz.getQuestion(), updateQuiz.getAnswer(), updateQuiz.getHint());
        return QuizResponse.of(quiz);
    }

    @Override
    public DeletedQuizData deleteQuiz(Member member, Long quizId) {
        boolean isCreator = quizAuthService.isQuizCreator(member, quizId);
        if (!isCreator) throw new ForbiddenException();

        Quiz quiz = quizRepository
                .findById(quizId)
                .orElseThrow(() -> new BadRequestException(RQSError.ITEM_IS_NOT_EXIST_IN_SPACE));

        quizRepository.deleteById(quizId);
        return DeletedQuizData.of(quiz.getSpace().getSpaceId(), quizId);
    }
}
