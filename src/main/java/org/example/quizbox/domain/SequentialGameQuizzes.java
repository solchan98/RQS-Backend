package org.example.quizbox.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SequentialGameQuizzes implements GameQuizzes {

    private final Map<Long, Quiz> quizzes;

    private final Deque<Long> notSubmittedQuizIds;

    private final Set<Long> submittedQuizIds = new HashSet<>();

    private long lastSubmittedQuizId = Long.MIN_VALUE;

    private SequentialGameQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes.stream().collect(Collectors.toMap(Quiz::getId, Function.identity()));
        this.notSubmittedQuizIds = new ArrayDeque<>(quizzes.stream().map(Quiz::getId).toList());
    }

    public static SequentialGameQuizzes from(Quiz... values) {
        return new SequentialGameQuizzes(Arrays.stream(values).toList());
    }

    public static SequentialGameQuizzes from(List<Quiz> values) {
        return new SequentialGameQuizzes(values);
    }

    @Override
    public boolean isEmpty() {
        return quizzes.isEmpty();
    }

    @Override
    public int size() {
        return quizzes.size();
    }

    @Override
    public boolean existsNextQuiz() {
        return !notSubmittedQuizIds.isEmpty();
    }

    @Override
    public Optional<Quiz> nextQuiz() {
        if (!existsNextQuiz()) {
            return Optional.empty();
        }
        Long nextQuizId = notSubmittedQuizIds.removeFirst();
        submittedQuizIds.add(nextQuizId);
        lastSubmittedQuizId = nextQuizId;

        return Optional.of(quizzes.get(nextQuizId));
    }

    @Override
    public Collection<Quiz> values() {
        return new ArrayList<>(quizzes.values());
    }

    @Override
    public boolean isSubmittableAnswers(long quizId) {
        return lastSubmittedQuizId == quizId;
    }

    @Override
    public int remainQuizCount() {
        return notSubmittedQuizIds.size();
    }

    @Override
    public Optional<Long> lastSubmittedQuizId() {
        if (lastSubmittedQuizId == Long.MIN_VALUE) {
            return Optional.empty();
        }

        return Optional.of(lastSubmittedQuizId);
    }
}
