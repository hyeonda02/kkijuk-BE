package umc.kkijuk.server.recruit.controller.port;

import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreate;
import umc.kkijuk.server.recruit.domain.RecruitStatusUpdate;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;

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
}
