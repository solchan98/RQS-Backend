package com.example.rqs.core.space;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.spacemember.SpaceMember;
import com.example.rqs.core.spacemember.SpaceRole;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
public class Space {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;

    private String title;

    private String content;

    private String url;

    private boolean visibility;

    private String adminCode;

    private String memberCode;

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
        this.adminCode = UUID.randomUUID().toString().substring(0, 6);
        this.memberCode = UUID.randomUUID().toString().substring(0, 6);
    }


    public static Space newSpace(String title, boolean visibility) {
        return new Space(title, null, null, visibility);
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

    public void changeVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Map<SpaceRole, String> joinCode() {
        return Map.of(
                SpaceRole.ADMIN, adminCode,
                SpaceRole.MEMBER, memberCode
        );
    }

    public SpaceRole getRoleByJoinCode(String joinCode) {
        if (adminCode.equals(joinCode)) {
            return SpaceRole.ADMIN;
        }
        if (memberCode.equals(joinCode)) {
            return SpaceRole.MEMBER;
        }
        return SpaceRole.GUEST;
    }
}
