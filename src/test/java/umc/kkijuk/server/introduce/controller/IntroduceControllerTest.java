package umc.kkijuk.server.introduce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import umc.kkijuk.server.introduce.domain.*;
import umc.kkijuk.server.introduce.dto.IntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.QuestionDto;
import umc.kkijuk.server.introduce.service.IntroduceService;
import umc.kkijuk.server.introduce.service.MasterIntroduceService;
import umc.kkijuk.server.member.domain.MarketingAgree;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.MemberJoinDto;
import umc.kkijuk.server.member.service.MemberService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitStatus;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class IntroduceControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private IntroduceRepository introduceRepository;
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private RecruitJpaRepository recruitJpaRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IntroduceService introduceService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MemberService memberService;

    private Member requestMember;

    @BeforeEach
    public void Init() {
        MemberJoinDto memberJoinDto = new MemberJoinDto("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
        requestMember = memberService.join(memberJoinDto);
    }

    @Test
    @DisplayName("자기소개서 생성 테스트")
    @Transactional
    public void postIntro() throws Exception {
        final int state = 1;

        Recruit recruit = Recruit.builder()
                .memberId(requestMember.getId())
                .title("test-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 31, 17, 30))
                .applyDate(LocalDate.of(2024, 7, 19))
                .tags(new ArrayList<>(Arrays.asList("코딩 테스트", "인턴", "대외 활동")))
                .link("test-link")
                .active(true)
                .build();

        Recruit savedRecruit = recruitRepository.save(recruit);
        Long recruitId = savedRecruit.getId();

        // 테스트용 질문 목록 생성
        final List<QuestionDto> questions = Arrays.asList(
                new QuestionDto("제목", "내용", 1),
                new QuestionDto("제목2", "내용2", 2)
        );

        IntroduceReqDto introduceReqDto = IntroduceReqDto.builder()
                .questionList(questions)
                .state(state)
                .build();

        // API 호출
        mockMvc.perform(MockMvcRequestBuilders.post("/history/intro/{recruitId}", recruitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(introduceReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionList[0].title").value("제목"))
                .andExpect(jsonPath("$.data.questionList[1].title").value("제목2"))
                .andDo(print());
    }

    @Test
    @DisplayName("자기소개서 수정 테스트")
    @Transactional
    public void updateIntro() throws Exception {
        final int state = 1;

        Recruit recruit = Recruit.builder()
                .memberId(requestMember.getId())
                .title("test-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 31, 17, 30))
                .applyDate(LocalDate.of(2024, 7, 19))
                .tags(new ArrayList<>(Arrays.asList("코딩 테스트", "인턴", "대외 활동")))
                .link("test-link")
                .active(true)
                .build();

        RecruitEntity recruitEntity = RecruitEntity.from(recruitRepository.save(recruit));

        Introduce introduce= introduceRepository.save(Introduce.builder()
                .member(requestMember)
                .recruit(recruitEntity)
                .questions(new ArrayList<>())
                .state(state)
                .build());

        Long introId=introduce.getId();

        // 테스트용 질문 목록 생성
        final List<QuestionDto> updatedQuestion = Arrays.asList(
                new QuestionDto("제목7", "내용", 1),
                new QuestionDto("제목2", "내용2", 2),
                new QuestionDto("제목6", "내용2", 3)
        );

        IntroduceReqDto introduceReqDto = IntroduceReqDto.builder()
                .questionList(updatedQuestion)
                .state(state)
                .build();

        // API 호출
        mockMvc.perform(MockMvcRequestBuilders.patch("/history/intro/{introId}", introId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(introduceReqDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionList[0].title").value("제목7"))
                .andExpect(jsonPath("$.data.questionList[1].title").value("제목2"))
                .andExpect(jsonPath("$.data.questionList[2].title").value("제목6"))
                .andExpect(jsonPath("$.data.questionList.length()").value(3))
                .andDo(print());
    }


    @Test
    @DisplayName("자기소개서 삭제 테스트")
    @Transactional
    public void deleteIntro() throws Exception {
        final int state = 1;

        Recruit recruit = Recruit.builder()
                .memberId(requestMember.getId())
                .title("test-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 31, 17, 30))
                .applyDate(LocalDate.of(2024, 7, 19))
                .tags(new ArrayList<>(Arrays.asList("코딩 테스트", "인턴", "대외 활동")))
                .link("test-link")
                .active(true)
                .build();

        RecruitEntity recruitEntity = RecruitEntity.from(recruitRepository.save(recruit));

        // 테스트용 질문 목록 생성
        final List<Question> questions = Arrays.asList(
                new Question("제목", "내용", 1),
                new Question("제목2", "내용2", 2)
        );

        Introduce introduce= introduceRepository.save(Introduce.builder()
                        .member(requestMember)
                        .recruit(recruitEntity)
                        .questions(questions)
                        .state(state)
                        .build());
        Long introId=introduce.getId();

        // when
        mockMvc.perform(delete("/history/intro/{introId}", introId))
                .andExpect(status().isOk());
    }
}