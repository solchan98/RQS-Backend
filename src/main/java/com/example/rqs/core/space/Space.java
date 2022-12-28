package com.example.rqs.core.space;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.spacemember.SpaceMember;
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

    private String content;

    private String url;

    private boolean visibility;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private final List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private final List<SpaceMember> spaceMemberList = new ArrayList<>();

    protected Space(){}

    private Space(String title, String content, String url, boolean visibility) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.visibility = visibility;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public static Space newSpace(String title, boolean visibility) {
        return new Space(title, null, null, visibility);
    }

    public static Space newSpace(String title, String content, boolean visibility) {
        return new Space(title, content, null, visibility);
    }

    public static Space newSpace(String title, String content, String url, boolean visibility) {
        return new Space(title, content, url, visibility);
    }

    public void addMember(SpaceMember spaceMember) {
        this.spaceMemberList.add(spaceMember);
    }

    public void updateTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }
}
