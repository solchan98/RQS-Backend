package org.example.quizbox.quiz.application;

import lombok.RequiredArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.common.Pagination;
import org.example.quizbox.keyword.application.KeywordService;
import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.keyword.domain.Keywords;
import org.example.quizbox.keyword.presentation.CreateKeyword;
import org.example.quizbox.quiz.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizPackService {

    private final IQuizPackRepository quizPackRepository;

    private final KeywordService keywordService;

    @Transactional(readOnly = true)
    public QuizPackResponse getQuizPack(long quizPackId, long memberId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        QuizPackMember quizPackMember = quizPack.getQuizPackMemberBy(memberId);

        Set<QuizPackMember> quizPackMembers = quizPack.getQuizPackMembersBy(quizPackMember).readonlyValues();
        Set<Quiz> quizzes = quizPack.getQuizzes(quizPackMember).readonlyValues();
        Set<Keyword> keywords = keywordService.getAll(quizPack.getKeywordIds()).readonlyValues();

        return QuizPackResponse.of(quizPack, quizPackMembers, quizzes, keywords);
    }

    @Transactional(readOnly = true)
    public List<QuizPackStatus> getQuizPacks(Long memberId, Pagination pagination) {
        List<QuizPack> quizPacks = memberId != null
                ? quizPackRepository.findAllBy(memberId, pagination)
                : quizPackRepository.findAllBy(pagination);

        Set<Long> keywordIds = quizPacks.stream()
                .map(QuizPack::getKeywordIds)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        Keywords keywords = keywordService.getAll(keywordIds);

        return quizPacks.stream()
                .map(quizPack -> QuizPackStatus.from(
                        quizPack,
                        keywords.getByIds(quizPack.getKeywordIds())
                ))
                .toList();
    }

    @Transactional
    public QuizPack create(long memberId, CreateSimpleQuizPack simpleQuizPack) {
        Set<Keyword> keywords = simpleQuizPack.getKeywords()
                .stream()
                .map(keyword -> keywordService.save(new CreateKeyword(keyword).toKeyword()))
                .collect(Collectors.toSet());

        QuizPack quizPack = simpleQuizPack.toQuizPack(memberId, keywords);

        return quizPackRepository.save(quizPack);
    }

    @Transactional
    public long addQuiz(CreateQuiz createQuiz) {
        QuizPack quizPack = quizPackRepository.findById(createQuiz.quizPackId())
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));
        QuizPackMember quizPackMember = quizPack.getQuizPackMemberBy(createQuiz.memberId());

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

    @Transactional(readOnly = true)
    public Set<Quiz> getQuizzes(long quizPackId, long memberId) {
        QuizPack quizPack = quizPackRepository.findById(quizPackId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP1));

        return quizPack.getQuizzes(quizPack.getQuizPackMemberBy(memberId))
                .readonlyValues();
    }
}
