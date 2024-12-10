package org.example.quizbox.support;

import org.example.quizbox.quiz.domain2.*;

import java.util.*;
import java.util.stream.Collectors;

public class QuizPackBuilder {

    // TEST 용도
    private static Long quizPackMemberId = 0L;

    // TEST 용도
    private static Long quizPackId = 0L;

    private Long id;
    private String title = "default title";
    private Set<Quiz> quizzes = new HashSet<>();
    private QuizPackMembers quizPackMembers = new QuizPackMembers();
    private Set<QuizPackTag> tags = new HashSet<>();

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
        this.quizzes = new HashSet<>(quizzes);
        return this;
    }

    public QuizPackBuilder quizPackMembers(QuizPackMembers quizPackMembers) {
        this.quizPackMembers = quizPackMembers;
        return this;
    }

    public QuizPackBuilder quizPackMembers(QuizPackMember... quizPackMembers) {
        Arrays.stream(quizPackMembers).forEach(quizPackMember -> this.quizPackMembers.add(quizPackMember));
        return this;
    }

    public QuizPackBuilder tags(Set<Long> tagIds) {
        this.tags = tagIds.stream().map(QuizPackTag::new).collect(Collectors.toSet());
        return this;
    }

    public QuizPack build() {
        if (Objects.isNull(quizPackMembers)) {
            quizPackMembers = new QuizPackMembers(Set.of(new QuizPackMember(1L, --quizPackMemberId, QuizPackMemberRole.ADMIN)));
        }

        if (Objects.isNull(id)) {
            id = --quizPackId;
        }

        return new QuizPack(id, title, quizzes, quizPackMembers, tags);
    }
}
