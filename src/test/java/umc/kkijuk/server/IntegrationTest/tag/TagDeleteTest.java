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

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/sql/createTestData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/deleteTestData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TagDeleteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final Long existMemberId =1L;
    @Test
    @DisplayName("[태그 삭제 API] 로그인 정보가 존재하면 정상 처리")
    void testTagCreateWithLogin() throws Exception {
        //given
        Long tagId = 1L;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, LoginInfo.from(Member.builder().id(existMemberId).build()));
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/career/tag/{tagId}", tagId)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        TagResponse<Object> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<TagResponse<Object>>() {});
        assertEquals("태그 삭제가 성공적으로 이루어졌습니다.", response.getMessage());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
    @Test
    @DisplayName("[태그 삭제 API] 로그인 정보가 없으면 /api/error로 forward")
    void testTagDeleteWithoutLogin() throws Exception{
        //given
        Long tagId = 1L;
        //when
        MvcResult mvcResult = mockMvc.perform(delete("/career/tag/{tagId}", tagId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        //then
        assertThat(mvcResult.getResponse().getForwardedUrl()).isEqualTo("/api/error");


    }

}
