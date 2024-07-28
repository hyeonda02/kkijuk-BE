package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ValidRecruitDto {
    private Long id;
    private RecruitStatus status;
    private String title;
    private List<String> tags;

    public static ValidRecruitDto from(Recruit recruit) {
        return ValidRecruitDto.builder()
                .id(recruit.getId())
                .status(recruit.getStatus())
                .title(recruit.getTitle())
                .tags(recruit.getTags())
                .build();
    }
}
