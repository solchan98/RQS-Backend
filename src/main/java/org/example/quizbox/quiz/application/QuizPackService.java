package org.example.quizbox.quiz.application;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.IQuizPackRepository;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizAnswers;
import org.example.quizbox.quiz.domain.QuizContent;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.domain.QuizPackMembers;
import org.example.quizbox.quiz.domain.QuizPackStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizPackService {

    private final IQuizPackRepository quizPackRepository;

    @Transactional(readOnly = true)
    public QuizPackStatus getQuizPack(long quizPackId, long memberId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        return quizPack.status(memberId);
    }

    @Transactional
    public QuizPack create(String title, long memberId) {
        QuizPackMembers quizPackMembers = new QuizPackMembers(QuizPackMember.createAdmin(memberId));

        return quizPackRepository.save(new QuizPack(title, quizPackMembers));
    }

    @Transactional
    public Quiz createQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        QuizContent quizContent = new QuizContent(createQuiz.quizContent());
        QuizAnswers quizAnswers = createQuizAnswers(createQuiz);

        quizPack.createQuiz(createQuiz.memberId(), quizContent, quizAnswers);
        quizPack = quizPackRepository.save(quizPack);

        return quizPack.findByQuizContent(quizContent)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP2));
    }

    private static QuizAnswers createQuizAnswers(CreateQuiz createQuiz) {
        Set<CreateAnswer> createAnswers = createQuiz.answers();
        return new QuizAnswers(
                createAnswers.stream().filter(CreateAnswer::correct).map(CreateAnswer::toAnswer)
                        .collect(Collectors.toSet()),
                createAnswers.stream().filter(createAnswer -> !createAnswer.correct()).map(CreateAnswer::toAnswer)
                        .collect(Collectors.toSet())
        );
    }

    @Transactional(readOnly = true)
    public Quiz getQuiz(long quizPackId, long quizId) {
        return quizPackRepository.findQuizByIdAndQuizId(quizPackId, quizId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP6));
    }
}
