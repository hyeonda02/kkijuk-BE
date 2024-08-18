package umc.kkijuk.server.record.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.introduce.dto.IntroduceReqDto;
import umc.kkijuk.server.introduce.dto.QuestionDto;
import umc.kkijuk.server.introduce.service.IntroduceService;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.record.domain.RecordRepository;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class RecordControllerTest {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CareerRepository careerRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;
    private Member requestMember;

    @BeforeEach
    public void Init() {
        MemberJoinDto memberJoinDto = new MemberJoinDto("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
        requestMember = memberService.join(memberJoinDto);
    }

}