package org.example.quizbox.quiz.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;
import org.springframework.util.CollectionUtils;

@Getter
@AllArgsConstructor
public class QuizPack {

    private Long id;

    private String title;

    private Collection<Quiz> quizzes = new ArrayList<>();

    public QuizPack(String title) {
        this.title = title;
    }

    public QuizPack(String title, Collection<Quiz> quizzes) {
        this.title = title;
        if (!CollectionUtils.isEmpty(quizzes)) {
            this.quizzes = new ArrayList<>(quizzes);
        }
    }

    public void addQuiz(Quiz newQuiz) {
        boolean duplicateContent = quizzes.stream().anyMatch(quiz -> quiz.isSameContent(newQuiz));
        if (duplicateContent) {
            throw new BusinessException(ExceptionConstants.QP3);
        }

        quizzes.add(newQuiz);
    }

    public Optional<Quiz> findByQuizContent(QuizContent content) {
        return quizzes.stream().filter(quiz -> quiz.getContent().equals(content)).findAny();
    }

    public long quizSize() {
        if (CollectionUtils.isEmpty(quizzes)) {
            return 0;
        }

        return quizzes.size();
    }

    public Optional<Quiz> findById(long quizId) {
        return quizzes.stream().filter(quiz -> quiz.getId().equals(quizId)).findAny();
    }

    public boolean containsQuiz(long quizId) {
        return quizzes.stream()
                .map(Quiz::getId).collect(Collectors.toSet())
                .contains(quizId);
    }
}