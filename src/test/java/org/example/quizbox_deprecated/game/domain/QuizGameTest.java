//package org.example.quizbox.game.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.example.quizbox.quiz.QuizBuilder.quizBuilder;
//import static org.example.quizbox.support.QuizPackBuilder.quizPackBuilder;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import org.example.quizbox.common.domain.exception.BusinessException;
//import org.example.quizbox.common.domain.exception.ExceptionConstants;
//import org.example.quizbox.quiz.domain.Answer;
//import org.example.quizbox.quiz.domain.Quiz;
//import org.example.quizbox.quiz.domain.QuizPack;
//import org.example.quizbox.quiz.domain.QuizPackMember;
//import org.example.quizbox.quiz.domain.QuizPackMembers;
//import org.example.quizbox.tags.QuizGameTag;
//import org.junit.jupiter.api.Test;
//
//class QuizGameTest {
//
//    // TODO 테스트 코드에서 사용되는 멤버 정보 빌러로 빼서 리팩토링 진행하기
//    private final SequentialGameQuizPicker sequentialGameQuizPicker = new SequentialGameQuizPicker();
//
//    @Test
//    @QuizGameTag
//    void 퀴즈팩을_통해_게임을_시작() {
//        assertThat(new QuizGame(quizPackBuilder().build(), sequentialGameQuizPicker, 1L)).isNotNull();
//    }
//
//    @Test
//    @QuizGameTag
//    void 퀴즈팩이_없는_경우_게임_불가() {
//        Throwable throwable = catchThrowable(() -> new QuizGame(null, sequentialGameQuizPicker, 1L));
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG1.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 게임에서_문제_뽑기_가능() {
//        Quiz quiz = quizBuilder().id(1L).build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//
//        assertThat(quizGame.pick(1L)).contains(quiz);
//    }
//
//    @Test
//    @QuizGameTag
//    void 뽑을_문제가_없는_경우_빈_옵셔널_객체를_반환() {
//        QuizGame quizGame = new QuizGame(quizPackBuilder().build(), sequentialGameQuizPicker, 1L);
//        assertThat(quizGame.pick(1L)).isEmpty();
//    }
//
//    @Test
//    @QuizGameTag
//    void 퀴즈팩_멤버가_아닌_경우_문제_뽑기_불가() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//
//        Throwable throwable = catchThrowable(() -> quizGame.pick(-999L));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QP4.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 퀴즈_참가자가_아닌_경우_문제_뽑기_불가() {
//        Quiz quiz = quizBuilder().id(1L).build();
//        QuizPack quizPack = quizPackBuilder()
//                .quizzes(List.of(quiz))
//                .quizPackMembers(
//                        new QuizPackMembers(
//                                QuizPackMember.createAdmin(1L),
//                                QuizPackMember.createAdmin(2L))
//                ).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//
//        Throwable throwable = catchThrowable(() -> quizGame.pick(2L));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG9.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 퀴즈팩_멤버가_아닌_경우_답변_불가() {
//        Quiz quiz = quizBuilder().id(1L).build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//        quizGame.pick(1L);
//        Set<Long> answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(
//                Collectors.toSet());
//
//        Throwable throwable = catchThrowable(
//                () -> quizGame.submit(-999L, new SubmitAnswer(answerIds)));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QP4.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 참가자가_아닌_경우_답변_불가() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder()
//                .quizzes(List.of(quiz))
//                .quizPackMembers(
//                        new QuizPackMembers(
//                                QuizPackMember.createAdmin(1L),
//                                QuizPackMember.createAdmin(2L))
//                ).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//        quizGame.pick(1L);
//        Set<Long> answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(
//                Collectors.toSet());
//
//        Throwable throwable = catchThrowable(
//                () -> quizGame.submit(2, new SubmitAnswer(answerIds)));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG9.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 퀴즈의_보기가_아닌_값은_답_제출_불가() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//        SubmitAnswer submitAnswer = new SubmitAnswer(Set.of(-999L));
//        quizGame.pick(1L);
//
//        Throwable throwable = catchThrowable(() -> quizGame.submit(1L, submitAnswer));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG4.code());
//    }
//
//    /**
//     * 퀴즈 1개, 퀴즈 1개 뽑고 답변 제출한 상태
//     */
//    @Test
//    @QuizGameTag
//    void 퀴즈게임의_진행_상태롤_알_수_있다() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//        Set<Long> answerIds = quiz.getQuizAnswers().answers().stream().map(Answer::getId).collect(Collectors.toSet());
//        SubmitAnswer submitAnswer = new SubmitAnswer(answerIds);
//        quizGame.pick(1L);
//        quizGame.submit(1L, submitAnswer);
//
//        assertThat(quizGame.status())
//                .hasFieldOrPropertyWithValue("quizGameId", quizGame.id())
//                .hasFieldOrPropertyWithValue("quizPackId", quizPack.getId())
//                .hasFieldOrPropertyWithValue("participantId", 1L)
//                .hasFieldOrPropertyWithValue("totalQuizCount", (int) quizPack.quizSize())
//                .hasFieldOrPropertyWithValue("remainQuizCount", (int) quizPack.quizSize() - 1)
//                .hasFieldOrPropertyWithValue("submittedAnswerCount", 1);
//    }
//
//    @Test
//    @QuizGameTag
//    void 답변_대기중인_경우_다음_문제_뽑기_불가() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//        quizGame.pick(1L);
//
//        Throwable throwable = catchThrowable(() -> quizGame.pick(1L));
//
//        assertThat(throwable).isInstanceOf(BusinessException.class)
//                .hasMessage(ExceptionConstants.QG2.code());
//    }
//
//    @Test
//    @QuizGameTag
//    void 답변_대기중인_문제가_없는_경우_새로운_퀴즈_뽑기_가능() {
//        Quiz quiz = quizBuilder().build();
//        QuizPack quizPack = quizPackBuilder().quizzes(List.of(quiz)).build();
//        QuizGame quizGame = new QuizGame(quizPack, sequentialGameQuizPicker, 1L);
//
//        assertThat(quizGame.pick(1L)).isNotEmpty();
//    }
//}
