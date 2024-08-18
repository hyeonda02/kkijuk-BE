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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createCareerTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteCareerTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CareerUpdateTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    private final Long careerId = 1L;
    @Test
    @DisplayName("[활동 수정 API] 로그인 정보가 존재하면 정상 처리")
    void testCareerUpdateWithLogin() throws Exception {
        //given
        CareerRequestDto.UpdateCareerDto request = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(2)
                .build();
        String careerUpdateJson = objectMapper.writeValueAsString(request);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(patch("/career/{careerId}", careerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(careerUpdateJson)
                .session(session)).andReturn();

        //then
        CareerResponse<CareerResponseDto.CareerDto> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerResponse<CareerResponseDto.CareerDto>>() {}
        );
        CareerResponseDto.CareerDto resultData =  response.getData();
        assertAll(
                () -> assertThat(response.getMessage()).isEqualTo(CareerResponseMessage.CAREER_UPDATE_SUCCESS),
                () -> assertThat(resultData.getId()).isEqualTo(1L),
                () -> assertThat(resultData.getCareerName()).isEqualTo("update test"),
                () -> assertThat(resultData.getSummary()).isEqualTo("update summary"),
                () -> assertThat(resultData.getAlias()).isEqualTo("update alias")
        );


    }
    @Test
    @DisplayName("[활동 수정 API] 로그인 정보가 없으면 /api/error로 forward")
    void testCareerUpdateWithoutLogin() throws Exception {
        //given
        CareerRequestDto.UpdateCareerDto request = CareerRequestDto.UpdateCareerDto.builder()
                .careerName("update test")
                .summary("update summary")
                .alias("update alias")
                .isUnknown(true)
                .startDate(LocalDate.of(2021, 1, 1))
                .category(2)
                .build();
        String careerUpdateJson = objectMapper.writeValueAsString(request);

        //when
        MvcResult mvcResult = mockMvc.perform(patch("/career/{careerId}", careerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(careerUpdateJson)).andReturn();

        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");


    }

}
