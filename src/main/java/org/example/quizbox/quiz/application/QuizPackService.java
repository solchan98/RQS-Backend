package org.example.quizbox.quiz.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.example.quizbox.common.infrastructure.Pagination;
import org.example.quizbox.quiz.domain.*;
import org.example.quizbox.tag.application.TagService;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.TG1;

@Service
@RequiredArgsConstructor
public class QuizPackService {

    private final IQuizPackRepository quizPackRepository;

    private final TagService tagService;

    private final QuizAutoGenerator quizAutoGenerator;

    @Transactional(readOnly = true)
    public QuizPackStatus getQuizPack(long quizPackId, long memberId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        quizPack.validateIsMember(memberId);

        Tags tags = tagService.getAll(quizPack.getTagIds());

        return QuizPackStatus.from(quizPack, tags);
    }

    @Transactional(readOnly = true)
    public List<QuizPackStatus> getQuizPacks(Long memberId, Pagination pagination) {
        List<QuizPack> quizPacks = memberId != null
                ? quizPackRepository.findAllBy(memberId, pagination)
                : quizPackRepository.findAllBy(pagination);

        Set<Long> tagIds = quizPacks.stream().map(QuizPack::getTagIds).flatMap(Set::stream).collect(Collectors.toSet());
        Tags tags = tagService.getAll(tagIds);

        return quizPacks.stream()
                .map(quizPack -> QuizPackStatus.from(
                        quizPack,
                        tags.getByIds(quizPack.getTagIds())
                ))
                .toList();
    }

    @Transactional
    public QuizPack create(long memberId, String title, Set<Long> tagIds) {
        if (!tagService.existsAll(tagIds)) {
            throw new BusinessException(TG1);
        }
        QuizPack quizPack = new QuizPack(title, Set.of(memberId), tagIds);

        return quizPackRepository.save(quizPack);
    }

    @Transactional
    public long addQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        QuizPackMember quizPackMember = quizPack.validateIsMember(createQuiz.memberId());

        Quiz newQuiz = new Quiz(quizPackMember, new QuizContent(createQuiz.quizContent()), createQuizOptions(createQuiz));
        quizPack.addQuiz(newQuiz);
        quizPack = quizPackRepository.save(quizPack);

        return quizPack.getId();
    }

    private static Set<Option> createQuizOptions(CreateQuiz createQuiz) {
        return createQuiz.options()
                .stream()
                .map(createOption -> new Option(null, createOption.content(), createOption.correct()))
                .collect(Collectors.toSet());
    }

    @Transactional
    public long autoGenerator(long memberId, String title, Set<Long> tagIds, int hopeCount) {
        if (!tagService.existsAll(tagIds)) {
            throw new BusinessException(TG1);
        }

        QuizPack quizPack = new QuizPack(title, Set.of(memberId), tagIds);
        quizPack.cancelPublish();
        QuizPackMember creator = quizPack.getQuizPackMemberBy(memberId);

        Tags tags = tagService.getAll(tagIds);
        Set<Quiz> quizzes = quizAutoGenerator.generate(creator, tags, hopeCount);
        quizzes.forEach(quizPack::addQuiz);

        return quizPackRepository.save(quizPack)
                .getId();

    }
}
