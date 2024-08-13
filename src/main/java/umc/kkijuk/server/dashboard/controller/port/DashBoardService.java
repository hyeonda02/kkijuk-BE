package umc.kkijuk.server.dashboard.controller.port;

import jakarta.transaction.Transactional;
import umc.kkijuk.server.dashboard.controller.response.DashBoardUserInfoResponse;
import umc.kkijuk.server.dashboard.controller.response.IntroduceRemindResponse;
import umc.kkijuk.server.dashboard.controller.response.RecruitRemindResponse;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.util.List;
import java.util.stream.Collectors;

public interface DashBoardService {
    DashBoardUserInfoResponse getUserInfo(Member requestMember);
    RecruitRemindResponse getTopTwoRecruitsByEndTime(Member requestMember);
    List<IntroduceRemindResponse> getHomeIntro(Member requestMember);

}
