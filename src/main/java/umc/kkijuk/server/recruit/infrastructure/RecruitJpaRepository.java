package umc.kkijuk.server.recruit.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecruitJpaRepository extends JpaRepository<RecruitEntity, Long> {
    Optional<RecruitEntity> findByIdAndActive(long id, boolean active);

    @Query(value = "SELECT * FROM recruit as e WHERE DATE(e.end_time) = :endDate AND e.active = :isActive", nativeQuery = true)
    List<RecruitEntity> findAllByEndDateAndActive(@Param("endDate") LocalDate endDate, @Param("isActive") boolean isActive);

    @Query(value = "SELECT * FROM recruit as e WHERE e.member_id = :memberId AND DATE(e.end_time) = :endDate AND e.active = :isActive", nativeQuery = true)
    List<RecruitEntity> findAllByMemberIdAndEndDateAndActive(@Param("memberId") Long memberId, @Param("endDate") LocalDate endDate, @Param("isActive") boolean isActive);

    List<RecruitEntity> findAllByEndTimeAfterAndActive(LocalDateTime endTime, boolean active);
    List<RecruitEntity> findAllByMemberIdAndEndTimeAfterAndActive(Long memberId, LocalDateTime endTime, boolean active);
    List<RecruitEntity> findAllByMemberIdAndActive(Long memberId, boolean active);
    @Query(value = "SELECT * FROM recruit as e WHERE e.member_id = :memberId AND e.active = true AND YEAR(e.end_time) = :year AND MONTH(e.end_time) = :month", nativeQuery = true)
    List<RecruitEntity> findAllByMemberIdAndMonth(@Param("memberId") Long memberId, @Param("year") Integer year, @Param("month") Integer month);
}
