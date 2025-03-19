package org.example.quizbox.support;

import org.example.quizbox.keyword.domain.Keyword;
import org.example.quizbox.quiz.domain.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.quizbox.support.QuizBuilder.quizBuilder;

public class QuizPackBuilder {

    private final QuizPackMember defaultMember = new QuizPackMember(-1, QuizPackMemberRole.ADMIN);


    private Long id;
    private String title = "default title";
    private Quizzes quizzes = new Quizzes(
            Set.of(
                    quizBuilder().build(defaultMember)
            )
    );
    private QuizPackMembers quizPackMembers = new QuizPackMembers(
            Set.of(
                    defaultMember
            )
    );
    private QuizPackKeywords keywords;

    public static QuizPackBuilder quizPackBuilder() {
        return new QuizPackBuilder();
    }

    public QuizPackBuilder id(long id) {
        this.id = id;
        return this;
    }

    public QuizPackBuilder title(String title) {
        this.title = title;
        return this;
    }

    public QuizPackBuilder quizzes(Collection<Quiz> quizzes) {
        this.quizzes = new Quizzes(new HashSet<>(quizzes));
        return this;
    }

    public QuizPackBuilder quizPackMembers(QuizPackMembers quizPackMembers) {
        this.quizPackMembers = quizPackMembers;
        return this;
    }

    public QuizPackBuilder quizPackMembers(QuizPackMember... quizPackMembers) {
        this.quizPackMembers = new QuizPackMembers(Set.of(quizPackMembers));
        return this;
    }

    public QuizPackBuilder keywords(Set<Long> keywordIds) {
        this.keywords.addAll(
                keywordIds.stream()
                        .map(keywordId -> new Keyword(keywordId, ""))
                        .collect(Collectors.toSet())
        );
        return this;
    }

    public QuizPack build() {
        return QuizPack.of(title, quizPackMembers, quizzes, keywords);
    }
}
