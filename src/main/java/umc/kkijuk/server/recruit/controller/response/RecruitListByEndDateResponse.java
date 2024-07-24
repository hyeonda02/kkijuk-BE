package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.util.List;

@Getter
@Builder
public class RecruitListByEndDateResponse {
    private final int count;
    private final List<RecruitListResponse> recruits;

    public static RecruitListByEndDateResponse from(List<Recruit> recruits) {
        return RecruitListByEndDateResponse.builder()
                .count(recruits.size())
                .recruits(recruits.stream().map(RecruitListResponse::from).toList())
                .build();
    }
}
