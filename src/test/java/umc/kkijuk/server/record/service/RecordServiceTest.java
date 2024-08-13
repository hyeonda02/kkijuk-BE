package umc.kkijuk.server.record.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import umc.kkijuk.server.introduce.domain.QuestionRepository;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.record.domain.RecordRepository;

import java.lang.Record;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class RecordServiceTest {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private RecordService recordService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MemberService memberService;
    private Member requestMember;
    private Record requestRecord;

    @BeforeEach
    public void Init() {
        MemberJoinDto memberJoinDto = new MemberJoinDto("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
        requestMember = memberService.join(memberJoinDto);
    }
}