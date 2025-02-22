package com.example.autoquizbox.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class AutoOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "correct")
    private boolean correct;

    @JsonCreator
    public AutoOption(
            @JsonProperty("content") String content,
            @JsonProperty("correct") boolean correct
    ) {
        this.content = content;
        this.correct = correct;
    }

    public AutoOption(Long id, String content, boolean correct) {
        this.id = id;
        this.content = content;
        this.correct = correct;
    }
}
