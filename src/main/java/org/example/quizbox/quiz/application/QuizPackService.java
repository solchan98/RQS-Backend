package org.example.quizbox.quiz.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.quiz.domain.*;
import org.example.quizbox.tag.application.TagService;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizPackService {

    private final IQuizPackRepository quizPackRepository;

    private final TagService tagService;

    @Transactional(readOnly = true)
    public QuizPackStatus getQuizPack(long quizPackId, long memberId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        quizPack.validateIsMember(memberId);

        Tags tags = tagService.getAll(quizPack.getTagIds());

        return QuizPackStatus.from(quizPack, tags);
    }

    @Transactional
    public QuizPack create(long memberId, String title, Set<Long> tagIds) {
        QuizPackMember quizPackMember = new QuizPackMember(memberId, QuizPackMemberRole.ADMIN);

        if (!tagService.existsAll(tagIds)) {
            // TODO: throw exception tag id not exists
            throw new RuntimeException("tag not found");
        }
        QuizPack quizPack = new QuizPack(title, Set.of(quizPackMember), tagIds);

        return quizPackRepository.save(quizPack);
    }

    @Transactional
    public long addQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        QuizPackMember quizPackMember = quizPack.validateIsMember(createQuiz.memberId());

        Quiz newQuiz = new Quiz(quizPackMember, new QuizContent(createQuiz.quizContent()), createQuizAnswers(createQuiz));
        quizPack.addQuiz(newQuiz);
        quizPack = quizPackRepository.save(quizPack);

        return quizPack.getId();
    }

    private static Set<Answer> createQuizAnswers(CreateQuiz createQuiz) {
        return createQuiz.answers()
                .stream()
                .map(createAnswer -> new Answer(null, createAnswer.content(), createAnswer.correct()))
                .collect(Collectors.toSet());
    }

//    @Transactional(readOnly = true)
//    public Quiz getQuiz(long quizPackId, long quizId, long memberId) {
//        QuizPicker quizPicker = new QuizPicker(quizPackRepository);
//
//        return quizPicker.getQuiz(quizPackId, quizId, memberId);
//    }
}
