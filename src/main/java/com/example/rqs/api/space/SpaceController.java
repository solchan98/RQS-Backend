package com.example.rqs.api.space;

import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.space.service.SpaceService;
import com.example.rqs.core.space.service.dtos.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/space")
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
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
        return spaceService.getSpace(
                memberDetails.getMember().getMemberId(),
                spaceId);
    }

    @GetMapping("/all")
    public List<SpaceResponse> getAllMySpace(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @Nullable @RequestParam("visibility") Boolean visibility,
            @Nullable @RequestParam("lastJoinedAt") String lastJoinedAt
    ) {
        ReadSpace readSpace = ReadSpace.of(memberDetails.getMember(), lastJoinedAt, visibility);
        return spaceService.getMySpaceList(readSpace);
    }

    @PatchMapping("/spaceMember/role")
    public SpaceMemberResponse updateSpaceMemberRole(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam("spaceId") Long spaceId,
            @RequestParam("spaceMemberId") Long spaceMemberId,
            @RequestParam("role") String role
    ) {
        // TODO: role을 String -> Enum으로 변경하면서 제거됳 검증 조건임. 따라서 이에 대한 테스트 케이스는 추후에 작성 예정
        if (!(role.equals("ADMIN") || role.equals("MEMBER"))) {
            throw new BadRequestException("변경하려는 권한이 올바르지 않습니다.");
        }
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
