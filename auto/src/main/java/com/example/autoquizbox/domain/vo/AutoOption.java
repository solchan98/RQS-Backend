package com.example.autoquizbox.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AutoOption {

    private String content;

    private boolean correct;

    @JsonCreator
    public AutoOption(
            @JsonProperty("content") String content,
            @JsonProperty("correct") boolean correct
    ) {
        this.content = content;
        this.correct = correct;
    }
}
