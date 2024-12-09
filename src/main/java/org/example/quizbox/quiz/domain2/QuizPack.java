package org.example.quizbox.quiz.domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class QuizPack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "quizPack", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    @Embedded
    private QuizPackMembers quizPackMembers;

    @OneToMany(mappedBy = "quizPack", cascade = CascadeType.ALL)
    private Set<QuizPackTag> tags = new HashSet<>();

    public void addTags(Set<Long> tagIds) {
        Set<QuizPackTag> newQuizPackTags = tagIds.stream()
                .map(tagId -> new QuizPackTag(new QuizPackTagId(id, tagId), this))
                .collect(Collectors.toSet());
        tags.addAll(newQuizPackTags);
    }

    public void addQuiz(Quiz newQuiz) {
//        validateIsCreatableQuizzes(newQuiz.getCreator());
//        boolean duplicateContent = quizzes.stream().anyMatch(quiz -> quiz.isSameContent(newQuiz));
//        if (duplicateContent) {
//            throw new BusinessException(ExceptionConstants.QP3);
//        }
//
//        quizzes.add(newQuiz);
    }

    public QuizPackMember validateIsMember(long memberId) {
//        return quizPackMembers.findByMemberId(memberId)
//                .orElseThrow(() -> new BusinessException(ExceptionConstants.QP4));
        return null;
    }

    private void validateIsCreatableQuizzes(QuizPackMember quizPackMember) {
//        if (!quizPackMembers.contains(quizPackMember)) {
//            throw new BusinessException(ExceptionConstants.QP4);
//        }
//
//        if (!quizPackMember.hasRole(QuizPackMemberRole.UPDATABLE)) {
//            throw new BusinessException(ExceptionConstants.QP5);
//        }
    }

    public long quizSize() {
        return 0L;
    }

    public long memberSize() {
        return 0L;
    }

    public Quiz getQuizById(long quizId) {
        return null;
    }
}
