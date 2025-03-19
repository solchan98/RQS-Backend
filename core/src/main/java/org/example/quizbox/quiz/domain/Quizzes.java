package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.quizbox.common.BusinessException;
import org.example.quizbox.common.ExceptionConstants;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Quizzes {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_pack_id")
    private Set<Quiz> values = new HashSet<>();

    public Quizzes(Set<Quiz> values) {
        if (CollectionUtils.isEmpty(values)) {
            throw new BusinessException(ExceptionConstants.QP_MIN_QUIZ);
        }
        this.values = new HashSet<>(values);
    }

    public long size() {
        return values.size();
    }

    public void add(Quiz newQuiz) {
        boolean duplicateContent = values.stream()
                .anyMatch(quiz -> quiz.isSameContent(newQuiz));
        if (duplicateContent) {
            throw new BusinessException(ExceptionConstants.QP3);
        }
        this.values.add(newQuiz);
    }

    public Set<Quiz> readonlyValues() {
        return new HashSet<>(values);
    }
}
