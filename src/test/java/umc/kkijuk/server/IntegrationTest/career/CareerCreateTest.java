package umc.kkijuk.server.IntegrationTest.career;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import umc.kkijuk.server.career.controller.response.CareerResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CareerCreateTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;

    @Test
    @DisplayName("[활동 생성 API] 로그인 정보가 존재하면 정상 처리")
    void testCareerCreateWithLogin() throws Exception {
        //given
        CareerRequestDto.CreateCareerDto request = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(1)
                .build();
        String careerCreateJson = objectMapper.writeValueAsString(request);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/career")
                .contentType(MediaType.APPLICATION_JSON)
                .content(careerCreateJson).session(session))
                .andReturn();
        //then
        CareerResponse<CareerResponseDto.CareerResultDto> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerResponse<CareerResponseDto.CareerResultDto>>() {}
        );

        assertEquals(CareerResponseMessage.CAREER_CREATE_SUCCESS, response.getMessage());
        CareerResponseDto.CareerResultDto resultData = response.getData();
        assertThat(resultData.getCareerId()).isNotNull();
    }
    @Test
    @DisplayName("[활동 생성 API] 로그인 정보가 없으면 /api/error로 forward")
    void testCareerCreateWithoutLogin() throws Exception {
        //given
        CareerRequestDto.CreateCareerDto request = CareerRequestDto.CreateCareerDto.builder()
                .careerName("test")
                .alias("alias")
                .summary("summary")
                .isUnknown(false)
                .startDate(LocalDate.of(2024,4,10))
                .endDate(LocalDate.of(2024,7,20))
                .category(1)
                .build();
        String careerCreateJson = objectMapper.writeValueAsString(request);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/career")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(careerCreateJson))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }

}
