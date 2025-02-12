package org.example.quizbox.quiz.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 * TODO: BeanValidation 적용하기
 */
@Getter
public class AddQuizAutoCreateTask {

    private String quizPackTitle;

    private String base64File;

    private String fileMineType;

    @JsonCreator
    public AddQuizAutoCreateTask(
            @JsonProperty("quizPackTitle") String quizPackTitle,
            @JsonProperty("base64File") String base64File,
            @JsonProperty("fileMineType") String fileMineType
    ) {
        /**
         * TODO
         *  1. base64File만 받도록 수정
         *  2. base64 검증 및 mine_type 추추하여 사용
         */
        this.quizPackTitle = quizPackTitle;
        this.base64File = base64File;
        this.fileMineType = fileMineType;
    }
}
