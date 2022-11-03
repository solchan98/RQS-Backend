package com.example.rqs.core.space;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class SpaceMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceMemberId;

    @OneToMany(mappedBy = "spaceMember", cascade = CascadeType.ALL)
    private final List<Item> itemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "spaceId")
    private Space space;

    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    private SpaceRole role;

    protected SpaceMember() {}

    private SpaceMember(Member member, Space space, SpaceRole role) {
        this.member = member;
        this.space = space;
        space.addMember(this);
        this.joinedAt = LocalDateTime.now();
        this.role = role;
    }

    public static SpaceMember newSpaceAdmin(Member member, Space space) {
        return new SpaceMember(member, space, SpaceRole.ADMIN);
    }

    public static SpaceMember newSpaceMember(Member member, Space space) {
        return new SpaceMember(member, space, SpaceRole.MEMBER);
    }

    public boolean isUpdatable() {
        return this.role.goe(SpaceRole.ADMIN);
    }

    public Space updateSpaceTitle(String title) {
        this.space.updateTitle(title);
        return this.space;
    }

    public boolean isCreator() {
        return this.role.goe(SpaceRole.ADMIN);
    }

    public boolean isCreatableItem() {
        return this.role.goe(SpaceRole.MEMBER);
    }

    public boolean isUpdatableMemberRole() {
        return this.role.goe(SpaceRole.ADMIN);
    }

    public void updateRole(SpaceRole role) {
        this.role = role;
    }

    public boolean isDeletableSpaceMember() {
        return this.role.goe(SpaceRole.ADMIN);
    }

    public boolean isReadableSpaceMemberList() {
        return this.role.goe(SpaceRole.ADMIN);
    }

    public boolean isDeletableSpace() {
        return this.role.goe(SpaceRole.ADMIN);
    }
}
