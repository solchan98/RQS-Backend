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

    // TODO: 아이템 업데이트 재구현 후 다시 오픈
//    @Test()
//    @DisplayName("아이템 컨텐츠 업데이트 테스트")
//    void testUpdateItemContent() {
//        Space space = mock(Space.class);
//        SpaceMember isCreator = mock(SpaceMember.class);
//        given(isCreator.getSpace()).willReturn(space);
//        Quiz quiz = Quiz.newQuiz(isCreator, "수정 전, 질문!", List.of(CreateAnswer.of("answer", true)), "", "수정, 전, 힌트");
//        LocalDateTime beforeUpdate = quiz.getUpdatedAt();
//
//        quiz.updateContent(
//                "수정 후, 질문!",
//                "수정 후, 답변!",
//                "수정 후, 힌트!");
//
//        assertAll(
//                () -> assertThat(quiz.getQuestion()).isEqualTo("수정 후, 질문!"),
//                () -> assertThat(quiz.getAnswers()).isEqualTo("수정 후, 답변!"),
//                () -> assertThat(quiz.getHint()).isEqualTo("수정 후, 힌트!"),
//                () -> assertThat(quiz.getUpdatedAt()).isNotEqualTo(beforeUpdate)
//        );
//    }
}
