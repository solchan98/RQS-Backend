package com.example.rqs.api.space;

import com.example.rqs.api.common.CommonAPIAuthChecker;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private final CommonAPIAuthChecker commonAPIAuthChecker;

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
    public SpaceResponse getSpace(
            HttpServletRequest request,
            @RequestParam("spaceId") Long spaceId
    ) {
        MemberDetails memberDetails = this.commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? spaceReadService.getSpace(ReadSpace.of(memberDetails.getMember(), spaceId))
                : spaceReadService.getSpace(ReadSpace.of(spaceId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<SpaceResponse> getAllSpace(
            @Nullable @RequestParam("lastCreatedAt") String lastCreatedAt
    ) {
        return this.spaceReadService.getSpaceList(ReadSpaceList.lastAt(lastCreatedAt));
    }

    @GetMapping(DOMAIN + "/all/trending")
    public List<SpaceResponse> getAllSpaceByTrending(
            @Nullable @RequestParam("offset") Long offset
    ) {
        return this.spaceReadService.getSpaceList(ReadSpaceList.offset(offset));
    }

    @GetMapping( DOMAIN + "/{targetMemberId}/all")
    public List<SpaceResponse> getAllMySpace(
            HttpServletRequest request,
            @PathVariable("targetMemberId") Long targetMemberId,
            @Nullable @RequestParam("lastJoinedAt") String lastJoinedAt
    ) {
        MemberDetails memberDetails = this.commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        ReadMembersSpaceList readMembersSpaceList = Objects.nonNull(memberDetails)
                        ? ReadMembersSpaceList.of(memberDetails.getMember().getMemberId(), targetMemberId, lastJoinedAt)
                        : ReadMembersSpaceList.of(targetMemberId, lastJoinedAt);
        return spaceReadService.getSpaceList(readMembersSpaceList);
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
        return spaceMemberReadService.getSpaceMemberList(memberDetails.getMember().getMemberId(), spaceId);
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
        boolean isUpdatable = spaceMemberAuthService
                .isUpdatableSpace(memberDetails.getMember().getMemberId(), spaceId);
        return isUpdatable
                ? new Message("200", HttpStatus.OK)
                : new Message("403", HttpStatus.FORBIDDEN);
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
        DeleteSpaceMember deleteSpaceMember = DeleteSpaceMember.of(
                memberDetails.getMember(),
                spaceId,
                spaceMemberId);

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
