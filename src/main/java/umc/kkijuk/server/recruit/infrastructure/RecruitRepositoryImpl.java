package umc.kkijuk.server.recruit.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryImpl implements RecruitRepository {
    private final RecruitJpaRepository recruitJpaRepository;

    @Override
    public Optional<Recruit> findById(Long id) {
        return recruitJpaRepository.findById(id).map(RecruitEntity::toModel);
    }

    @Override
    public Recruit save(Recruit recruit) {
        return recruitJpaRepository.save(RecruitEntity.from(recruit)).toModel();
    }

    @Override
    public Recruit getById(long id) {
        return findByIdAndIsActive(id, true)
                .orElseThrow(() -> new ResourceNotFoundException("recruit", id));
    }

    @Override
    public Optional<Recruit> findByIdAndIsActive(long id, boolean active) {
        return recruitJpaRepository.findByIdAndActive(id, active).map(RecruitEntity::toModel);
    }

    @Override
    public List<Recruit> findAllByEndDateAndActive(LocalDate endTime, boolean active) {
        return recruitJpaRepository.findAllByEndDateAndActive(endTime, active)
                .stream().map(RecruitEntity::toModel).toList();
    }

    @Override
    public List<Recruit> findAllByEndTimeAfterAndActive(LocalDateTime endTime, boolean active) {
        return recruitJpaRepository.findAllByEndTimeAfterAndActive(endTime,active)
                .stream().map(RecruitEntity::toModel).toList();
    }

    @Override
    public List<Recruit> findAllActiveRecruitByMemberIdAndEndDate(Long memberId, LocalDate endDate) {
        return recruitJpaRepository.findAllByMemberIdAndEndDateAndActive(memberId, endDate, true)
                .stream().map(RecruitEntity::toModel).toList();
    }

    @Override
    public List<Recruit> findAllActiveRecruitByMemberIdAndEndTimeAfter(Long memberId, LocalDateTime endTime) {
        return recruitJpaRepository.findAllByMemberIdAndEndTimeAfterAndActive(memberId, endTime, true)
                .stream().map(RecruitEntity::toModel).toList();
    }

    @Override
    public List<Recruit> findAllActiveRecruitByMemberId(Long memberId) {
        return recruitJpaRepository.findAllByMemberIdAndActive(memberId, true)
                .stream().map(RecruitEntity::toModel).toList();
    }

    @Override
    public List<Recruit> findAllActiveRecruitByMemberIdAndMonth(Long memberId, Integer year, Integer month) {
        return recruitJpaRepository.findAllByMemberIdAndMonth(memberId, year, month)
                .stream().map(RecruitEntity::toModel).toList();
    }
}
