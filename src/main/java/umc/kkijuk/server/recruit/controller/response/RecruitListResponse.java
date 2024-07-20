package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.util.List;

@Getter
@Builder
public class RecruitListResponse {
    private final Long recruitId;
    private final String title;
    private final RecruitStatus status;
    private final List<String> tag;

    public static RecruitListResponse from(Recruit recruit) {
        return RecruitListResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .status(recruit.getStatus())
                .tag(recruit.getTags())
                .build();
    }
}
