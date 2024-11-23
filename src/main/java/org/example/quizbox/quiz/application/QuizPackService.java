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
import org.example.quizbox.quiz.domain.QuizPicker;
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
    public long addQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        QuizPackMember quizPackMember = quizPack.getQuizPackMemberByMemberId(createQuiz.memberId());

        Quiz newQuiz = createQuiz(createQuiz, quizPackMember);
        quizPack.addQuiz(newQuiz);
        quizPack = quizPackRepository.save(quizPack);

        return quizPack.getId();
    }

    private static Quiz createQuiz(CreateQuiz createQuiz, QuizPackMember quizPackMember) {
        return Quiz.create(quizPackMember, new QuizContent(createQuiz.quizContent()), createQuizAnswers(createQuiz));
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
    public Quiz getQuiz(long quizPackId, long quizId, long memberId) {
        QuizPicker quizPicker = new QuizPicker(quizPackRepository);

        return quizPicker.getQuiz(quizPackId, quizId, memberId);
    }
}
