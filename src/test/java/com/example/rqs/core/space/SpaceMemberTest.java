package com.example.rqs.core.space;

import com.example.rqs.core.member.Member;
import com.example.rqs.core.spacemember.SpaceMember;
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
                () -> assertThat(memberCreatableItem).isTrue(),
                () -> assertThat(adminCreatableItem).isTrue()
        );
    }

    @Test
    @DisplayName("스페이스 멤버 권한 변경 권한 여부 확인 테스트")
    void testIsUpdatableSpaceMemberRole() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberUpdatableSpaceMemberRole = spaceMember.isUpdatableMemberRole();
        boolean adminUpdatableSpaceMemberRole = spaceAdmin.isUpdatableMemberRole();

        assertAll(
                () -> assertThat(memberUpdatableSpaceMemberRole).isFalse(),
                () -> assertThat(adminUpdatableSpaceMemberRole).isTrue()
        );
    }

    @Test
    @DisplayName("스페이스 멤버 삭제 권한 여부 확인 테스트")
    void testIsDeletableSpaceMember() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberUpdatableSpaceMemberRole = spaceMember.isDeletableSpaceMember();
        boolean adminUpdatableSpaceMemberRole = spaceAdmin.isDeletableSpaceMember();

        assertAll(
                () -> assertThat(memberUpdatableSpaceMemberRole).isFalse(),
                () -> assertThat(adminUpdatableSpaceMemberRole).isTrue()
        );
    }

    @Test
    @DisplayName("스페이스 멤버 전체 조회 권한 여부 확인 테스트")
    void testIsReadableSpaceMemberList() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberUpdatableSpaceMemberRole = spaceMember.isReadableSpaceMemberList();
        boolean adminUpdatableSpaceMemberRole = spaceAdmin.isReadableSpaceMemberList();

        assertAll(
                () -> assertThat(memberUpdatableSpaceMemberRole).isFalse(),
                () -> assertThat(adminUpdatableSpaceMemberRole).isTrue()
        );
    }

    @Test
    @DisplayName("스페이스 삭제 권한 여부 확인 테스트")
    void testIsDeletableSpace() {
        Member member = Member.newMember("sol@sol.com", "abcd1234!", "sol");
        Space space = Space.newSpace("임시 스페이스", false);
        SpaceMember spaceMember = SpaceMember.newSpaceMember(member, space);
        SpaceMember spaceAdmin = SpaceMember.newSpaceAdmin(member, space);

        boolean memberDeletableSpaceRole = spaceMember.isDeletableSpace();
        boolean adminDeletableSpaceRole = spaceAdmin.isDeletableSpace();

        assertAll(
                () -> assertThat(memberDeletableSpaceRole).isFalse(),
                () -> assertThat(adminDeletableSpaceRole).isTrue()
        );
    }

}
