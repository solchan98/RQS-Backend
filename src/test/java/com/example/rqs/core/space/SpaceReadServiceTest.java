package com.example.rqs.core.space;

import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.member.Member;
import com.example.rqs.core.space.repository.SpaceRepository;
import com.example.rqs.core.space.service.SpaceReadServiceImpl;
import com.example.rqs.core.space.service.dtos.ReadSpace;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("Space Read Service Test")
public class SpaceReadServiceTest {

    @InjectMocks
    SpaceReadServiceImpl spaceReadService;

    @Mock
    SpaceRepository spaceRepository;

    @Mock
    SpaceMemberReadService smReadService;

    @Test
    @DisplayName("getSpace() - 스페이스가 없는 경우")
    void getSpaceWhenNotExistSpaceTest() {
        // given
        ReadSpace mockReadSpace = mock(ReadSpace.class);
        given(mockReadSpace.getSpaceId()).willReturn(1L);
        given(spaceRepository.findById(1L)).willReturn(Optional.empty());

        // when then
        assertThrows(
                BadRequestException.class,
                () -> spaceReadService.getSpace(mockReadSpace));
    }

    @Test
    @DisplayName("getSpace() - 비공개 + 스페이스 유저가 아닌 경우")
    void getSpaceWhenMemberIsSpaceMemberTest() {
        // given
        Member mockMember = mock(Member.class);
        given(mockMember.getMemberId()).willReturn(1L);
        ReadSpace readSpace = ReadSpace.of(mockMember, 1L);
        Space space = Space.newSpace("Test Space", false);
        given(spaceRepository.findById(1L)).willReturn(Optional.of(space));
        given(smReadService.getSpaceMember(1L, 1L)).willReturn(Optional.empty());

        // when then
        assertThrows(
                ForbiddenException.class,
                () -> spaceReadService.getSpace(readSpace));
    }
}
