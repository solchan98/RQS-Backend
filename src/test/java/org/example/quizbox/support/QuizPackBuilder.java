package org.example.quizbox.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.quiz.domain.QuizPackMemberRole;
import org.example.quizbox.quiz.domain.QuizPackMembers;

public class QuizPackBuilder {

    // TEST 용도
    private static Long quizPackMemberId = 0L;

    private Long id;
    private String title = "default title";
    private Collection<Quiz> quizzes = new ArrayList<>();
    private QuizPackMembers quizPackMembers;

    public static QuizPackBuilder quizPackBuilder() {
        return new QuizPackBuilder();
    }

    public QuizPackBuilder id(long id) {
        this.id = id;
        return this;
    }

    ;

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

    public QuizPack build() {
        if (Objects.isNull(quizPackMembers)) {
            quizPackMembers = new QuizPackMembers(
                    new QuizPackMember(1L, --quizPackMemberId, QuizPackMemberRole.allRoles()));
        }

        return new QuizPack(id, quizPackMembers, title, quizzes);
    }
}
