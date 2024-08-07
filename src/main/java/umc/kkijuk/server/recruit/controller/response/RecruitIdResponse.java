package umc.kkijuk.server.recruit.controller.response;

import lombok.*;
import umc.kkijuk.server.recruit.domain.Recruit;

@Getter
public class RecruitIdResponse {
    private Long id;

    private RecruitIdResponse() {
    }

    @Builder
    public RecruitIdResponse(Long id) {
        this.id = id;
    }

    public static RecruitIdResponse from(Recruit recruit) {
        return RecruitIdResponse.builder()
                .id(recruit.getId())
                .build();
    }
}
