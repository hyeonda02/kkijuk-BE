package umc.kkijuk.server.recruit.controller.port;

import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.controller.response.RecruitReviewListByKeywordResponse;

public interface RecruitSearchService {

    public RecruitReviewListByKeywordResponse findRecruitByKeyword(Member requestMember, String keyword);
}
