package umc.kkijuk.server.IntegrationTest.tag;

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
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.controller.response.TagResponse;
import umc.kkijuk.server.tag.dto.TagRequestDto;
import umc.kkijuk.server.tag.dto.TagResponseDto;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TagCreateTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    @Test
    @DisplayName("[태그 생성 API] 로그인 정보가 존재하면 정상 처리")
    void testTagCreateWithLogin() throws Exception {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag1")
                .build();

        String tagCreateJson = objectMapper.writeValueAsString(request);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/career/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagCreateJson).session(session))
                .andReturn();
        //then
        TagResponse<TagResponseDto.ResultTagDto> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<TagResponse<TagResponseDto.ResultTagDto>>() {}
        );
        assertEquals("태그를 성공적으로 생성했습니다.", response.getMessage());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        TagResponseDto.ResultTagDto resultData = response.getData();
        assertThat(resultData.getId()).isNotNull();
        assertThat(resultData.getTagName()).isEqualTo("test tag1");
    }
    @Test
    @DisplayName("[태그 생성 API] 로그인 정보가 없으면 /api/error로 forward")
    void testTagCreateWithoutLogin() throws Exception {
        //given
        TagRequestDto.CreateTagDto request = TagRequestDto.CreateTagDto.builder()
                .tagName("test tag1")
                .build();
        String tagCreateJson = objectMapper.writeValueAsString(request);
        //when
        MvcResult mvcResult = mockMvc.perform(post("/career/tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagCreateJson))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");

    }
}
