package org.example.quizbox.game.domain;

import static org.assertj.core.api.Assertions.assertThat;

class SubmittedGameQuizzesTest {
//
//    @Test
//    void 동일_퀴즈에_대하여_중복_제출_불가() {
//        SubmittedGameQuizzes submittedGameQuizzes = new SubmittedGameQuizzes();
//        Quiz quiz = quizBuilder().build();
//        Set<Long> optionIds = quiz.getOptions().options().stream().map(Option::getId).collect(Collectors.toSet());
//        SubmitOption submitOption = new SubmitOption(optionIds);
//        submittedGameQuizzes.submitOptions(new GameQuiz(quiz), submitOption);
//
//        Throwable throwable = catchThrowable(() -> submittedGameQuizzes.submitOptions(new GameQuiz(quiz), submitOption));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG5.code());
//    }
//
//    @Test
//    void 정답_개수_확인_가능() {
//        SubmittedGameQuizzes submittedGameQuizzes = new SubmittedGameQuizzes();
//        Quiz quiz1 = quizBuilder()
//                .options(
//                        new Option(1L, "A", true),
//                        new Option(2L, "B", true),
//                        new Option(3L, "C", false)
//                )
//                .build();
//        Set<Long> quiz1CorrectOptionIds = quiz1.getOptions().correctOptions().stream().map(Option::getId).collect(Collectors.toSet());
//        Quiz quiz2 = quizBuilder()
//                .options(
//                        new Option(4L, "E", true),
//                        new Option(5L, "F", false)
//                ).build();
//        Set<Long> quiz2WrongOptionIds = quiz2.getOptions().wrongOptions().stream().map(Option::getId).collect(Collectors.toSet());
//        submittedGameQuizzes.submitOptions(new GameQuiz(quiz1), new SubmitOption(quiz1CorrectOptionIds));
//        submittedGameQuizzes.submitOptions(new GameQuiz(quiz2), new SubmitOption(quiz2WrongOptionIds));
//
//        assertThat(submittedGameQuizzes.matchCount()).isEqualTo(1);
//    }

}
