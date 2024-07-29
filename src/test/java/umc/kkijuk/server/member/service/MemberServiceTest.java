package umc.kkijuk.server.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.ConfirmPasswordMismatchException;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String testMemberEmail = "asd@naver.com";
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void 유저정보db저장및조회() throws Exception {
        // given
        MemberJoinDto memberJoinDto = new MemberJoinDto(testMemberEmail, "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);

        // when
        Member joinMember = memberService.join(memberJoinDto);

        // then
        assertAll(
                () -> assertEquals(joinMember.getEmail(), testMemberEmail),
                () -> assertEquals(joinMember.getName(), "홍길동")
        );
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void joinExceptionWIthPasswordIncorrect() {
        // given
        MemberJoinDto memberJoinDto = new MemberJoinDto(testMemberEmail, "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "incorrectPassword", MarketingAgree.BOTH, State.ACTIVATE);

        // when
        // then
        assertThatThrownBy(() -> memberService.join(memberJoinDto))
                .isInstanceOf(ConfirmPasswordMismatchException.class);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void 회원가입성공() {
        // given
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .email("asd@naver.com")
                .name("홍길동")
                .phoneNumber("010-7444-1768")
                .birthDate(LocalDate.parse("1999-03-31"))
                .password("passwordTest")
                .passwordConfirm("passwordTest")
                .marketingAgree(MarketingAgree.BOTH)
                .userState(State.ACTIVATE)
                .build();

        HttpEntity<MemberJoinDto> request = new HttpEntity<>(memberJoinDto);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity("/member", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("Member created successfully");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void 회원가입실패_비밀번호불일치() {
        // given
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .email("asd@naver.com")
                .name("홍길동")
                .phoneNumber("010-7444-1768")
                .birthDate(LocalDate.parse("1999-03-31"))
                .password("passwordTest")
                .passwordConfirm("wrongPassword")
                .marketingAgree(MarketingAgree.BOTH)
                .userState(State.ACTIVATE)
                .build();

        HttpEntity<MemberJoinDto> request = new HttpEntity<>(memberJoinDto);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity("/member", request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Passwords do not match");
    }
}
