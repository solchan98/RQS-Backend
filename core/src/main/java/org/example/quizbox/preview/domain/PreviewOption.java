package org.example.quizbox.preview.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PreviewOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "correct")
    private boolean correct;

//    @JsonCreator
//    public PreviewOption(
//            @JsonProperty("content") String content,
//            @JsonProperty("correct") boolean correct
//    ) {
//        this.content = content;
//        this.correct = correct;
//    }
}
