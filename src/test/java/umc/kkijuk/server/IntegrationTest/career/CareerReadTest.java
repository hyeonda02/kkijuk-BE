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
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createCareerTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteCareerTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class CareerReadTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    private final Long careerId = 1L;
    @Test
    @DisplayName("[활동 상세 API] 로그인 정보가 존재하면 정상 처리")
    void findCareerWithLogin() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));
        //when
        MvcResult mvcResult = mockMvc.perform(get("/career/{careerId}",careerId)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //then
        CareerResponse<CareerResponseDto.CareerDetailDto> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerResponse<CareerResponseDto.CareerDetailDto>>() {}
        );
        CareerResponseDto.CareerDetailDto resultData = response.getData();

        assertAll(
                () -> assertEquals(CareerResponseMessage.CAREER_FINDALL_SUCCESS, response.getMessage()),
                () -> assertNotNull(resultData),
                () -> assertEquals(resultData.getMemberId(), 1L),
                () -> assertEquals(resultData.getId(), 1L ),
                () -> assertEquals(resultData.getCareerName(),"IT 서비스 개발 동아리"),
                () -> assertEquals(resultData.getAlias(), "UMC"),
                () -> assertNotNull(resultData.getDetails())
        );

    }

    @Test
    @DisplayName("[활동 상세 API] 로그인 정보가 없으면 /api/error로 forward")
    void findCareerWithoutLogin() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/career/{careerId}",careerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }

    @Test
    @DisplayName("[활동 목록 API] 로그인 정보가 존재하면 정상 처리 ( status = category )")
    void readCareerByCategoryWithLogin() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/career")
                        .param("status", "category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andReturn();

        CareerResponse<List<CareerResponseDto.CareerGroupedByCategoryDto>> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerResponse<List<CareerResponseDto.CareerGroupedByCategoryDto>>>() {}
        );

        List<CareerResponseDto.CareerGroupedByCategoryDto> resultData = (List<CareerResponseDto.CareerGroupedByCategoryDto>) response.getData();
        //then
        assertAll(
                () -> assertEquals(response.getMessage(),  CareerResponseMessage.CAREER_FINDALL_SUCCESS),
                () -> assertEquals(resultData.get(0).getCount(),2),
                () -> assertEquals(resultData.get(0).getCategoryName(), "동아리"),
                () -> assertNotNull(resultData.get(0).getCareers())
        );

    }

    @Test
    @DisplayName("[활동 목록 API] 로그인 정보가 존재하면 정상 처리 ( status = year )")
    void readCareerByYearWithLogin() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/career")
                        .param("status", "year")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andReturn();

        CareerResponse<List<CareerResponseDto.CareerGroupedByYearDto>> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<CareerResponse<List<CareerResponseDto.CareerGroupedByYearDto>>>() {}
        );

        List<CareerResponseDto.CareerGroupedByYearDto> resultData = (List<CareerResponseDto.CareerGroupedByYearDto>) response.getData();

        //then
        assertAll(
                () -> assertEquals(response.getMessage(),  CareerResponseMessage.CAREER_FINDALL_SUCCESS),
                () -> assertEquals(resultData.get(0).getCount(),1),
                () -> assertEquals(resultData.get(1).getCount(),1),
                () -> {
                    int year = resultData.get(0).getYear();
                    assertTrue(year == 2024 || year == 2023);
                },
                () -> {
                    int year = resultData.get(1).getYear();
                    assertTrue(year == 2024 || year == 2023);
                },
                () -> assertNotNull(resultData.get(0).getCareers())
        );

    }

    @Test
    @DisplayName("[활동 목록 API] 로그인 정보가 없으면 /api/error로 forward ( status = category )")
    void readCareerByCategoryWithoutLogin() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/career")
                        .param("status", "category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }

    @Test
    @DisplayName("[활동 목록 API] 로그인 정보가 없으면 /api/error로 forward ( status = year )")
    void readCareerByYearWithoutLogin() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/career")
                        .param("status", "year")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }



}
