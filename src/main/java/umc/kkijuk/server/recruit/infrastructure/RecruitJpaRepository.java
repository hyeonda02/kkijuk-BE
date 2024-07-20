package umc.kkijuk.server.recruit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecruitJpaRepository extends JpaRepository<RecruitEntity, Long> {
    Optional<RecruitEntity> findByIdAndIsActive(long id, Boolean isActive);

    @Query(value = "SELECT * FROM recruit as e WHERE DATE(e.end_time) = :endTime AND e.is_active = :isActive", nativeQuery = true)
    List<RecruitEntity> findAllByEndDateAndIsActive(@Param("endTime") LocalDate endTime, @Param("isActive") Boolean isActive);
}
