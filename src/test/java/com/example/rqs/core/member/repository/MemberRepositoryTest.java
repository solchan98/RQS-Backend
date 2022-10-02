package com.example.rqs.core.member.repository;

import com.example.rqs.core.config.DataTestConfig;
import com.example.rqs.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Import(DataTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("멤버 레포지토리 테스트")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    Member createMember(String email, String password, String nickname) {
        Member member = Member.newMember(email, password, nickname);
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("findByEmail - 존재하는 경우")
    void findByEmailTestWhenExist() {
        Member member = createMember("sol@sol.com", "a12345678!", "sol");

        Optional<Member> memberByEmail = memberRepository.findByEmail(member.getEmail());

        assertThat(memberByEmail).isPresent();
        assertAll(
                () -> assertThat(memberByEmail.get().getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(memberByEmail.get().getNickname()).isEqualTo(member.getNickname())
        );
    }

    @Test
    @DisplayName("findByEmail - 존재하지 않는 경우")
    void findByEmailTestWhenNotExist() {

        Optional<Member> memberByEmail = memberRepository.findByEmail("sol@sol.com");

        assertThat(memberByEmail).isEmpty();
    }

    @Test
    @DisplayName("existsByEmail - 존재하는 경우")
    void existsByEmailTestWhenExist() {
        Member member = createMember("solchan@sol.com", "b12345678!", "solchan");

        boolean exist = memberRepository.existsByEmail(member.getEmail());

        assertThat(exist).isTrue();

    }

    @Test
    @DisplayName("existsByEmail - 존재하지 않는 경우")
    void existsByEmailTestWhenNotExist() {
        boolean none = memberRepository.existsByEmail("none");

        assertThat(none).isFalse();
    }

}
