package com.example.rqs.core.space;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Space {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    private String title;

    private boolean visibility;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private final List<SpaceMember> spaceMemberList = new ArrayList<>();

    protected Space(){}

    private Space(String title, boolean visibility) {
        this.title = title;
        this.visibility = visibility;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Space newSpace(String title, boolean visibility) {
        return new Space(title, visibility);
    }

    public void addMember(SpaceMember spaceMember) {
        this.spaceMemberList.add(spaceMember);
    }

    protected void updateTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
}
