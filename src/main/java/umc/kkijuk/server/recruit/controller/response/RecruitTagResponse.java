package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecruitTagResponse {
    private final List<String> tags;

    public static RecruitTagResponse from(List<String> tags) {
        return RecruitTagResponse.builder()
                .tags(tags)
                .build();
    }
}
