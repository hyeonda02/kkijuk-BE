package umc.kkijuk.server.recruit.service.port;

import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecruitRepository {
    Optional<Recruit> findById(Long id);
    Recruit save(Recruit recruit);
    Recruit getById(long id);
    Optional<Recruit> findByIdAndIsActive(long id, boolean active);

    List<Recruit> findAllByEndDateAndIsActive(LocalDate endTime, boolean active);
}
