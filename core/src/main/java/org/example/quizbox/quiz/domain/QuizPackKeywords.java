package org.example.quizbox.quiz.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.quizbox.keyword.domain.Keyword;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizPackKeywords {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_pack_id")
    private Set<QuizPackKeyword> values = new HashSet<>();

    public QuizPackKeywords(Set<QuizPackKeyword> values) {
        this.values = values;
    }

    public Set<Long> keywordIds() {
        return values.stream()
                .map(QuizPackKeyword::getKeywordId)
                .collect(Collectors.toSet());
    }

    public void addAll(Set<Keyword> keywords) {
        Set<Long> keywordIds = keywordIds();

        keywords.stream()
                .filter(keyword -> !keywordIds.contains(keyword.getId()))
                .forEach(keyword -> values.add(new QuizPackKeyword(keyword.getId())));
    }
}
