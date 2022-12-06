package com.example.rqs.api.space;

import com.example.rqs.api.common.CommonAPIAuthChecker;
import com.example.rqs.api.exception.Message;
import com.example.rqs.api.jwt.*;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.space.SpaceRole;
import com.example.rqs.core.space.service.SpaceService;
import com.example.rqs.core.space.service.dtos.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class SpaceController {

    private static final String AUTH = "/my";
    private static final String DOMAIN = "/space";

    private final SpaceService spaceService;
    private final JwtProvider jwtProvider;
    private final JoinSpaceValidator joinSpaceValidator;
    private final CommonAPIAuthChecker commonAPIAuthChecker;

    public SpaceController(SpaceService spaceService, JwtProvider jwtProvider, JoinSpaceValidator joinSpaceValidator, CommonAPIAuthChecker commonAPIAuthChecker) {
        this.spaceService = spaceService;
        this.jwtProvider = jwtProvider;
        this.joinSpaceValidator = joinSpaceValidator;
        this.commonAPIAuthChecker = commonAPIAuthChecker;
    }

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
        return spaceService.createSpace(createSpace);
    }

    @PatchMapping(AUTH + DOMAIN)
    public SpaceResponse updateSpaceTitle(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UpdateSpaceDto updateSpaceDto
    ) {
        if (updateSpaceDto.getTitle().isEmpty()) throw new BadRequestException();
        UpdateSpace updateSpace = UpdateSpace.of(
                updateSpaceDto.getSpaceId(),
                memberDetails.getMember(),
                updateSpaceDto.getTitle());
        return spaceService.updateTitle(updateSpace);
    }

    @GetMapping(DOMAIN)
    public SpaceResponse getSpace(
            HttpServletRequest request,
            @RequestParam("spaceId") Long spaceId
    ) {
        MemberDetails memberDetails = this.commonAPIAuthChecker.checkIsAuth(request.getHeader("Authorization"));
        return Objects.nonNull(memberDetails)
                ? this.spaceService.getSpace(ReadSpace.of(memberDetails.getMember(), spaceId))
                : this.spaceService.getSpace(ReadSpace.of(spaceId));
    }

    @GetMapping(DOMAIN + "/all")
    public List<SpaceResponse> getAllSpace(
            @Nullable @RequestParam("lastCreatedAt") String lastCreatedAt
    ) {
        return this.spaceService.getSpaceList(ReadSpaceList.guest(lastCreatedAt));
    }

    @GetMapping(DOMAIN + "/all/trending")
    public List<SpaceResponse> getAllSpaceByTrending(
            @Nullable @RequestParam("offset") Long offset
    ) {
        return this.spaceService.getSpaceList(ReadSpaceList.guest(offset));
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
        return spaceService.getMemberSpaceList(readMembersSpaceList);
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
        return spaceService.changeMemberRole(updateSpaceMemberRole);
    }

    @GetMapping(AUTH + DOMAIN + "/spaceMemberList")
    public List<SpaceMemberResponse> getSpaceMemberList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        return spaceService.getSpaceMemberList(
                memberDetails.getMember().getMemberId(),
                spaceId);
    }

    @GetMapping(AUTH + DOMAIN + "/invite")
    public InviteSpaceTokenResponse createInviteSpaceLink(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        spaceService.checkIsCreatableInviteLink(memberDetails.getMember().getMemberId(), spaceId);
        ReadSpace readSpace = ReadSpace.of(memberDetails.getMember(), spaceId);
        SpaceResponse space = spaceService.getSpace(readSpace);
        InviteSpaceSubject inviteSpaceSubject = InviteSpaceSubject.of(
                spaceId,
                space.getTitle(),
                memberDetails.getMember().getMemberId(),
                memberDetails.getMember().getNickname());
        return jwtProvider.createInviteToken(inviteSpaceSubject);
    }

    @GetMapping(AUTH + DOMAIN + "/join")
    public SpaceMemberResponse joinSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itk") String itk
    ) throws JsonProcessingException {
        joinSpaceValidator.validate(itk);
        InviteSpaceSubject inviteSpaceSubject = jwtProvider.getInviteSpaceSubject(itk);
        return spaceService.addNewMember(inviteSpaceSubject.getSpaceId(), memberDetails.getMember());
    }

    @GetMapping(AUTH + DOMAIN + "/creator")
    public Message isSpaceCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        boolean isCreator = spaceService.isSpaceCreator(memberDetails.getMember(), spaceId);
        return isCreator
                ? new Message("200", HttpStatus.OK)
                : new Message("403", HttpStatus.FORBIDDEN);
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

        spaceService.deleteMember(deleteSpaceMember);
        return DeleteResponse.of(spaceMemberId, true);
    }

    @DeleteMapping(AUTH + DOMAIN)
    public DeleteResponse deleteSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        DeleteSpace deleteSpace = DeleteSpace.of(memberDetails.getMember(), spaceId);
        spaceService.deleteSpace(deleteSpace);
        return DeleteResponse.of(spaceId, true);
    }
}
