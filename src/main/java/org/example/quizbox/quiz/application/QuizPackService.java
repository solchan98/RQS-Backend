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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizPackService {

    private final IQuizPackRepository quizPackRepository;

    @Transactional(readOnly = true)
    public QuizPackStatus getQuizPackStatus(long quizPackId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        return QuizPackStatus.from(quizPack);
    }

    @Transactional
    public QuizPack create(String title) {
        return quizPackRepository.save(new QuizPack(title));
    }

    @Transactional
    public Quiz addQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        QuizAnswers quizAnswers = createQuizAnswers(createQuiz);
        Quiz quiz = Quiz.create(new QuizContent(createQuiz.quizContent()), quizAnswers);

        quizPack.addQuiz(quiz);
        quizPack = quizPackRepository.save(quizPack);

        return quizPack.findByQuizContent(quiz.getContent())
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
}
