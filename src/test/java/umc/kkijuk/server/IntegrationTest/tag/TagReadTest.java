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
import umc.kkijuk.server.tag.dto.TagResponseDto;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TagReadTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    @Test
    @DisplayName("[태그 조회 API] 로그인 정보가 존재하면 정상 처리")
    void testTagReadWithLogin() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/career/tag")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        TagResponse<TagResponseDto.ResultTagDtoList> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<TagResponse<TagResponseDto.ResultTagDtoList>>() {}
        );
        assertEquals("모든 태그를 성공적으로 조회했습니다.", response.getMessage());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        TagResponseDto.ResultTagDtoList resultData = response.getData();

        assertNotNull(resultData);
        assertThat(resultData.getTagList()).isNotEmpty();
        assertThat(resultData.getCount()).isEqualTo(2);
        assertThat(resultData.getTagList()).anyMatch(tag ->
                Arrays.asList("커뮤니케이션 능력", "피그마 활용 능력").contains(tag.getTagName())
        );
    }
    @Test
    @DisplayName("[태그 조회 API] 로그인 정보가 없으면 /api/error로 forward")
    void testTagReadWithoutLogin() throws Exception {
        //given
        //when
        MvcResult mvcResult = mockMvc.perform(get("/career/tag")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");
    }


}
