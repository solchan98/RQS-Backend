package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.quizbox.common.domain.exception.BusinessException;
import org.example.quizbox.common.domain.exception.ExceptionConstants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor
public class Options {

    private static final long MIN_SIZE_OF_OPTION = 2;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "option_id")
    private Set<Option> values = new HashSet<>();

    public Options(Set<Option> values) {
        if (values == null || values.size() < MIN_SIZE_OF_OPTION) {
            throw new BusinessException(ExceptionConstants.QA1);
        }

        boolean containsCorrectOption = values.stream().anyMatch(Option::isCorrect);
        if (!containsCorrectOption) {
            throw new BusinessException(ExceptionConstants.QA2);
        }
        this.values = values;
    }

    public boolean match(Set<Long> submitOptionIds) {
        Set<Long> collectOptionIds = correctOptions().stream().map(Option::getId).collect(Collectors.toSet());

        return collectOptionIds.containsAll(submitOptionIds) && collectOptionIds.size() == submitOptionIds.size();
    }

    public Set<Option> options() {
        return new HashSet<>(values);
    }

    public boolean containsAll(Set<Long> submitOptionIds) {
        Set<Long> quizOptionIds = options().stream()
                .map(Option::getId)
                .collect(Collectors.toSet());

        return quizOptionIds.containsAll(submitOptionIds);
    }

    public Set<Option> correctOptions() {
        return values.stream().filter(Option::isCorrect).collect(Collectors.toSet());
    }

    public Set<Option> wrongOptions() {
        return values.stream().filter(option -> !option.isCorrect()).collect(Collectors.toSet());
    }
}
