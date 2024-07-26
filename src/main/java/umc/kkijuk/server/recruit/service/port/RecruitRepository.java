package umc.kkijuk.server.recruit.service.port;

import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecruitRepository {
    Optional<Recruit> findById(Long id);
    Recruit save(Recruit recruit);
    Recruit getById(long id);
    Optional<Recruit> findByIdAndIsActive(long id, boolean active);
    List<Recruit> findAllByEndDateAndActive(LocalDate endTime, boolean active);
    List<Recruit> findAllByEndTimeAfterAndActive(LocalDateTime endTime, boolean active);
    List<Recruit> findAllActiveRecruitByMemberIdAndEndDate(Long memberId, LocalDate endTime);
    List<Recruit> findAllActiveRecruitByMemberIdAndEndTimeAfter(Long memberId, LocalDateTime endTime);
    List<Recruit> findAllActiveRecruitByMemberId(Long memberId);
}
