package com.example.rqs.core.item;

import com.example.rqs.core.space.Space;
import com.example.rqs.core.space.SpaceMember;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "spaceId")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "space__member_id", referencedColumnName = "spaceMemberId")
    private SpaceMember spaceMember;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
