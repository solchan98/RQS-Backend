package org.example.quizbox.quiz.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.quiz.domain.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CreateSimpleQuizPack {

    private String title;
    private List<CreateSimpleQuiz> quizzes;
    private List<String> keywords;

    public QuizPack toQuizPack(long memberId, Set<Keyword> keywords) {
        QuizPackMember quizPackMember = new QuizPackMember(memberId, QuizPackMemberRole.ADMIN);
        QuizPackMembers quizPackMembers = new QuizPackMembers(Set.of(quizPackMember));
        Quizzes quizPackQuizzes = new Quizzes(
                quizzes.stream()
                        .map(createSimpleQuiz -> createSimpleQuiz.toQuiz(quizPackMember))
                        .collect(Collectors.toSet())
        );
        QuizPackKeywords quizPackKeywords = new QuizPackKeywords(keywords.stream()
                .map(keyword -> new QuizPackKeyword(keyword.getId()))
                .collect(Collectors.toSet()));

        return QuizPack.of(title, quizPackMembers, quizPackQuizzes, quizPackKeywords, memberId);
    }

    @Getter
    @AllArgsConstructor
    public static class CreateSimpleQuiz {
        private String content;
        private List<CreateSimpleOption> options;

        public Quiz toQuiz(QuizPackMember quizPackMember) {
            Set<Option> options = this.options.stream()
                    .map(CreateSimpleOption::toOption)
                    .collect(Collectors.toSet());

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
