package org.example.quizbox.support;

import java.util.*;

import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.domain.QuizPackMemberRole;
import org.example.quizbox.quiz.domain.QuizPackMembers;

public class QuizPackBuilder {

    // TEST 용도
    private static Long quizPackMemberId = 0L;

    // TEST 용도
    private static Long quizPackId = 0L;

    private Long id;
    private String title = "default title";
    private Collection<Quiz> quizzes = new ArrayList<>();
    private QuizPackMembers quizPackMembers;
    private Set<Long> tagIds = new HashSet<>();

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
        this.quizzes = new ArrayList<>(quizzes);
        return this;
    }

    public QuizPackBuilder quizPackMembers(QuizPackMembers quizPackMembers) {
        this.quizPackMembers = quizPackMembers;
        return this;
    }

    public QuizPackBuilder tagIds(Set<Long> tagIds) {
        this.tagIds = new HashSet<>(tagIds);
        return this;
    }

    public QuizPack build() {
        if (Objects.isNull(quizPackMembers)) {
            quizPackMembers = new QuizPackMembers(
                    new QuizPackMember(1L, --quizPackMemberId, QuizPackMemberRole.allRoles()));
        }

        if (Objects.isNull(id)) {
            id = --quizPackId;
        }

        return new QuizPack(id, title, quizPackMembers, quizzes, tagIds);
    }
}
