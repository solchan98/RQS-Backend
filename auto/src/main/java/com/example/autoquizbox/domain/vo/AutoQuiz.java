package com.example.autoquizbox.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AutoQuiz {

    private String content;

    private String description;

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
