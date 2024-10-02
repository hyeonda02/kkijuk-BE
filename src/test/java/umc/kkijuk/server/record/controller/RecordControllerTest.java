//package umc.kkijuk.server.record.controller;
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
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import umc.kkijuk.server.career.domain.Career;
//import umc.kkijuk.server.career.domain.Category;
//import umc.kkijuk.server.record.domain.Education;
//import umc.kkijuk.server.record.repository.EducationRepository;
//import umc.kkijuk.server.record.domain.Record;
//import umc.kkijuk.server.login.controller.SessionConst;
//import umc.kkijuk.server.login.controller.dto.LoginInfo;
//import umc.kkijuk.server.member.domain.MarketingAgree;
//import umc.kkijuk.server.member.domain.Member;
//import umc.kkijuk.server.member.domain.State;
//import umc.kkijuk.server.member.dto.MemberJoinDto;
//import umc.kkijuk.server.member.service.MemberService;
//import umc.kkijuk.server.record.repository.RecordRepository;
//import umc.kkijuk.server.record.dto.RecordReqDto;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.ArrayList;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@Transactional
//class RecordControllerTest {
//
//    @Autowired
//    private RecordRepository recordRepository;
//    @Autowired
//    private EducationRepository educationRepository;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private MemberService memberService;
//    private Member requestMember;
//
//    private Career career1;
//    private Career career2;
//    private Career career3;
//    private Category category1;
//    private Category category2;
//
//    @BeforeEach
//    public void Init() {
//        MemberJoinDto memberJoinDto = new MemberJoinDto("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.parse("1999-03-31"), "passwordTest", "passwordTest", MarketingAgree.BOTH, State.ACTIVATE);
//        requestMember = memberService.join(memberJoinDto);
//
//        category1 = Category.builder()
//                .id(1L)
//                .name("동아리")
//                .build();
//
//        category2 = Category.builder()
//                .id(2L)
//                .name("대외활동")
//                .build();
//
//        career1 = Career.builder()
//                .id(1L)
//                .memberId(requestMember.getId())
//                .name("test")
//                .alias("alias")
//                .summary("summary")
//                .unknown(false)
//                .category(category1)
//                .startdate(LocalDate.of(2024, 4, 10))
//                .enddate(LocalDate.of(2024, 7, 20))
//                .year(2024)
//                .build();
//
//        career2 = Career.builder()
//                .id(2L)
//                .memberId(requestMember.getId())
//                .name("test2")
//                .alias("alias2")
//                .summary("summary2")
//                .unknown(true)
//                .category(category2)
//                .startdate(LocalDate.of(2024, 4, 10))
//                .enddate(LocalDate.now())
//                .year(2024)
//                .build();
//
//        career3 = Career.builder()
//                .id(3L)
//                .memberId(requestMember.getId())
//                .name("test3")
//                .alias("alias3")
//                .summary("summary3")
//                .unknown(true)
//                .category(category2)
//                .startdate(LocalDate.of(2022, 4, 10))
//                .enddate(LocalDate.of(2022, 8, 10))
//                .year(2022)
//                .build();
//
//    }
//
//    @Test
//    @DisplayName("이력서 생성 테스트")
//    @Transactional
//    public void postRecord() throws Exception {
//        final String address="address";
//        final String profileImageUrl="profileImageUrl";
//
//        RecordReqDto recordReqDto = RecordReqDto.builder()
//                .address(address)
//                .profileImageUrl(profileImageUrl)
//                .build();
//
//        // 세션 추가
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(requestMember));
//
//        // API 호출
//        mockMvc.perform(MockMvcRequestBuilders.post("/history/resume/")
//                        .session(session)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(recordReqDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.address").value("address"))
//                .andExpect(jsonPath("$.data.profileImageUrl").value("profileImageUrl"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("이력서 수정 테스트")
//    @Transactional
//    public void updateRecord() throws Exception {
//        final String address = "address";
//        final String profileImageUrl = "profileImageUrl";
//
//        // Record 객체 저장
//        Record existRecord = recordRepository.save(
//                Record.builder()
//                        .memberId(requestMember.getId())
//                        .address("sample address")
//                        .profileImageUrl("sampleProfileImageUrl")
//                        .educations(new ArrayList<>())
//                        .build());
//
//        educationRepository.save(Education.builder()
//                .record(existRecord)
//                .state("state")
//                .major("major")
//                .schoolName("schoolName")
//                .category("category")
//                .admissionDate(YearMonth.of(2020, 8))
//                .graduationDate(YearMonth.of(2024, 8))
//                .build());
//
//        educationRepository.save(Education.builder()
//                .record(existRecord)
//                .state("state1")
//                .major("major1")
//                .schoolName("schoolName1")
//                .category("category1")
//                .admissionDate(YearMonth.of(2020, 8))
//                .graduationDate(YearMonth.of(2024, 8))
//                .build());
//
//        Long id = existRecord.getId();
//
//        // DTO 생성
//        RecordReqDto recordReqDto = RecordReqDto.builder()
//                .address(address)
//                .profileImageUrl(profileImageUrl)
//                .build();
//
//        // 세션 추가
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(requestMember));
//
//        // API 호출
//        mockMvc.perform(MockMvcRequestBuilders.patch("/history/resume/")
//                        .session(session)
//                        .param("recordId", id.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(recordReqDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.address").value("address"))
//                .andExpect(jsonPath("$.data.profileImageUrl").value("profileImageUrl"));
//    }
//
//
//    @Test
//    @DisplayName("학력 삭제 테스트")
//    @Transactional
//    public void deleteEducation() throws Exception {
//        // Record 객체 저장
//        Record existRecord = recordRepository.save(
//                Record.builder()
//                        .memberId(requestMember.getId())
//                        .address("sample address")
//                        .profileImageUrl("sampleProfileImageUrl")
//                        .educations(new ArrayList<>())
//                        .build());
//
//        Education education = educationRepository.save(Education.builder()
//                .record(existRecord)
//                .state("state")
//                .major("major")
//                .schoolName("schoolName")
//                .category("category")
//                .admissionDate(YearMonth.of(2020, 8))
//                .graduationDate(YearMonth.of(2024, 8))
//                .build());
//
//        educationRepository.save(Education.builder()
//                .record(existRecord)
//                .state("state1")
//                .major("major1")
//                .schoolName("schoolName1")
//                .category("category1")
//                .admissionDate(YearMonth.of(2020, 8))
//                .graduationDate(YearMonth.of(2024, 8))
//                .build());
//
//        Long id = education.getId();
//
//        // 세션 추가
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(requestMember));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/history/resume/education")
//                        .session(session)
//                        .param("educationId", id.toString()))
//                .andExpect(status().isOk());
//
//    }
//
//
//}