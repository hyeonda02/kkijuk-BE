package umc.kkijuk.server.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.dashboard.controller.port.DashBoardService;
import umc.kkijuk.server.dashboard.controller.response.DashBoardUserInfoResponse;
import umc.kkijuk.server.dashboard.controller.response.RecruitRemindResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final RecruitService recruitService;
    private final CareerRepository careerRepository;

    @Override
    public DashBoardUserInfoResponse getUserInfo(Member requestMember) {
        long recruitCount = recruitService.findAllValidRecruitByMember(requestMember, LocalDateTime.now()).size();
        long careerCount = careerRepository.findAllCareerByMemberId(requestMember.getId()).size();
        return DashBoardUserInfoResponse.from(requestMember, careerCount, recruitCount);
    }

    @Override
    public RecruitRemindResponse getTopTwoRecruitsByEndTime(Member requestMember) {
        List<Recruit> recruits = recruitService.getTopTwoRecruitsByEndTime(requestMember);
        return RecruitRemindResponse.from(recruits);
    }
}
