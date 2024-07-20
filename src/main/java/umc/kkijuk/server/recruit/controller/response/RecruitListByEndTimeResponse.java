package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.util.List;

@Getter
@Builder
public class RecruitListByEndTimeResponse {
    private final int count;
    private final List<RecruitListResponse> recruits;

    public static RecruitListByEndTimeResponse from(List<Recruit> recruits) {
        return RecruitListByEndTimeResponse.builder()
                .count(recruits.size())
                .recruits(recruits.stream().map(RecruitListResponse::from).toList())
                .build();
    }
}
