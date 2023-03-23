package com.example.rqs.api.space;

import com.example.rqs.api.common.CommonAPI;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.*;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.space.service.*;
import com.example.rqs.core.space.service.dtos.*;
import com.example.rqs.core.spacemember.SpaceRole;
import com.example.rqs.core.spacemember.service.SpaceMemberAuthService;
import com.example.rqs.core.spacemember.service.SpaceMemberReadService;
import com.example.rqs.core.spacemember.service.SpaceMemberUpdateService;
import com.example.rqs.core.spacemember.service.dtos.DeleteSpaceMember;
import com.example.rqs.core.spacemember.service.dtos.SpaceMemberResponse;
import com.example.rqs.core.spacemember.service.dtos.UpdateSpaceMemberRole;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SpaceController {
    private static final String AUTH = "/my";
    private static final String DOMAIN = "/space";

    private final SpaceReadService spaceReadService;
    private final SpaceRegisterService spaceRegisterService;
    private final SpaceInviteService spaceInviteService;
    private final SpaceUpdateService spaceUpdateService;

    private final SpaceMemberReadService spaceMemberReadService;
    private final SpaceMemberUpdateService spaceMemberUpdateService;
    private final SpaceMemberAuthService spaceMemberAuthService;

    @PostMapping(AUTH + DOMAIN)
    public SpaceResponse createNewSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody CreateSpaceDto createSpaceDto
    ) {
        if (createSpaceDto.getTitle().isEmpty()) throw new BadRequestException();
        CreateSpace createSpace = CreateSpace.of(
                memberDetails.getMember(),
                createSpaceDto.getTitle(),
                createSpaceDto.getContent(),
                createSpaceDto.getUrl(),
                createSpaceDto.isVisibility());
        return spaceRegisterService.createSpace(createSpace);
    }

    @PatchMapping(AUTH + DOMAIN)
    public SpaceResponse updateSpaceTitle(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UpdateSpaceDto updateSpaceDto
    ) {
        if (updateSpaceDto.getTitle().isEmpty()) throw new BadRequestException();
        return spaceUpdateService.updateTitle(
                memberDetails.getMember(),
                updateSpaceDto.getSpaceId(),
                updateSpaceDto.getTitle());
    }

    @GetMapping(DOMAIN)
    @CommonAPI
    public SpaceResponse getSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        if (memberDetails == null) {
            return spaceReadService.getSpace(ReadSpace.of(spaceId));
        }

        return spaceReadService.getSpace(ReadSpace.of(memberDetails.getMember(), spaceId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<SpaceResponse> getAllSpace(
            @Nullable @RequestParam("lastCreatedAt") String lastCreatedAt
    ) {
        return this.spaceReadService.getSpaces(ReadSpaces.lastAt(lastCreatedAt));
    }

    @GetMapping(DOMAIN + "/all/trending")
    public List<SpaceResponse> getAllSpaceByTrending(
            @Nullable @RequestParam("offset") Long offset
    ) {
        return this.spaceReadService.getSpaces(ReadSpaces.offset(offset));
    }

    @GetMapping( DOMAIN + "/{targetMemberId}/all")
    @CommonAPI
    public List<SpaceResponse> getAllMySpace(
            @PathVariable("targetMemberId") Long targetMemberId,
            @Nullable @RequestParam("lastJoinedAt") String lastJoinedAt,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        if (memberDetails != null) {
            ReadMembersSpaceList readMembersSpaceList = ReadMembersSpaceList.of(memberDetails.getMember().getMemberId(), targetMemberId, lastJoinedAt);
            return spaceReadService.getSpaces(readMembersSpaceList);
        }

        ReadMembersSpaceList readMembersSpaceList = ReadMembersSpaceList.of(targetMemberId, lastJoinedAt);
        return spaceReadService.getSpaces(readMembersSpaceList);
    }

    @PatchMapping(AUTH + DOMAIN + "/spaceMember/role")
    public SpaceMemberResponse updateSpaceMemberRole(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("spaceMemberId") Long spaceMemberId,
            @RequestParam("role") SpaceRole role
    ) {
        UpdateSpaceMemberRole updateSpaceMemberRole = UpdateSpaceMemberRole.of(
                memberDetails.getMember(),
                spaceId,
                spaceMemberId,
                role);
        return spaceMemberUpdateService.changeSpaceMemberRole(updateSpaceMemberRole);
    }

    @GetMapping(AUTH + DOMAIN + "/spaceMemberList")
    public List<SpaceMemberResponse> getSpaceMemberList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        return spaceMemberReadService.getSpaceMembers(memberDetails.getMember().getMemberId(), spaceId);
    }

    @GetMapping(DOMAIN + "/join")
    public JoinSpace checkJoinSpace(
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("code") String code
    ) {
        return spaceInviteService.checkJoinSpace(spaceId, code);
    }

    @GetMapping(AUTH + DOMAIN + "/join")
    public JoinCodeDto getJoinCode(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        Map<SpaceRole, String> joinCodes = spaceReadService.getJoinCodes(memberDetails.getMember(), spaceId);
        return new JoinCodeDto(spaceId, joinCodes.get(SpaceRole.ADMIN), joinCodes.get(SpaceRole.MEMBER));
    }

    @PostMapping(AUTH + DOMAIN + "/join")
    public SpaceMemberResponse joinSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("code") String code
    ) {
       return spaceInviteService.join(memberDetails.getMember(), spaceId, code);
    }

    @GetMapping(AUTH + DOMAIN + "/updatable")
    public Message isUpdatable(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        boolean isUpdatable = spaceMemberAuthService.isUpdatableSpace(memberDetails.getMember().getMemberId(), spaceId);
        if (isUpdatable) {
            return new Message("200", HttpStatus.OK);
        }

        return new Message("403", HttpStatus.FORBIDDEN);
    }

    @PatchMapping(AUTH + DOMAIN + "/visibility")
    public Message changeVisibility(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("open") boolean open
    ) {
        spaceUpdateService.changeVisibility(memberDetails.getMember(), spaceId, open);

        return new Message("Success", HttpStatus.OK);
    }

    @DeleteMapping(AUTH + DOMAIN + "/spaceMember")
    public DeleteResponse deleteSpaceMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("spaceMemberId") Long spaceMemberId
    ) {
        DeleteSpaceMember deleteSpaceMember = DeleteSpaceMember.of(memberDetails.getMember(), spaceId, spaceMemberId);
        spaceMemberUpdateService.deleteSpaceMember(deleteSpaceMember);
        return DeleteResponse.of(spaceMemberId, true);
    }

    @DeleteMapping(AUTH + DOMAIN)
    public DeleteResponse deleteSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        spaceUpdateService.deleteSpace(memberDetails.getMember(), spaceId);
        return DeleteResponse.of(spaceId, true);
    }
}
