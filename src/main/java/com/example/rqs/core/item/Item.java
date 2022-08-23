package com.example.rqs.core.item;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "spaceId")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "space_member_id", referencedColumnName = "spaceMemberId")
    private SpaceMember spaceMember;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private String hint; // TODO: will table

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected Item() {}

    private Item(Space space, SpaceMember spaceMember, String question, String answer, String hint) {
        this.space = space;
        this.spaceMember = spaceMember;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Item newItem(Space space, SpaceMember spaceMember, String question, String answer, String hint) {
        return new Item(space, spaceMember, question, answer, hint);
    }

    public void updateContent(String question, String answer, String hint) {
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCreator(SpaceMember spaceMember) {
        return this.spaceMember.getSpaceMemberId().equals(spaceMember.getSpaceMemberId());
    }
}
