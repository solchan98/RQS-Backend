package org.example.quizbox.quiz.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.quiz.domain.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CreateSimpleQuizPack {

    private String title;
    private List<CreateSimpleQuiz> quizzes;

    public QuizPack toQuizPack(long memberId) {
        QuizPack quizPack = new QuizPack(title, Set.of(memberId), Set.of());
        QuizPackMember quizPackMember = quizPack.getQuizPackMemberBy(memberId);
        quizPack.setQuizzes(
                this.quizzes.stream().map(quiz -> quiz.toQuiz(quizPackMember)).collect(Collectors.toSet())
        );

        return quizPack;
    }

    @Getter
    @AllArgsConstructor
    public static class CreateSimpleQuiz {
        private String content;
        private List<CreateSimpleOption> options;

        public Quiz toQuiz(QuizPackMember quizPackMember) {
            Set<Option> options = this.options.stream().map(CreateSimpleOption::toOption).collect(Collectors.toSet());
            return new Quiz(quizPackMember, new QuizContent(content), options);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CreateSimpleOption {
        private String content;
        private boolean correct;

        public Option toOption() {
            return new Option(content, correct);
        }

    }
}
