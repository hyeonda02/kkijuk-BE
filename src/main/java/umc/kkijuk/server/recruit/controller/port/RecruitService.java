package umc.kkijuk.server.recruit.controller.port;

import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;

public interface RecruitService {
    Recruit create(RecruitCreateDto recruitCreateDto);
}
