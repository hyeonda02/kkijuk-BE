package umc.kkijuk.server.recruit.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;
import umc.kkijuk.server.recruit.domain.RecruitStatusUpdate;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitRepository recruitRepository;

    @Override
    public Recruit getById(long id) {
        return recruitRepository.findByIdAndIsActive(id, true)
                .orElseThrow(() -> new ResourceNotFoundException("Recruit", id));
    }

    @Override
    @Transactional
    public Recruit create(RecruitCreateDto recruitCreateDto) {
        Recruit recruit = Recruit.from(recruitCreateDto);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit update(Long recruitId, RecruitUpdate recruitUpdate) {
        Recruit recruit = getById(recruitId);
        recruit = recruit.update(recruitUpdate);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit updateStatus(long recruitId, RecruitStatusUpdate recruitStatusUpdate) {
        Recruit recruit = getById(recruitId);
        recruit = recruit.updateStatus(recruitStatusUpdate);
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public Recruit disable(long recruitId) {
        Recruit recruit = getById(recruitId);
        recruit = recruit.disable();
        return recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public List<Recruit> findAllByEndTime(LocalDate date) {
        return recruitRepository.findAllByEndDateAndIsActive(date, true);
    }

    @Override
    @Transactional
    public List<Recruit> findAllByEndTimeAfter(LocalDateTime endTime) {
        return recruitRepository.findAllByEndTimeAfterAndIsActive(endTime, true);
    }
}
