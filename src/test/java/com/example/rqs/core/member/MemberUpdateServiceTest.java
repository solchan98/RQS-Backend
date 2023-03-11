package com.example.rqs.core.member;

import com.example.rqs.core.member.repository.MemberRepository;
import com.example.rqs.core.member.service.MemberUpdateServiceImpl;
import com.example.rqs.core.member.service.dtos.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Update Service Test")
public class MemberUpdateServiceTest {

    @InjectMocks
    MemberUpdateServiceImpl memberUpdateService;

    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 avatar 업데이트 테스트")
    void updateAvatarTest() {
        // given
        Member member = Member.newMember("sol@sol.com", "1234", "sol");
        String avatarUrl = "https://solsavatar.com";

        // when
        MemberDto memberDto = memberUpdateService.updateAvatar(member, avatarUrl);

        // then
        assertThat(memberDto.getAvatar()).isEqualTo(avatarUrl);
    }

//    @Test
//    @DisplayName("멤버 description 업데이트 테스트")
//    void updateDescriptionTest() {
//        // given
//        Member member = Member.newMember("sol@sol.com", "1234", "sol");
//        String newDescription = "Hello, ";
//
//        // when
//        MemberDto memberDto = memberUpdateService.updateDescription(member, newDescription);
//
//        // then
//        assertThat(memberDto.getDescription()).isEqualTo(newDescription);
//    }
}
