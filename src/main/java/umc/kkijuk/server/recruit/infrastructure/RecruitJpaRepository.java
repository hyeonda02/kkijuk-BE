package umc.kkijuk.server.recruit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitJpaRepository extends JpaRepository<RecruitEntity, Long> {
    Optional<RecruitEntity> findByIdAndIsActive(long id, Boolean isActive);
}
