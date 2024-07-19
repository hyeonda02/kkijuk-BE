package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;

@Getter
@Builder
public class RecruitIdResponse {
    private final Long id;
    public static RecruitIdResponse from(Recruit recruit) {
        return RecruitIdResponse.builder()
                .id(recruit.getId())
                .build();
    }
}
