package umc.kkijuk.server.recruit.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitCreateDto;
import umc.kkijuk.server.recruit.service.port.RecruitRepository;

@Service
@Builder
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitRepository recruitRepository;

    @Override
    public Recruit create(RecruitCreateDto recruitCreateDto) {
        Recruit recruit = Recruit.from(recruitCreateDto);
        return recruitRepository.save(recruit);
    }
}
