package org.example.quizbox.quiz.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.example.quizbox.common.domain.exception.BusinessException;

import java.util.Set;

import static org.example.quizbox.common.domain.exception.ExceptionConstants.QP8;


/**
 * TODO: BeanValidation 적용하기
 */
@Getter
public class CreateQuizPackV2 {

    private String title;

    private Set<String> keywords; // keyword == tag

    @JsonCreator
    public CreateQuizPackV2(
            @JsonProperty("title") String title,
            @JsonProperty("keywords") Set<String> keywords
    ) {
        if (keywords == null || keywords.isEmpty() || keywords.size() > 3) {
            throw new BusinessException(QP8);
        }
        this.keywords = keywords;
        this.title = title;
    }
}
