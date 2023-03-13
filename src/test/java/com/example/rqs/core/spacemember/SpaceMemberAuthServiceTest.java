package com.example.rqs.core.spacemember;

import com.example.rqs.core.spacemember.service.SpaceMemberAuthServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpaceMember Auth Service Test")
public class SpaceMemberAuthServiceTest {

    @InjectMocks
    SpaceMemberAuthServiceImpl spaceMemberAuthService;

    @Test
    @DisplayName("isSpaceCreator() - 생성자인 경우")
    // TODO: Creator != ADMIN 이슈 변경 시 수정 예정
    void isSpaceCreatorWhenIsCreator() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.ADMIN);

        // when
        boolean isCreator = spaceMemberAuthService.isSpaceCreator(mockSM);

        // then
        assertThat(isCreator).isTrue();
    }

    @Test
    @DisplayName("isSpaceCreator() - 생성자가 아닌 경우")
        // TODO: Creator != ADMIN 이슈 변경 시 수정 예정
    void isSpaceCreatorWhenIsNotCreator() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isCreator = spaceMemberAuthService.isSpaceCreator(mockSM);

        // then
        assertThat(isCreator).isFalse();
    }

    @Test
    @DisplayName("isCreatableItem() - 권한이 MEMBER인 경우")
    void isCreatableItemWhenRoleIsMember() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isCreatableItem = spaceMemberAuthService.isCreatableItem(mockSM);

        // then
        assertThat(isCreatableItem).isTrue();
    }

    @Test
    @DisplayName("isReadableSpaceMembers() - 권한이 ADMIN인 경우")
    void isReadableSpaceMemberListWhenRoleIsADMIN() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.ADMIN);

        // when
        boolean isReadableSpaceMemberList = spaceMemberAuthService.isReadableSpaceMembers(mockSM);

        // then
        assertThat(isReadableSpaceMemberList).isTrue();
    }

    @Test
    @DisplayName("isReadableSpaceMembers() - 권한이 MEMBER인 경우")
    void isReadableSpaceMemberListWhenRoleIsMember() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isReadableSpaceMemberList = spaceMemberAuthService.isReadableSpaceMembers(mockSM);

        // then
        assertThat(isReadableSpaceMemberList).isFalse();
    }

    @Test
    @DisplayName("isUpdatableSpace() - 권한이 ADMIN인 경우")
    void isUpdatableSpaceWhenRoleIsAdmin() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.ADMIN);

        // when
        boolean isUpdatableSpace = spaceMemberAuthService.isUpdatableSpace(mockSM);

        // then
        assertThat(isUpdatableSpace).isTrue();
    }

    @Test
    @DisplayName("isUpdatableSpace() - 권한이 MEMBER인 경우")
    void isUpdatableSpaceWhenRoleIsMember() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isUpdatableSpace = spaceMemberAuthService.isUpdatableSpace(mockSM);

        // then
        assertThat(isUpdatableSpace).isFalse();
    }

    @Test
    @DisplayName("isUpdatableSpaceMemberRole() - 권한이 ADMIN인 경우")
    void isUpdatableSpaceMemberRoleWhenRoleIsAdmin() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.ADMIN);

        // when
        boolean isUpdatableSpaceMemberRole = spaceMemberAuthService.isUpdatableSpaceMemberRole(mockSM);

        // then
        assertThat(isUpdatableSpaceMemberRole).isTrue();
    }

    @Test
    @DisplayName("isUpdatableSpaceMemberRole() - 권한이 MEMBER인 경우")
    void isUpdatableSpaceMemberRoleWhenRoleIsMember() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isUpdatableSpaceMemberRole = spaceMemberAuthService.isUpdatableSpaceMemberRole(mockSM);

        // then
        assertThat(isUpdatableSpaceMemberRole).isFalse();
    }

    @Test
    @DisplayName("isDeletableSpace() - 권한이 ADMIN인 경우")
    void isDeletableSpaceWhenRoleIsAdmin() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.ADMIN);

        // when
        boolean isDeletableSpace = spaceMemberAuthService.isDeletableSpace(mockSM);

        // then
        assertThat(isDeletableSpace).isTrue();
    }

    @Test
    @DisplayName("isDeletableSpace() - 권한이 MEMBER인 경우")
    void isDeletableSpaceWhenRoleIsMember() {
        // given
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.getRole()).willReturn(SpaceRole.MEMBER);

        // when
        boolean isDeletableSpace = spaceMemberAuthService.isDeletableSpace(mockSM);

        // then
        assertThat(isDeletableSpace).isFalse();
    }
}
