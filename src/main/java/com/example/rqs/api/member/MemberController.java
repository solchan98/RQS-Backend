package com.example.rqs.api.member;

import com.example.rqs.api.jwt.JwtProvider;
import com.example.rqs.api.jwt.MemberDetails;
import com.example.rqs.core.member.service.MemberService;
import com.example.rqs.core.member.service.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final SignUpValidator signUpValidator;
    private final JwtProvider jwtProvider;

    @InitBinder("signUpDto")
    public void initSignUpBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    public MemberController(MemberService memberService, SignUpValidator signUpValidator, JwtProvider jwtProvider) {
        this.memberService = memberService;
        this.signUpValidator = signUpValidator;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody @Validated SignUpDto signUpDto) {
        MemberDto memberDto = memberService.signUp(signUpDto);
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        MemberDto memberDto = memberService.login(loginDto);
        String atk = jwtProvider.createAccessToken(memberDto.getEmail(), memberDto.getNickname(), "USER");
        LoginResponse loginResponse = LoginResponse.of(memberDto, atk);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("")
    public ResponseEntity<MemberDto> getMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        MemberDto memberDto = MemberDto.of(memberDetails.getMember());
        return ResponseEntity.ok(memberDto);
    }
}
