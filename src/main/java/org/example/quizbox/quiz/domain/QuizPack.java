package org.example.quizbox.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class QuizPack {

    private Long id;

    private String title;

    private final QuizPackMembers quizPackMembers;

    private Collection<Quiz> quizzes = new ArrayList<>();

    private Set<Long> tagIds = new HashSet<>();


    public QuizPack(String title, QuizPackMembers quizPackMembers, Collection<Quiz> quizzes, Set<Long> tagIds) {
        this.title = title;
        this.quizPackMembers = quizPackMembers;
        this.tagIds = tagIds;
        if (!CollectionUtils.isEmpty(quizzes)) {
            this.quizzes = new ArrayList<>(quizzes);
        }
    }

   public static QuizPack create(String title, QuizPackMembers quizPackMembers, Set<Long> tagIds) {
        return new QuizPack(title, quizPackMembers, null, tagIds);
   }

    public void addTags(Set<Long> tagIds) {
        this.tagIds.addAll(tagIds);
    }

    public void addQuiz(Quiz newQuiz) {
        validateIsCreatableQuizzes(newQuiz.getCreator());
        boolean duplicateContent = quizzes.stream().anyMatch(quiz -> quiz.isSameContent(newQuiz));
        if (duplicateContent) {
            throw new BusinessException(ExceptionConstants.QP3);
        }

        quizzes.add(newQuiz);
    }

    public QuizPackMember validateIsMember(long memberId) {
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
}