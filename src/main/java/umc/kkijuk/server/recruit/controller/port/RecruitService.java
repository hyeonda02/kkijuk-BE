package umc.kkijuk.server.recruit.controller.port;

import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;
import umc.kkijuk.server.recruit.domain.RecruitStatusUpdate;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RecruitService {
    Recruit create(RecruitCreateDto recruitCreateDto);

    Recruit update(Long recruitId, RecruitUpdate recruitUpdate);

    Recruit getById(long id);

    Recruit updateStatus(long recruitId, RecruitStatusUpdate recruitStatusUpdate);

    Recruit disable(long recruitId);

    List<Recruit> findAllByEndTime(LocalDate date);

    List<Recruit> findAllByEndTimeAfter(LocalDateTime endTime);
}
