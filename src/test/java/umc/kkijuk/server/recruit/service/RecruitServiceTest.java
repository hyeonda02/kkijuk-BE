package umc.kkijuk.server.recruit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import umc.kkijuk.server.common.domian.exception.RecruitOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.*;
import umc.kkijuk.server.recruit.mock.FakeRecruitRepository;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RecruitServiceTest {
    private RecruitService recruitService;
    private Member requestMember;

    private final Long testMemberId = 3333L;
    private final LocalDateTime testRecruitStartTime = LocalDateTime.of(2023, 7, 19, 2, 30);
    private final LocalDateTime testRecruitEndTime = LocalDateTime.of(2023, 7, 31, 17, 30);
    private final LocalDate testRecruitApplyDate = LocalDate.of(2023, 7, 19);
    private final LocalDateTime newRecruitStartTime = LocalDateTime.of(2024, 7, 19, 2, 30);
    private final LocalDateTime newRecruitEndTime = LocalDateTime.of(2024, 7, 30, 2, 30);
    private final LocalDate newRecruitApplyDate = LocalDate.of(2024, 7, 25);

    @BeforeEach
    void init() {
        requestMember = Member.builder()
                .id(testMemberId)
                .email("test-email@test.com")
                .name("test-name")
                .phoneNumber("test-test-test")
                .birthDate(LocalDate.of(2024, 7, 25))
                .password("test-password")
                .marketingAgree(true)
                .userState(State.ACTIVATE)
                .build();


        RecruitRepository recruitRepository = new FakeRecruitRepository();
        this.recruitService = RecruitServiceImpl.builder()
                .recruitRepository(recruitRepository)
                .build();

        Recruit recruit = Recruit.builder()
                .memberId(testMemberId)
                .title("test-title")
                .status(RecruitStatus.PLANNED)
                .startTime(testRecruitStartTime)
                .endTime(testRecruitEndTime)
                .applyDate(testRecruitApplyDate)
                .tags(new ArrayList<>(Arrays.asList("코딩 테스트", "인턴", "대외 활동")))
                .link("test-link")
                .active(true)
                .build();


        recruitRepository.save(recruit);
    }

    @Test
    void create_새로운_recruit_만들기() {
        //given
        RecruitCreate recruitCreate = RecruitCreate.builder()
                .title("dto-title")
                .status(RecruitStatus.PLANNED)
                .startTime(newRecruitStartTime)
                .endTime(newRecruitEndTime)
                .applyDate(newRecruitApplyDate)
                .tags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")))
                .link("https://www.dto-title.com")
                .build();

        //when
        Recruit result = recruitService.create(requestMember, recruitCreate);

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(2L),
                () -> assertThat(result.getMemberId()).isEqualTo(testMemberId),
                () -> assertThat(result.getTitle()).isEqualTo("dto-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.PLANNED),
                () -> assertThat(result.getStartTime()).isEqualTo(newRecruitStartTime),
                () -> assertThat(result.getEndTime()).isEqualTo(newRecruitEndTime),
                () -> assertThat(result.getApplyDate()).isEqualTo(newRecruitApplyDate),
                () -> assertThat(result.getTags().size()).isEqualTo(3),
                () -> assertThat(result.getLink()).isEqualTo("https://www.dto-title.com")
        );
    }

    @Test
    void create_새로운_recruit_만들기_nullable() {
        //given
        RecruitCreate recruitCreate = RecruitCreate.builder()
                .title("dto-title")
                .status(RecruitStatus.PLANNED)
                .startTime(newRecruitStartTime)
                .endTime(newRecruitEndTime)
                .build();

        //when
        Recruit result = recruitService.create(requestMember, recruitCreate);

        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(2L),
                () -> assertThat(result.getTitle()).isEqualTo("dto-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.PLANNED),
                () -> assertThat(result.getStartTime()).isEqualTo(newRecruitStartTime),
                () -> assertThat(result.getEndTime()).isEqualTo(newRecruitEndTime),
                () -> assertThat(result.getApplyDate()).isNull(),
                () -> assertThat(result.getTags().size()).isZero(),
                () -> assertThat(result.getLink()).isNull()
        );
    }

    @Test
    void update_기존_recruit_수정() {
        //given
        RecruitUpdate recruitUpdate = RecruitUpdate.builder()
                .title("update-title")
                .status(RecruitStatus.PLANNED)
                .startTime(newRecruitStartTime)
                .endTime(newRecruitEndTime)
                .applyDate(newRecruitApplyDate)
                .tags(new ArrayList<>(Arrays.asList("updatedTag1", "updatedTag2")))
                .link("https://www.update-title.com")
                .build();

        //when
        Recruit updatedRecruit = recruitService.update(requestMember, 1L, recruitUpdate);

        //then
        assertAll(
                () -> assertThat(updatedRecruit.getId()).isEqualTo(1L),
                () -> assertThat(updatedRecruit.getTitle()).isEqualTo("update-title"),
                () -> assertThat(updatedRecruit.getStatus()).isEqualTo(RecruitStatus.PLANNED),
                () -> assertThat(updatedRecruit.getStartTime()).isEqualTo(newRecruitStartTime),
                () -> assertThat(updatedRecruit.getEndTime()).isEqualTo(newRecruitEndTime),
                () -> assertThat(updatedRecruit.getApplyDate()).isEqualTo(newRecruitApplyDate),
                () -> assertThat(updatedRecruit.getTags().size()).isEqualTo(2),
                () -> assertEquals(updatedRecruit.getTags(), Arrays.asList("updatedTag1", "updatedTag2")),
                () -> assertThat(updatedRecruit.getLink()).isEqualTo("https://www.update-title.com")
        );
    }

    @Test
    void update_수정시_없는_리소스로의_요청은_에러() {
        //given
        RecruitUpdate recruitUpdate = RecruitUpdate.builder()
                .title("update-title")
                .status(RecruitStatus.PLANNED)
                .startTime(newRecruitStartTime)
                .endTime(newRecruitEndTime)
                .applyDate(newRecruitApplyDate)
                .tags(new ArrayList<>(Arrays.asList("updatedTag1", "updatedTag2")))
                .link("https://www.update-title.com")
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.update(requestMember, 44444L, recruitUpdate))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_다른_사용자가_수정요청시_에러() {
        //given
        RecruitUpdate recruitUpdate = RecruitUpdate.builder()
                .title("update-title")
                .status(RecruitStatus.PLANNED)
                .startTime(newRecruitStartTime)
                .endTime(newRecruitEndTime)
                .applyDate(newRecruitApplyDate)
                .tags(new ArrayList<>(Arrays.asList("updatedTag1", "updatedTag2")))
                .link("https://www.update-title.com")
                .build();

        Member anotherMember = Member.builder()
                .id(2222L)
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.update(anotherMember, 1L, recruitUpdate))
                .isInstanceOf(RecruitOwnerMismatchException.class);
    }

    @Test
    void updateStatus_다른_사용자가_수정요청시_에러() {
        //given
        RecruitStatusUpdate recruitStatusUpdate = RecruitStatusUpdate.builder()
                .status(RecruitStatus.ACCEPTED).build();

        Member anotherMember = Member.builder()
                .id(2222L)
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.updateStatus(anotherMember, 1L, recruitStatusUpdate))
                .isInstanceOf(RecruitOwnerMismatchException.class);
    }

    @Test
    void updateStatus_status만_수정시_없는_리소스로의_요청은_에러() {
        //given
        RecruitStatusUpdate recruitStatusUpdate = RecruitStatusUpdate.builder()
                .status(RecruitStatus.ACCEPTED).build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.updateStatus(requestMember, 2L, recruitStatusUpdate)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateStatus_기존_recruit_status_수정() {
        //given
        RecruitStatusUpdate recruitStatusUpdate = RecruitStatusUpdate.builder()
                .status(RecruitStatus.ACCEPTED).build();

        //when
        Recruit result = recruitService.updateStatus(requestMember, 1L, recruitStatusUpdate);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo("test-title"),
                () -> assertThat(result.getStatus()).isEqualTo(RecruitStatus.ACCEPTED),
                () -> assertThat(result.getStartTime()).isEqualTo(testRecruitStartTime),
                () -> assertThat(result.getEndTime()).isEqualTo(testRecruitEndTime),
                () -> assertThat(result.getApplyDate()).isEqualTo(testRecruitApplyDate),
                () -> assertThat(result.getTags().size()).isEqualTo(3),
                () -> assertEquals(result.getTags(), Arrays.asList("코딩 테스트", "인턴", "대외 활동")),
                () -> assertThat(result.getLink()).isEqualTo("test-link")
        );
    }

    @Test
    void disable_기존_recruit_비활성화() {
        //given
        //when
        Recruit result = recruitService.disable(requestMember, 1L);

        //then
        assertAll(
                () -> assertThat(result.isActive()).isFalse(),
                () -> assertThat(result.getDisabledTime()).isNotNull(),
                () -> assertThat(result.getDisabledTime()).isBefore(LocalDateTime.now())
        );
    }

    @Test
    void disable_없는_유저는_비활성화_할수_없다() {
        //given
        //when
        //then
        assertThatThrownBy(
                () -> recruitService.disable(requestMember, 1234L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void disable_비활성화된_유저는_getById로_찾을수_없다() {
        //given
        recruitService.disable(requestMember, 1L);

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void disable_다른_사용자가_요청할수_없다() {
        //given
        Member anotherMember = Member.builder()
                .id(2222L)
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> recruitService.disable(anotherMember, 1L))
                .isInstanceOf(RecruitOwnerMismatchException.class);
    }
    @Test
    void findAllByEndTime_마감시간이_date인_모든_active공고를_불러온다() {
        //given
        int times = 10;
        for (int i = 0; i < times; i++){
            recruitService.create(requestMember, RecruitCreate.builder()
                .endTime(newRecruitEndTime)
                .build());
        }

        //when
        List<Recruit> result = recruitService.findAllByEndTime(requestMember, newRecruitEndTime.toLocalDate());

        //then
        assertThat(result.size()).isEqualTo(times);
    }

    @Test
    void findAllByEndTime_마감시간이_date인_inactive공고는_제외한다() {
        //given
        int times = 10;
        for (int i = 0; i < times; i++){
            Recruit recruit = recruitService.create(requestMember, RecruitCreate.builder()
                    .endTime(newRecruitStartTime)
                    .build());
            recruitService.disable(requestMember, recruit.getId());
        }

        //when
        List<Recruit> result = recruitService.findAllByEndTime(requestMember, newRecruitStartTime.toLocalDate());

        //then
        assertThat(result.size()).isZero();
    }

    @Test
    void findAllByEndTime_active_inactive공고() {
        //given
        int times = 10;
        for (int i = 0; i < times; i++){
            Recruit recruit = recruitService.create(requestMember, RecruitCreate.builder()
                    .endTime(newRecruitEndTime)
                    .build());
            if (i % 2 == 0)
                recruitService.disable(requestMember, recruit.getId());
        }
        //when
        List<Recruit> result = recruitService.findAllByEndTime(requestMember, newRecruitEndTime.toLocalDate());

        //then
        assertThat(result.size()).isEqualTo(times / 2);
    }

    @Test
    void findAllByEndTimeAfter_마감이_안지난_active공고를_불러온다() {
        //given
        //when
        List<Recruit> result = recruitService.findAllByEndTimeAfter(requestMember, testRecruitEndTime.minusDays(1));

        //then
        assertThat(result.size()).isOne();
    }

    @Test
    void findAllByEndTimeAfter_inactive공고는_제외한다() {
        //given
        recruitService.disable(requestMember, 1L);

        //when
        List<Recruit> result = recruitService.findAllByEndTimeAfter(requestMember, testRecruitEndTime.minusDays(1));

        //then
        assertThat(result.size()).isZero();
    }

    @Test
    void findAllValidRecruitByMemberIdTest() {
        //given
        for (RecruitStatus value : RecruitStatus.values()) {
            RecruitCreate recruitCreate = RecruitCreate.builder()
                    .title("dto-title")
                    .status(value)
                    .startTime(LocalDateTime.now().minusDays(1))
                    .endTime(LocalDateTime.now().plusDays(1))
                    .applyDate(LocalDate.now())
                    .tags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")))
                    .link("https://www.dto-title.com")
                    .build();

            RecruitCreate ExpiredRecruitCreate = RecruitCreate.builder()
                    .title("dto-title")
                    .status(value)
                    .startTime(LocalDateTime.now().minusDays(1))
                    .endTime(LocalDateTime.now().minusDays(1))
                    .applyDate(LocalDate.now())
                    .tags(new ArrayList<>(Arrays.asList("tag1", "tag2", "tag3")))
                    .link("https://www.dto-title.com")
                    .build();
            recruitService.create(requestMember, recruitCreate);
            recruitService.create(requestMember, ExpiredRecruitCreate);
        }

        //when
        List<ValidRecruitDto> result = recruitService.findAllValidRecruitByMemberId(testMemberId, LocalDateTime.now());

        //then
        assertAll(
                () -> assertThat(result.stream().filter(item -> item.getStatus().equals(RecruitStatus.ACCEPTED)).count()).isEqualTo(2),
                () -> assertThat(result.stream().filter(item -> item.getStatus().equals(RecruitStatus.REJECTED)).count()).isEqualTo(2),
                () -> assertThat(result.stream().filter(item -> item.getStatus().equals(RecruitStatus.APPLYING)).count()).isEqualTo(2),
                () -> assertThat(result.stream().filter(item -> item.getStatus().equals(RecruitStatus.PLANNED)).count()).isOne(),
                () -> assertThat(result.stream().filter(item -> item.getStatus().equals(RecruitStatus.UNAPPLIED)).count()).isOne(),
                () -> assertThat(result.size()).isEqualTo(8)
        );
    }
}