package org.example.quizbox.quiz.domain2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_pack_id")
    private Set<Quiz> quizzes = new HashSet<>();

    @Embedded
    private QuizPackMembers quizPackMembers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_pack_id")
    private Set<QuizPackTag> tags = new HashSet<>();


    public QuizPack(String title, Set<QuizPackMember> members, Set<Long> tagIds) {
        this.title = title;
        this.quizPackMembers = new QuizPackMembers(members);
        this.tags = tagIds.stream().map(QuizPackTag::new).collect(Collectors.toSet());
    }


    public void addQuiz(Quiz newQuiz) {
        validateIsCreatableQuizzes(newQuiz.getQuizPackMember());
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

    public Quiz getQuizById(long quizId) {
        return null;
    }

    public Set<Long> getTagIds() {
        return tags.stream().map(QuizPackTag::getTagId).collect(Collectors.toSet());
    }
}
