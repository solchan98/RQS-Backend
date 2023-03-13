package com.example.rqs.core.quiz;

import com.example.rqs.core.quiz.service.dtos.CreateAnswer;
import com.example.rqs.core.spacemember.SpaceMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("아이템 테스트")
@ExtendWith(MockitoExtension.class)
public class QuizTest {

    @Test()
    @DisplayName("아이템의 생성자인지 확인 메서드 테스트 - 아닌 경우")
    void testIsCreatorWhenIsNotCreator() {
        SpaceMember isCreator = mock(SpaceMember.class);
        SpaceMember isNotCreator = mock(SpaceMember.class);
        given(isNotCreator.getSpaceMemberId()).willReturn(1L);
        given(isCreator.getSpaceMemberId()).willReturn(2L);
        given(isCreator.getSpace()).willReturn(null);
        Quiz quiz = Quiz.newQuiz(isCreator, "", List.of(CreateAnswer.of("answer", true)), "", "");

        boolean willIsCreator = quiz.isCreator(isCreator);
        boolean willIsNotCreator = quiz.isCreator(isNotCreator);

        assertAll(
                () -> assertThat(willIsCreator).isTrue(),
                () -> assertThat(willIsNotCreator).isFalse()
        );

    }
}
