package com.example.rqs.core.quiz;

import com.example.rqs.core.quiz.service.dtos.CreateAnswer;
import com.example.rqs.core.space.Space;
import com.example.rqs.core.spacemember.SpaceMember;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class Quiz {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "spaceId")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "space_member_id", referencedColumnName = "spaceMemberId")
    private SpaceMember spaceMember;

    @OneToMany(mappedBy = "quiz",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Column(columnDefinition = "TEXT")
    private String question;

    private String type; // TODO: will enum

    private String hint; // TODO: will table

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected Quiz() {}

    private Quiz(Space space, SpaceMember spaceMember, String question, List<CreateAnswer> createAnswers, String type, String hint) {
        this.space = space;
        this.spaceMember = spaceMember;
        this.question = question;
        this.answers = createAnswers.stream().map(ca -> Answer.of(this, ca.getAnswer(), ca.isCorrect())).collect(Collectors.toList());
        this.type = type;
        this.hint = hint;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Quiz newQuiz(SpaceMember spaceMember, String question, List<CreateAnswer> createAnswers, String type, String hint) {
        return new Quiz(spaceMember.getSpace(), spaceMember, question, createAnswers, type, hint);
    }

    // TODO: Quiz update
    public void updateContent(String question, String answer, String hint) {
        this.question = question;
//        this.answer = answer;
        this.hint = hint;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCreator(SpaceMember spaceMember) {
        return this.spaceMember.getSpaceMemberId().equals(spaceMember.getSpaceMemberId());
    }
}
