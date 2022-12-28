package com.example.rqs.core.spacemember;

import com.example.rqs.core.common.exception.ForbiddenException;
import com.example.rqs.core.spacemember.repository.SpaceMemberRepository;
import com.example.rqs.core.spacemember.service.SpaceMemberReadServiceImpl;
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
@DisplayName("SpaceMember Read Service Test")
public class SpaceMemberReadServiceTest {

    @InjectMocks
    SpaceMemberReadServiceImpl smReadService;

    @Mock
    SpaceMemberRepository smRepository;

    @Test
    @DisplayName("getSpaceMemberList() - 스페이스 멤버가 아닌 경우")
    void getSpaceMemberListWhenIsNotSpaceMember() {
        // given
        long memberId = 1L;
        long spaceId = 1L;
        given(smRepository.getSpaceMember(memberId, spaceId)).willReturn(Optional.empty());

        // when then
        assertThrows(ForbiddenException.class, () -> smReadService.getSpaceMemberList(memberId, spaceId));
    }

    @Test
    @DisplayName("getSpaceMemberList() - 권한이 존재하지 않는 경우")
    void getSpaceMemberListWhenIsNotReadableRole() {
        // given
        long memberId = 1L;
        long spaceId = 1L;
        SpaceMember mockSM = mock(SpaceMember.class);
        given(mockSM.isReadableSpaceMemberList()).willReturn(Boolean.FALSE);
        given(smRepository.getSpaceMember(memberId, spaceId)).willReturn(Optional.of(mockSM));

        // when then
        assertThrows(ForbiddenException.class, () -> smReadService.getSpaceMemberList(memberId, spaceId));
    }
}
