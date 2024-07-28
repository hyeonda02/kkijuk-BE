package umc.kkijuk.server.member.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import umc.kkijuk.server.member.controller.response.MemberInfoResponse;
import umc.kkijuk.server.member.controller.response.ResultResponse;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberInfoChangeDto;
import umc.kkijuk.server.member.repository.MemberJpaRepository;
import umc.kkijuk.server.common.LoginUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberInfoTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @BeforeEach
    public void setup() {
        memberJpaRepository.deleteAll();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void 내정보_조회() {
        // Given
        Member member1 = new Member("asd@naver.com", "홍길동", "010-1234-5678",
                LocalDate.parse("1999-03-31"), "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
        memberJpaRepository.save(member1);
        System.out.println("test1");
        // When
        ResponseEntity<MemberInfoResponse> response = restTemplate.getForEntity("/member/myPage/info", MemberInfoResponse.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        MemberInfoResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("asd@naver.com", body.getEmail());
        assertEquals("홍길동", body.getName());
        assertEquals("010-1234-5678", body.getPhoneNumber());
        assertEquals(LocalDate.parse("1999-03-31"), body.getBirthDate());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void 내정보_수정() {
        // Given
        Member member1 = new Member("asd@naver.com", "홍길동", "010-1234-5678",
                LocalDate.parse("1999-03-31"), "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
        memberJpaRepository.save(member1);
        System.out.println("test2");


        MemberInfoChangeDto changeDto = MemberInfoChangeDto.builder()
                .phoneNumber("010-5678-1234")
                .birthDate(LocalDate.parse("2000-01-01"))
                .marketingAgree(MarketingAgree.EMAIL)
                .build();

        HttpEntity<MemberInfoChangeDto> requestEntity = new HttpEntity<>(changeDto);

        // When
        ResponseEntity<ResultResponse> response = restTemplate.exchange("/member/myPage/info", HttpMethod.PUT, requestEntity, ResultResponse.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResultResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("information update success", body.getMessage());

        // Verify changes
        Member updatedMember = memberJpaRepository.findById(member1.getId()).orElse(null);
        assertNotNull(updatedMember);
        assertEquals("010-5678-1234", updatedMember.getPhoneNumber());
        assertEquals(LocalDate.parse("2000-01-01"), updatedMember.getBirthDate());
        assertEquals(MarketingAgree.EMAIL, updatedMember.getMarketingAgree());
    }

}
