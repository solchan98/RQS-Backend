package org.example.quizbox.keyword.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Keywords {

    private final Set<Keyword> values;

    public Keywords(List<Keyword> values) {
        this.values = new HashSet<>(values);
    }

    public boolean contains(String name) {
        return values.stream().anyMatch(keyword -> keyword.equalsName(name));
    }

    public void addAll(Keywords keywords) {
        values.addAll(new HashSet<>(keywords.values));
    }

    public long size() {
        return values.size();
    }

    public Keywords getByIds(Set<Long> ids) {
        return new Keywords(values.stream()
                .filter(keyword -> ids.contains(keyword.getId()))
                .toList()
        );
    }

    public Set<Keyword> readonlyValues() {
        return new HashSet<>(values);
    }
}
