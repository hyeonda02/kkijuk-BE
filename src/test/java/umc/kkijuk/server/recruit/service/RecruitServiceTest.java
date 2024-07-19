package umc.kkijuk.server.recruit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.mock.FakeRecruitRepository;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RecruitServiceTest {
    private RecruitService recruitService;

    @BeforeEach
    void init() {
        RecruitRepository recruitRepository = new FakeRecruitRepository();
        this.recruitService = RecruitServiceImpl.builder()
                .recruitRepository(recruitRepository)
                .build();

        Recruit recruit = Recruit.builder()
                .title("test-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 31, 17, 30))
                .applyDate(LocalDate.of(2024, 7, 19))
                .tags(new ArrayList<>(Arrays.asList("코딩 테스트", "인턴", "대외 활동")))
                .link("test-link")
                .isActive(true)
                .build();

        recruitRepository.save(recruit);
    }

    @Test
    void 새로운_recruit_만들기() {
        //given
        RecruitCreateDto recruitCreateDto = RecruitCreateDto.builder()
                .title("dto-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 30, 2, 30))
                .applyDate(LocalDate.of(2024, 7, 25))
                .tags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")))
                .link("https://www.dto-title.com")
                .build();
        //when
        Recruit result = recruitService.create(recruitCreateDto);

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(2L),
                () -> assertThat(result.getTitle()).isEqualTo("dto-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.PLANNED),
                () -> assertThat(result.getStartTime()).isEqualTo(LocalDateTime.of(2024, 7, 19, 2, 30)),
                () -> assertThat(result.getEndTime().isEqual(LocalDateTime.of(2024, 7, 30, 2, 30))),
                () -> assertThat(result.getApplyDate().isEqual(LocalDate.of(2024, 7, 25))),
                () -> assertThat(result.getTags().size()).isEqualTo(3),
                () -> assertThat(result.getLink()).isEqualTo("https://www.dto-title.com")
        );
    }

    @Test
    void 새로운_recruit_만들기_nullable() {
        //given
        RecruitCreateDto recruitCreateDto = RecruitCreateDto.builder()
                .title("dto-title")
                .status(RecruitStatus.PLANNED)
                .startTime(LocalDateTime.of(2024, 7, 19, 2, 30))
                .endTime(LocalDateTime.of(2024, 7, 30, 2, 30))
                .build();
        //when
        Recruit result = recruitService.create(recruitCreateDto);

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(2L),
                () -> assertThat(result.getTitle()).isEqualTo("dto-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.PLANNED),
                () -> assertThat(result.getStartTime()).isEqualTo(LocalDateTime.of(2024, 7, 19, 2, 30)),
                () -> assertThat(result.getEndTime().isEqual(LocalDateTime.of(2024, 7, 30, 2, 30))),
                () -> assertThat(result.getApplyDate()).isNull(),
                () -> assertThat(result.getTags().size()).isZero(),
                () -> assertThat(result.getLink()).isNull()
        );
    }

    @Test
    void 기존_recruit_수정() {
        //given
        RecruitUpdate recruitUpdate = RecruitUpdate.builder()
                .title("update-title")
                .status(RecruitStatus.INVALID)
                .startTime(LocalDateTime.of(2024, 9, 18, 1, 30))
                .endTime(LocalDateTime.of(2024, 10, 4, 3, 30))
                .applyDate(LocalDate.of(2024, 8, 19))
                .tags(new ArrayList<>(Arrays.asList("updatedTag1", "updatedTag2")))
                .link("https://www.update-title.com")
                .build();

        //when
        Recruit updatedRecruit = recruitService.update(1L, recruitUpdate);

        //then
        assertAll(
                () -> assertThat(updatedRecruit.getId()).isEqualTo(1L),
                () -> assertThat(updatedRecruit.getTitle()).isEqualTo("update-title"),
                () -> assertThat(updatedRecruit.getStatus()).isEqualTo(RecruitStatus.INVALID),
                () -> assertThat(updatedRecruit.getStartTime()).isEqualTo(LocalDateTime.of(2024, 9, 18, 1, 30)),
                () -> assertThat(updatedRecruit.getEndTime().isEqual(LocalDateTime.of(2024, 10, 4, 3, 30))),
                () -> assertThat(updatedRecruit.getApplyDate().isEqual(LocalDate.of(2024, 8, 19))),
                () -> assertThat(updatedRecruit.getTags().size()).isEqualTo(2),
                () -> assertEquals(updatedRecruit.getTags(), Arrays.asList("updatedTag1", "updatedTag2")),
                () -> assertThat(updatedRecruit.getLink()).isEqualTo("https://www.update-title.com")
        );
    }

    @Test
    void 수정시_없는_리소스로의_요청은_에러() {
        //given
        RecruitUpdate recruitUpdate = RecruitUpdate.builder()
                .title("update-title")
                .status(RecruitStatus.INVALID)
                .startTime(LocalDateTime.of(2024, 9, 18, 1, 30))
                .endTime(LocalDateTime.of(2024, 10, 4, 3, 30))
                .applyDate(LocalDate.of(2024, 8, 19))
                .tags(new ArrayList<>(Arrays.asList("updatedTag1", "updatedTag2")))
                .link("https://www.update-title.com")
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.update(2L, recruitUpdate)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void status만_수정시_없는_리소스로의_요청은_에러() {
        //given
        RecruitStatusUpdate recruitStatusUpdate = RecruitStatusUpdate.builder()
                .status(RecruitStatus.ACCEPTED).build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.updateStatus(2L, recruitStatusUpdate)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 기존_recruit_status_수정() {
        //given
        RecruitStatusUpdate recruitStatusUpdate = RecruitStatusUpdate.builder()
                .status(RecruitStatus.ACCEPTED).build();

        //when
        Recruit result = recruitService.updateStatus(1L, recruitStatusUpdate);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo("test-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.ACCEPTED),
                () -> assertThat(result.getStartTime()).isEqualTo(LocalDateTime.of(2024, 7, 19, 2, 30)),
                () -> assertThat(result.getEndTime().isEqual(LocalDateTime.of(2024, 7, 31, 17, 30))),
                () -> assertThat(result.getApplyDate().isEqual(LocalDate.of(2024, 7, 19))),
                () -> assertThat(result.getTags().size()).isEqualTo(3),
                () -> assertEquals(result.getTags(), Arrays.asList("코딩 테스트", "인턴", "대외 활동")),
                () -> assertThat(result.getLink()).isEqualTo("test-link")
        );
    }

    @Test
    void 기존_recruit_비활성화() {
        //given
        //when
        Recruit result = recruitService.disable(1L);

        //then
        assertAll(
                () -> assertThat(result.getIsActive()).isFalse(),
                () -> assertThat(result.getDisabledTime()).isNotNull(),
                () -> assertThat(result.getDisabledTime()).isBefore(LocalDateTime.now())
        );
    }

    @Test
    void 없는_유저는_비활성화_할수_없다() {
        //given
        //when
        //then
        assertThatThrownBy(
                () -> recruitService.disable(1234L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 비활성화된_유저는_getById로_찾을수_없다() {
        //given
        recruitService.disable(1L);

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.getById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }
}