package umc.kkijuk.server.IntegrationTest.CareerDetail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import umc.kkijuk.server.careerdetail.controller.response.CareerDetailResponse;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createCareerTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteCareerTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CareerDetailCreateTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    @Test
    @DisplayName("[활동 기록 생성 API] 로그인 정보가 존재하면 정상 처리")
    void testCareerDetailCreateWithLogin() throws Exception {
        //given
        Long careerId = 1L;
        CareerDetailRequestDto.CareerDetailUpdate request = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("detail title")
                .content("detail content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(1L,2L))
                .build();
        String careerDetailJson = objectMapper.writeValueAsString(request);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/career/{careerId}",careerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(careerDetailJson).session(session))
                .andReturn();

        //then
        CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> >() {}
        );
        CareerDetailResponseDto.CareerDetailResult resultData = response.getData();

        assertEquals("활동 기록을 성공적으로 생성했습니다.", response.getMessage());
        assertEquals(HttpStatus.CREATED.value(), response.getStaus());

        assertAll(
                () -> assertThat(resultData.getTitle()).isEqualTo("detail title"),
                () -> assertThat(resultData.getId()).isNotNull(),
                () -> assertThat(resultData.getContent()).isEqualTo("detail content")
        );


    }
    @Test
    @DisplayName("[활동 기록 생성 API] 로그인 정보가 없으면 /api/error로 forward")
    void testCareerDetailCreateWithoutLogin() throws Exception {
        //given
        Long careerId = 1L;
        CareerDetailRequestDto.CareerDetailUpdate request = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("detail title")
                .content("detail content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(1L,2L))
                .build();
        String careerDetailJson = objectMapper.writeValueAsString(request);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/career/{careerId}",careerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(careerDetailJson))
                .andReturn();

        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }


}
