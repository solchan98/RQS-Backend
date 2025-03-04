package com.example.autoquizbox.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.tika.Tika;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;

import java.util.Base64;
import java.util.Set;

@Getter
public class AutoTaskRequest {

    private String quizPackTitle;

    private String content;

    private String mimeType;

    private Set<String> keywords;

    @JsonCreator
    public AutoTaskRequest(
            @JsonProperty("quizPackTitle") String quizPackTitle,
            @JsonProperty("content") String content,
            @JsonProperty("mimeType") String mimeType,
            @JsonProperty("keywords") Set<String> keywords
    ) {
        this.quizPackTitle = quizPackTitle;
        this.content = content;
        this.keywords = keywords;

        try {
            // TODO
            Tika tika = new Tika();
            byte[] decodedBytes = Base64.getDecoder().decode(content);
            MimeType mimeTypeObj = MimeType.valueOf(tika.detect(decodedBytes));
            this.mimeType = mimeTypeObj.getType()
                    .concat("/")
                    .concat(mimeTypeObj.getSubtype());
        } catch (InvalidMimeTypeException e) {
            // TODO:
            throw new RuntimeException("파일 형식을 확인하세요.");
        }
    }
}
