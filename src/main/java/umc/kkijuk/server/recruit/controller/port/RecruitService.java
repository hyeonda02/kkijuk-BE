package umc.kkijuk.server.recruit.controller.port;

import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;

public interface RecruitService {
    Recruit create(RecruitCreateDto recruitCreateDto);

    Recruit update(Long recruitId, RecruitUpdate recruitUpdate);

    Recruit getById(long id);
}
