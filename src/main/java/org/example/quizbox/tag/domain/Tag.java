package org.example.quizbox.tag.domain;

import lombok.Getter;

@Getter
public class Tag {

    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

}
