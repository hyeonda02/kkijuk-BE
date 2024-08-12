package umc.kkijuk.server.dashboard.controller.port;

import umc.kkijuk.server.dashboard.controller.response.DashBoardUserInfoResponse;
import umc.kkijuk.server.dashboard.controller.response.RecruitRemindResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.util.List;

public interface DashBoardService {
    DashBoardUserInfoResponse getUserInfo(Member requestMember);
    RecruitRemindResponse getTopTwoRecruitsByEndTime(Member requestMember);
}
