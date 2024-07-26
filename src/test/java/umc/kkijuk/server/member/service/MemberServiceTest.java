package umc.kkijuk.server.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void 유저정보db저장및조회() throws Exception {
        // given
        Member member1 = new Member("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.now(), "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);

        // when
        Long savedId = memberService.join(member1);
        Optional<Member> member2 = memberJpaRepository.findById(savedId);

        // then
        assertEquals(member1, member2.get());
    }

    @Test
    public void 회원가입성공() {
        // given
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .email("asd@naver.com")
                .name("홍길동")
                .phoneNumber("010-7444-1768")
                .birthDate(LocalDate.now())
                .password("passwordTest")
                .passwordConfirm("passwordTest")
                .marketingAgree(Boolean.TRUE)
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
    public void 회원가입실패_비밀번호불일치() {
        // given
        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
                .email("asd@naver.com")
                .name("홍길동")
                .phoneNumber("010-7444-1768")
                .birthDate(LocalDate.now())
                .password("passwordTest")
                .passwordConfirm("wrongPassword")
                .marketingAgree(Boolean.TRUE)
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
