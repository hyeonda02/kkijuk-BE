package umc.kkijuk.server.recruit.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

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
    public Optional<Recruit> findByIdAndIsActive(long id, Boolean isActive) {
        return recruitJpaRepository.findByIdAndIsActive(id, isActive).map(RecruitEntity::toModel);
    }
}
