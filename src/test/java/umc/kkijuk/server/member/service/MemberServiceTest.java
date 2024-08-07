package umc.kkijuk.server.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import umc.kkijuk.server.member.controller.response.MemberFieldResponse;
import umc.kkijuk.server.member.controller.response.MemberInfoResponse;
import umc.kkijuk.server.common.domian.exception.ConfirmPasswordMismatchException;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberFieldDto;
import umc.kkijuk.server.member.dto.MemberInfoChangeDto;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        Member member = new Member("test@naver.com", "홍길동",
                "01012345678", LocalDate.parse("1999-03-31"),
                "testpassword", MarketingAgree.BOTH, State.ACTIVATE);
        member.changeFieldInfo(List.of("game", "computer"));
        memberJpaRepository.save(member);
    }

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

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    public void 회원가입실패_비밀번호불일치() {
//        // given
//        MemberJoinDto memberJoinDto = MemberJoinDto.builder()
//                .email("asd@naver.com")
//                .name("홍길동")
//                .phoneNumber("010-7444-1768")
//                .birthDate(LocalDate.parse("1999-03-31"))
//                .password("passwordTest")
//                .passwordConfirm("wrongPassword")
//                .marketingAgree(MarketingAgree.BOTH)
//                .userState(State.ACTIVATE)
//                .build();
//
//        HttpEntity<MemberJoinDto> request = new HttpEntity<>(memberJoinDto);
//
//        // when
//        ResponseEntity<String> response = restTemplate.postForEntity("/member", request, String.class);
//
//        // then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(response.getBody()).contains("비밀번호가 일치하지 않습니다.");
//    }

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    public void 내정보_조회_성공() {
//        // Given
//
//        // When
//        ResponseEntity<MemberInfoResponse> response = restTemplate.getForEntity("/member/myPage/info", MemberInfoResponse.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        MemberInfoResponse body = response.getBody();
//        assertNotNull(body);
//        assertEquals("test@naver.com", body.getEmail());
//        assertEquals("홍길동", body.getName());
//        assertEquals("01012345678", body.getPhoneNumber());
//        assertEquals(LocalDate.parse("1999-03-31"), body.getBirthDate());
//    }

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    public void 내정보_수정() {
//        // Given
//
//        MemberInfoChangeDto changeDto = MemberInfoChangeDto.builder()
//                .phoneNumber("010-5678-1234")
//                .birthDate(LocalDate.parse("2000-01-01"))
//                .marketingAgree(MarketingAgree.EMAIL)
//                .build();
//
//        HttpEntity<MemberInfoChangeDto> requestEntity = new HttpEntity<>(changeDto);
//
//        // When
//        ResponseEntity<Boolean> response = restTemplate.exchange("/member/myPage/info", HttpMethod.PUT, requestEntity, Boolean.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Boolean body = response.getBody();
//        assertNotNull(body);
//        assertEquals(true, body);
//
//        // Verify changes
//        Member updatedMember = memberJpaRepository.findById(1L).orElse(null);
//        assertNotNull(updatedMember);
//        assertEquals("010-5678-1234", updatedMember.getPhoneNumber());
//        assertEquals(LocalDate.parse("2000-01-01"), updatedMember.getBirthDate());
//        assertEquals(MarketingAgree.EMAIL, updatedMember.getMarketingAgree());
//    }

//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    public void 관심분야_조회(){
//        // 회원추가
//
//        ResponseEntity<MemberFieldResponse> response = restTemplate.getForEntity("/member/myPage/field", MemberFieldResponse.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        MemberFieldResponse body = response.getBody();
//        assertNotNull(body);
//        assertEquals(List.of("game", "computer"), body.getField());
//    }


//    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    public void 관심분야_수정(){
//        //given
//
//        MemberFieldDto memberFieldDto = MemberFieldDto.builder().field(List.of("book", "movie")).build();
//        HttpEntity<MemberFieldDto> request = new HttpEntity<>(memberFieldDto);
//        //when
//        ResponseEntity<Boolean> response = restTemplate.postForEntity("/member/myPage/field", request, Boolean.class);
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Boolean body = response.getBody();
//        assertNotNull(body);
//        assertEquals(true, body);
//
//        Member updatedMember = memberJpaRepository.findById(1L).orElse(null);
//        assertNotNull(updatedMember);
//        assertEquals(List.of("book", "movie"), updatedMember.getField());
//    }
}
