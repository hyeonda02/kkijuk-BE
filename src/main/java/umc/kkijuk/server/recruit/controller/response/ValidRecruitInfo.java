package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.ValidRecruitDto;

import java.util.List;

@Getter
@Builder
public class ValidRecruitInfo {
    private Long id;
    private String title;
    private List<String> tags;

    public static ValidRecruitInfo from(ValidRecruitDto dto) {
        return ValidRecruitInfo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .tags(dto.getTags())
                .build();
    }
}
