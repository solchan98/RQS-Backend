package com.example.rqs.core.space;

import com.example.rqs.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("스페이스 멤버 테스트")
public class SpaceMemberTest {

    @Test
    @DisplayName("업데이트 권한 여부 확인 테스트")
    void testIsUpdatableWhenRoleIsMember() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberUpdatable = spaceMember.isUpdatable();
        boolean adminUpdatable = spaceAdmin.isUpdatable();

        assertAll(
                () -> assertThat(memberUpdatable).isFalse(),
                () -> assertThat(adminUpdatable).isTrue()
        );
    }

    @Test
    @DisplayName("아이템 생성 권한 여부 확인 테스트")
    void testIsCreatableWhenRoleIsMember() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberCreatableItem = spaceMember.isCreatableItem();
        boolean adminCreatableItem = spaceAdmin.isCreatableItem();

        assertAll(
                () -> assertThat(memberCreatableItem).isFalse(),
                () -> assertThat(adminCreatableItem).isTrue()
        );
    }
}
