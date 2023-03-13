package com.example.rqs.api.member;

import com.example.rqs.api.jwt.*;
import com.example.rqs.api.oauth.OauthManager;
import com.example.rqs.api.oauth.OauthProfile;
import com.example.rqs.api.oauth.OauthType;
import com.example.rqs.core.common.exception.BadRequestException;
import com.example.rqs.core.member.service.*;
import com.example.rqs.core.member.service.dtos.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAuthService memberAuthService;
    private final MemberRegisterService memberRegisterService;
    private final MemberUpdateService memberUpdateService;

    private final SignUpValidator signUpValidator;
    private final JwtProvider jwtProvider;

    private final Map<String, OauthManager> oauthManagerMap;

    @InitBinder("signUpDto")
    public void initSignUpBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody @Validated SignUpDto signUpDto) {
        MemberDto memberDto = memberRegisterService.signUp(signUpDto);
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDto loginDto) {
        MemberDto memberDto = memberAuthService.login(loginDto);
        TokenResponse tokenList = jwtProvider.createTokensByLogin(memberDto);
        return ResponseEntity.ok(tokenList);
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissueAtk(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        MemberDto memberDto = MemberDto.of(memberDetails.getMember());
        String atk = jwtProvider.reissueAtk(memberDto);
        TokenResponse atkResponse = TokenResponse.of(atk, null);
        return ResponseEntity.ok(atkResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDto> getMemberInfo(
            @RequestParam("memberId") Long memberId
    ) {
        MemberDto memberDto = MemberDto.of(memberAuthService.getMember(memberId).orElseThrow(BadRequestException::new));
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/check")
    public ResponseEntity<CheckEmailResponse> checkEmail(
            @RequestParam("email") String email
    ) {
        boolean isExist = memberAuthService.existEmail(email);
        return ResponseEntity.ok(CheckEmailResponse.of(email, isExist));
    }

    @PatchMapping("")
    public MemberDto updateMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UpdateMember updateMember
    ) {
        boolean nicknameIsNull = Objects.isNull(updateMember.getNickname());
        if(nicknameIsNull) throw new BadRequestException();
        return memberUpdateService.updateMember(
                memberDetails.getMember(),
                updateMember.getNickname(),
                updateMember.getDescription()
        );
    }

    @PatchMapping("/avatar")
    public MemberDto updateAvatar(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody UpdateMember updateMember
    ) {
        boolean avatarIsNull = Objects.isNull(updateMember.getUpdateUrl());
        if(avatarIsNull) {
            throw new BadRequestException();
        }

        return memberUpdateService.updateAvatar(memberDetails.getMember(), updateMember.getUpdateUrl());
    }

    @PostMapping("/oauth/{type}")
    public ResponseEntity<TokenResponse>  oauthLogin(
            HttpServletRequest request,
            @PathVariable("type") String type
    ) {
        String code = request.getParameter("code");
        OauthType oauthType = OauthType.valueOfType(type);
        OauthManager oauthManager = oauthManagerMap.get(oauthType.getType() + "Oauth");
        OauthProfile oauthProfile = oauthManager.verify(code);

        MemberDto memberDto = memberAuthService.oauthLogin(oauthProfile.toOauthLoginDto(type));
        TokenResponse tokenList = jwtProvider.createTokensByLogin(memberDto);
        return ResponseEntity.ok(tokenList);

    }
}
