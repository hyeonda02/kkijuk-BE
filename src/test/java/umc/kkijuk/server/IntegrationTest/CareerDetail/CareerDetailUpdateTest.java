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
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.dto.CareerResponseDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createCareerTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteCareerTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CareerDetailUpdateTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    @Test
    @DisplayName("[활동 기록 수정 API] 로그인 정보가 존재하면 정상 처리")
    void testCareerDetailUpdateWithLogin() throws Exception {
        //given
        Long careerId = 1L;
        Long detailId = 1L;
        CareerDetailRequestDto.CareerDetailUpdate request = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(1L))
                .build();
        String careerUpdateJson = objectMapper.writeValueAsString(request);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(put("/career/{careerId}/{detailId}", careerId, detailId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(careerUpdateJson).session(session)).andReturn();

        //then
        CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerDetailResponse<CareerDetailResponseDto.CareerDetailResult>>() {}
        );
        CareerDetailResponseDto.CareerDetailResult resultData =  response.getData();

        assertAll(
                () -> assertThat(response.getMessage()).isEqualTo("활동 기록을 성공적으로 수정했습니다."),
                () -> assertThat(response.getStaus()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(resultData.getCareerId()).isEqualTo(1L),
                () -> assertThat(resultData.getTitle()).isEqualTo("updated title"),
                () -> assertThat(resultData.getContent()).isEqualTo("updated content")
        );

    }
    @Test
    @DisplayName("[활동 기록 수정 API] 로그인 정보가 없으면 /api/error로 forward")
    void testCareerDetailUpdateWithoutLogin() throws Exception {
        //given
        Long careerId = 1L;
        Long detailId = 1L;
        CareerDetailRequestDto.CareerDetailUpdate request = CareerDetailRequestDto.CareerDetailUpdate.builder()
                .title("updated title")
                .content("updated content")
                .startDate(LocalDate.of(2024, 2, 1))
                .endDate(LocalDate.of(2024, 3, 1))
                .tagList(Arrays.asList(1L))
                .build();
        String careerUpdateJson = objectMapper.writeValueAsString(request);
        //when
        MvcResult mvcResult = mockMvc.perform(put("/career/{careerId}/{detailId}", careerId, detailId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(careerUpdateJson)).andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");



    }
}
