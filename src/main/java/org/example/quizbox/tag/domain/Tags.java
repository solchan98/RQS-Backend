package org.example.quizbox.tag.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
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
}
