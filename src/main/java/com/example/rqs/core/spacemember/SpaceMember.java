package com.example.rqs.core.spacemember;

import com.example.rqs.core.item.Item;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.Space;
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

    public static SpaceMember of(Member member, Space space, SpaceRole spaceRole) {
        return new SpaceMember(member, space, spaceRole);
    }

    public static SpaceMember newSpaceAdmin(Member member, Space space) {
        return new SpaceMember(member, space, SpaceRole.ADMIN);
    }

    public static SpaceMember newSpaceMember(Member member, Space space) {
        return new SpaceMember(member, space, SpaceRole.MEMBER);
    }

    public Space updateSpaceTitle(String title) {
        this.space.updateTitle(title);
        return this.space;
    }

    public void updateRole(SpaceRole role) {
        this.role = role;
    }
}
