package com.example.autoquizbox.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class AutoQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id")
    private List<AutoOption> options;


    @JsonCreator
    public AutoQuiz(
            @JsonProperty("content") String content,
            @JsonProperty("options") List<AutoOption> options,
            @JsonProperty("description") String description
    ) {
        this.content = content;
        this.options = options;
        this.description = description;
    }
}
