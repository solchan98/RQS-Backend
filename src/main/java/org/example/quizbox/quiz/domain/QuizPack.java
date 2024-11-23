package org.example.quizbox.quiz.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

@Getter
@AllArgsConstructor
public class QuizPack {

    private Long id;

    private final QuizPackMembers quizPackMembers;

    private String title;

    private Collection<Quiz> quizzes = new ArrayList<>();

    public QuizPack(String title, QuizPackMembers quizPackMembers) {
        this.title = title;
        this.quizPackMembers = quizPackMembers;
    }

    public QuizPack(String title, QuizPackMembers quizPackMembers, Collection<Quiz> quizzes) {
        this.title = title;
        this.quizPackMembers = quizPackMembers;
        if (!CollectionUtils.isEmpty(quizzes)) {
            this.quizzes = new ArrayList<>(quizzes);
        }
    }

    public void addQuiz(Quiz newQuiz) {
        validateIsCreatableQuizzes(newQuiz.getCreator());
        boolean duplicateContent = quizzes.stream().anyMatch(quiz -> quiz.isSameContent(newQuiz));
        if (duplicateContent) {
            throw new BusinessException(ExceptionConstants.QP3);
        }

        quizzes.add(newQuiz);
    }

    public QuizPackMember getQuizPackMemberByMemberId(long memberId) {
        return quizPackMembers.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP4));
    }

    private void validateIsCreatableQuizzes(QuizPackMember quizPackMember) {
        if (!quizPackMembers.contains(quizPackMember)) {
            throw new BusinessException(ExceptionConstants.QP4);
        }

        if (!quizPackMember.hasRole(QuizPackMemberRole.UPDATABLE)) {
            throw new BusinessException(ExceptionConstants.QP5);
        }
    }

    public long quizSize() {
        if (CollectionUtils.isEmpty(quizzes)) {
            return 0;
        }

        return quizzes.size();
    }

    public long memberSize() {
        return quizPackMembers.size();
    }

    public Quiz getQuizById(long quizId) {
        return quizzes.stream().filter(quiz -> quiz.getId().equals(quizId)).findFirst()
                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP6));
    }

    public QuizPackStatus status(long memberId) {
        getQuizPackMemberByMemberId(memberId);

        return new QuizPackStatus(id, title, memberSize(), quizSize());
    }
}