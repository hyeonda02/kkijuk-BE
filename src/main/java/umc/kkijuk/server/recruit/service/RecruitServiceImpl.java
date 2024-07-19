package umc.kkijuk.server.recruit.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;
import umc.kkijuk.server.recruit.domain.RecruitUpdate;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitRepository recruitRepository;

    @Override
    public Recruit getById(long id) {
        return recruitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recruit", id));
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


}
