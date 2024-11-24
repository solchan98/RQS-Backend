package org.example.quizbox.quiz.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackMembers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class QuizPackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "quizPackEntity", cascade = CascadeType.ALL)
    private List<QuizEntity> quizEntities = new ArrayList<>();

    @OneToMany(mappedBy = "quizPackEntity", cascade = CascadeType.ALL)
    private Set<QuizPackMemberEntity> quizPackMemberEntities = new HashSet<>();

    @OneToMany(mappedBy = "quizPackEntity")
    private Set<QuizPackTagEntity> tags = new HashSet<>();

    public QuizPack toDomain() {
        QuizPackMembers quizPackMembers = new QuizPackMembers(
                quizPackMemberEntities.stream().map(QuizPackMemberEntity::toDomain).collect(Collectors.toSet()));

        return new QuizPack(
                id,
                title,
                quizPackMembers,
                new ArrayList<>(quizEntities.stream().map(QuizEntity::toDomain).toList()),
                tags.stream().map(QuizPackTagEntity::getTagId).collect(Collectors.toSet())
        );
    }

    public static QuizPackEntity fromDomain(QuizPack quizPack) {
        QuizPackEntity quizPackEntity = new QuizPackEntity();

        quizPackEntity.id = quizPack.getId();
        quizPackEntity.title = quizPack.getTitle();
        if (quizPack.getQuizzes() != null) {
            quizPackEntity.quizEntities = quizPack.getQuizzes().stream().map(QuizEntity::fromDomain).toList();
            quizPackEntity.quizEntities.forEach(quizEntity -> quizEntity.setQuizPackEntity(quizPackEntity));
        }

        quizPackEntity.quizPackMemberEntities = quizPack.getQuizPackMembers().values().stream()
                .map(QuizPackMemberEntity::fromDomain).collect(Collectors.toSet());
        quizPackEntity.quizPackMemberEntities.forEach(
                quizPackMemberEntity -> quizPackMemberEntity.setQuizPackEntity(quizPackEntity));

        return quizPackEntity;
    }
}
