package umc.kkijuk.server.recruit.controller.port;

import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RecruitService {
    @Transactional
    Recruit create(Member member, RecruitCreate recruitCreate);

    Recruit update(Member member, Long recruitId, RecruitUpdate recruitUpdate);

    Recruit getById(long id);

    Recruit updateStatus(Member member, long recruitId, RecruitStatusUpdate recruitStatusUpdate);

    Recruit disable(Member member, long recruitId);

    List<Recruit> findAllByEndTime(Member member, LocalDate date);

    List<Recruit> findAllByEndTimeAfter(Member member, LocalDateTime endTime);

    List<ValidRecruitDto> findAllValidRecruitByMemberId(Member member, LocalDateTime endTime);

    List<RecruitListByMonthDto> findAllValidRecruitByYearAndMonth(Member member, Integer year, Integer month);

    Recruit updateApplyDate(Member requestMember, long recruitId, RecruitApplyDateUpdate recruitApplyDateUpdate);
}
