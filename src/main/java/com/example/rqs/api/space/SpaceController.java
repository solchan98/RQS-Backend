package com.example.rqs.api.space;

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

import java.util.List;

@RestController
@RequestMapping("/api/v1/space")
public class SpaceController {

    private final SpaceService spaceService;
    private final JwtProvider jwtProvider;
    private final JoinSpaceValidator joinSpaceValidator;

    public SpaceController(SpaceService spaceService, JwtProvider jwtProvider, JoinSpaceValidator joinSpaceValidator) {
        this.spaceService = spaceService;
        this.jwtProvider = jwtProvider;
        this.joinSpaceValidator = joinSpaceValidator;
    }

    @PostMapping("")
    public SpaceResponse createNewSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody CreateSpaceDto createSpaceDto
    ) {
        if (createSpaceDto.getTitle().isEmpty()) throw new BadRequestException();
        CreateSpace createSpace = CreateSpace.of(
                memberDetails.getMember(),
                createSpaceDto.getTitle(),
                createSpaceDto.isVisibility());
        return spaceService.createSpace(createSpace);
    }

    @PatchMapping("")
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

    @GetMapping("")
    public SpaceResponse getSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {

        ReadSpace readSpace = ReadSpace.of(
                memberDetails.getMember(),
                spaceId);
        return spaceService.getSpace(readSpace);
    }

    @GetMapping("/all")
    public List<SpaceResponse> getAllMySpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Nullable @RequestParam("visibility") Boolean visibility,
            @Nullable @RequestParam("lastJoinedAt") String lastJoinedAt
    ) {
        ReadSpaceList readSpaceList = ReadSpaceList.of(memberDetails.getMember(), lastJoinedAt, visibility);
        return spaceService.getMySpaceList(readSpaceList);
    }

    @PatchMapping("/spaceMember/role")
    public SpaceMemberResponse updateSpaceMemberRole(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("spaceMemberId") Long spaceMemberId,
            @RequestParam("role") SpaceRole role
    ) {
//        // TODO: role을 String -> Enum으로 변경하면서 제거됳 검증 조건임. 따라서 이에 대한 테스트 케이스는 추후에 작성 예정
//        if (!(role.equals("ADMIN") || role.equals("MEMBER"))) {
//            throw new BadRequestException("변경하려는 권한이 올바르지 않습니다.");
//        }
        UpdateSpaceMemberRole updateSpaceMemberRole = UpdateSpaceMemberRole.of(
                memberDetails.getMember(),
                spaceId,
                spaceMemberId,
                role);
        return spaceService.changeMemberRole(updateSpaceMemberRole);
    }

    @GetMapping("/spaceMemberList")
    public List<SpaceMemberResponse> getSpaceMemberList(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        return spaceService.getSpaceMemberList(
                memberDetails.getMember().getMemberId(),
                spaceId);
    }

    @GetMapping("/invite")
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

    @GetMapping("/join")
    public SpaceMemberResponse joinSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("itk") String itk
    ) throws JsonProcessingException {
        joinSpaceValidator.validate(itk);
        InviteSpaceSubject inviteSpaceSubject = jwtProvider.getInviteSpaceSubject(itk);
        return spaceService.addNewMember(inviteSpaceSubject.getSpaceId(), memberDetails.getMember());
    }

    @GetMapping("/creator")
    public Message isSpaceCreator(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        boolean isCreator = spaceService.isSpaceCreator(memberDetails.getMember(), spaceId);
        return isCreator
                ? new Message("200", HttpStatus.OK)
                : new Message("403", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/spaceMember")
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

    @DeleteMapping("")
    public DeleteResponse deleteSpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId
    ) {
        DeleteSpace deleteSpace = DeleteSpace.of(memberDetails.getMember(), spaceId);
        spaceService.deleteSpace(deleteSpace);
        return DeleteResponse.of(spaceId, true);
    }
}
