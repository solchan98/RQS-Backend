package org.example.quizbox.tag.domain;

import java.util.stream.Collectors;
import lombok.Getter;
import org.example.quizbox.game.application.GameService;

import java.util.HashSet;
import java.util.Set;

public class Tags {

    private final Set<Tag> values;

    public Tags () {
        this.values = new HashSet<>();
    }

    public Tags(Set<Tag> values) {
        this.values = values;
    }

    public boolean contains(String tagName) {
        return values.stream().anyMatch(tag -> tag.equalsName(tagName));
    }

    public void addAll(Tags tags) {
        values.addAll(new HashSet<>(tags.values));
    }

    public long size() {
        return values.size();
    }

    public Tags getByIds(Set<Long> ids) {
        return new Tags(values.stream()
                .filter(tag -> ids.contains(tag.getId()))
                .collect(Collectors.toSet()));
    }

    public Set<Tag> getValues() {
        return new HashSet<>(values);
    }
}
