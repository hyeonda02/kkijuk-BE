package umc.kkijuk.server.recruit.service.port;

import umc.kkijuk.server.recruit.domain.Recruit;

import java.util.Optional;

public interface RecruitRepository {
    Optional<Recruit> findById(Long id);
    Recruit save(Recruit recruit);
}
