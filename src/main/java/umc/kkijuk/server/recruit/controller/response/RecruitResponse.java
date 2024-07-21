package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;

@Getter
@Builder
public class RecruitResponse {
    private final Long id;
    public static RecruitResponse from(Recruit recruit) {
        return RecruitResponse.builder()
                .id(recruit.getId())
                .build();
    }
}
