//package umc.kkijuk.server.introduce.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import umc.kkijuk.server.introduce.domain.MasterIntroduce;
//import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
//import umc.kkijuk.server.introduce.dto.IntroduceResDto;
//import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
//import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
//import umc.kkijuk.server.introduce.service.MasterIntroduceService;
//import umc.kkijuk.server.login.controller.SessionConst;
//import umc.kkijuk.server.login.controller.dto.LoginInfo;
//import umc.kkijuk.server.member.domain.MarketingAgree;
//import umc.kkijuk.server.member.domain.Member;
//import umc.kkijuk.server.member.domain.State;
//import umc.kkijuk.server.member.dto.MemberJoinDto;
//import umc.kkijuk.server.member.service.MemberService;
//import umc.kkijuk.server.recruit.domain.Recruit;
//import umc.kkijuk.server.recruit.domain.RecruitStatus;
//import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;
//import umc.kkijuk.server.recruit.service.port.RecruitRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@Transactional
//class MasterIntroduceControllerTest {
//    @Autowired
//    private MasterIntroduceRepository masterIntroduceRepository;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MemberService memberService;
//
//    private Member requestMember;
//    @Autowired
//    private MasterIntroduceService masterIntroduceService;
//
//    @BeforeEach
//    public void Init() {
//        MemberJoinDto memberJoinDto = new MemberJoinDto("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
//        requestMember = memberService.join(memberJoinDto);
//    }
//
//    @Test
//    @DisplayName("마스터 자기소개서 생성 테스트")
//    @Transactional
//    public void postMaster() throws Exception{
//        final String oneLiner="one-liner-test";
//        final String motiveTitle="motive-title-test";
//        final String motive="motive-test";
//        final String prosAndConsTitle="prosAndCons-title-test";
//        final String prosAndCons="prosAndCons-test";
//        final String jobSuitability="jobSuitability-test";
//        final String jobSuitabilityTitle="jobSuitability-title-test";
//
//        MasterIntroduceReqDto masterIntroduceReqDto= MasterIntroduceReqDto.builder()
//                .oneLiner(oneLiner)
//                .motiveTitle(motiveTitle)
//                .motive(motive)
//                .prosAndConsTitle(prosAndConsTitle)
//                .prosAndCons(prosAndCons)
//                .jobSuitabilityTitle(jobSuitabilityTitle)
//                .jobSuitability(jobSuitability)
//                .build();
//
//        // 세션 추가
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(requestMember));
//
//        mockMvc.perform(post("/history/intro/master")
//                        .session(session)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(masterIntroduceReqDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.oneLiner").value("one-liner-test"))
//                .andExpect(jsonPath("$.data.motiveTitle").value("motive-title-test"))
//                .andExpect(jsonPath("$.data.motive").value("motive-test"))
//                .andExpect(jsonPath("$.data.prosAndCons").value("prosAndCons-test"))
//                .andExpect(jsonPath("$.data.jobSuitability").value("jobSuitability-test"));
//    }
//
//    @Test
//    @DisplayName("마스터 자기소개서 수정 테스트")
//    @Transactional
//    public void updateMaster() throws Exception {
//        final String oneLiner="one-liner-test";
//        final String motiveTitle="motive-title-test";
//        final String motive="motive-test";
//        final String prosAndConsTitle="prosAndCons-title-test";
//        final String prosAndCons="prosAndCons-test";
//        final String jobSuitability="jobSuitability-test";
//        final String jobSuitabilityTitle="jobSuitability-title-test";
//
//        MasterIntroduce masterIntroduce = masterIntroduceRepository.save(MasterIntroduce.builder()
//                .memberId(requestMember.getId())
//                .oneLiner(oneLiner)
//                .motiveTitle(motiveTitle)
//                .motive(motive)
//                .prosAndConsTitle(prosAndConsTitle)
//                .prosAndCons(prosAndCons)
//                .jobSuitabilityTitle(jobSuitabilityTitle)
//                .jobSuitability(jobSuitability)
//                .build());
//
//        Long id = masterIntroduce.getId();
//
//
//
//        String expectedOneLiner = "one-liner-test2";
//        String expectedMotiveTitle = "motiveTitle-test2";
//        String expectedMotive = "motive-test2";
//        String expectedPncTitle = "pncTitle-test2";
//        String expectedPnC = "prosAndCons-test2";
//        String expectedJS = "jobSuitability-test2";
//        String expectedJSTitle = "jobSuitabilityTitle-test2";
//
//        MasterIntroduceReqDto masterIntroduceReqDto = MasterIntroduceReqDto.builder()
//                .oneLiner(expectedOneLiner)
//                .motiveTitle(expectedMotiveTitle)
//                .motive(expectedMotive)
//                .prosAndConsTitle(expectedPncTitle)
//                .prosAndCons(expectedPnC)
//                .jobSuitabilityTitle(expectedJSTitle)
//                .jobSuitability(expectedJS)
//                .build();
//
//        //when
//
//        MasterIntroduceResDto result = masterIntroduceService.updateMasterIntro(requestMember, id, masterIntroduceReqDto);
//
//        //then
//        assertAll(
//                () -> assertThat(result.getId()).isEqualTo(1L),
//                () -> assertThat(result.getMemberId()).isEqualTo(requestMember.getId()),
//                () -> assertThat(result.getOneLiner()).isEqualTo(expectedOneLiner),
//                () -> assertThat(result.getJobSuitabilityTitle()).isEqualTo(expectedJSTitle),
//                () -> assertThat(result.getMotive()).isEqualTo(expectedMotive)
//        );
//
//       /* mockMvc.perform(patch("/history/intro/master")
//                        .param("id", id.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(masterIntroduceReqDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.oneLiner").value(expectedOneLiner))
//                .andExpect(jsonPath("$.data.introduction").value(expectedIntroduce))
//                .andExpect(jsonPath("$.data.motive").value(expectedMotivate))
//                .andExpect(jsonPath("$.data.prosAndCons").value(expectedPnC))
//                .andExpect(jsonPath("$.data.jobSuitability").value(expectedJS));*/
//    }
//}