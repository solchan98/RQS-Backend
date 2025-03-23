package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.quizbox.common.Audit;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.example.quizbox.keyword.domain.Keyword;

import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Entity
public class QuizPack extends Audit {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String title;

    @Embedded
    private Quizzes quizzes;

    @Embedded
    private QuizPackMembers quizPackMembers;

    @Embedded
    private QuizPackKeywords keywords;

    public static QuizPack of(
            String title,
            QuizPackMembers quizPackMembers,
            Quizzes quizzes,
            QuizPackKeywords quizPackKeywords,
            long creatorId
    ) {
        QuizPack quizPack = new QuizPack(null, title, quizzes, quizPackMembers, quizPackKeywords);
        quizPack.initAudit(creatorId);
        return quizPack;
    }

    public Quizzes getQuizzes(QuizPackMember quizPackMember) {
        if (!quizPackMembers.contains(quizPackMember)) {
            throw new BusinessException(ExceptionConstants.QP4);
        }

        return quizzes;
    }

    public void addQuiz(Quiz newQuiz) {
        validateIsCreatableQuizzes(newQuiz.getQuizPackMember());
        quizzes.add(newQuiz);
    }

    public void addKeywords(Set<Keyword> keywords) {
        this.keywords.addAll(keywords);
    }

    public void validateIsMember(long memberId) {
        quizPackMembers.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP4));
    }

    public void validateIsMember(QuizPackMember quizPackMember) {
        boolean contains = quizPackMembers.contains(quizPackMember);
        if (!contains) {
            throw new BusinessException(ExceptionConstants.QP4);
        }
    }

    private void validateIsCreatableQuizzes(QuizPackMember quizPackMember) {
        if (!quizPackMembers.contains(quizPackMember)) {
            throw new BusinessException(ExceptionConstants.QP4);
        }

        if (!quizPackMember.hasRole(QuizPackMemberRole.ADMIN)) {
            throw new BusinessException(ExceptionConstants.QP5);
        }

    }

    public long quizSize() {
        return quizzes.size();
    }

    public long memberSize() {
        return quizPackMembers.size();
    }

    public Set<Long> getKeywordIds() {
        return keywords.keywordIds();
    }

    public QuizPackMembers getQuizPackMembersBy(QuizPackMember quizPackMember) {
        validateIsMember(quizPackMember);
        return new QuizPackMembers(quizPackMembers.readonlyValues());
    }

    public QuizPackMember getQuizPackMemberBy(long memberId) {
        validateIsMember(memberId);
        return quizPackMembers.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP4));
    }
}
