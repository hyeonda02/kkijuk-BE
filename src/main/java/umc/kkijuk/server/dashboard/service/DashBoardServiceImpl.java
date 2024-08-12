package umc.kkijuk.server.dashboard.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.dashboard.controller.port.DashBoardService;
import umc.kkijuk.server.dashboard.controller.response.DashBoardUserInfoResponse;
import umc.kkijuk.server.dashboard.controller.response.IntroduceRemindResponse;
import umc.kkijuk.server.dashboard.controller.response.RecruitRemindResponse;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.port.RecruitService;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final RecruitService recruitService;
    private final CareerRepository careerRepository;
    private final IntroduceRepository introduceRepository;

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

    @Override
    public List<IntroduceRemindResponse> getHomeIntro(Member requestMember) {
        Pageable pageable = PageRequest.of(0, 2);  // 첫 페이지에서 2개의 결과만 가져옴
        Page<Introduce> introduces = introduceRepository.findByMemberIdAndStateOrderByEndTimeAsc(requestMember.getId(), 0, pageable);

        return introduces.stream()
                .map(IntroduceRemindResponse::new)
                .collect(Collectors.toList());
    }
}
