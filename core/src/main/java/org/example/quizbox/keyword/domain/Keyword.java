package org.example.quizbox.keyword.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Keyword(String name) {
        this.name = name;
    }

    public Keyword(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

}
