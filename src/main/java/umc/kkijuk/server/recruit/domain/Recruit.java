package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Recruit {
    private final Long id;
    private final String title;
    private final RecruitStatus status;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDate applyDate;
    private final List<String> tags;
    private final String link;

    public static Recruit from(RecruitCreateDto recruitCreateDto) {
        return Recruit.builder()
                .title(recruitCreateDto.getTitle())
                .status(recruitCreateDto.getStatus())
                .startTime(recruitCreateDto.getStartTime())
                .endTime(recruitCreateDto.getEndTime())
                .applyDate(recruitCreateDto.getApplyDate())
                .tags(recruitCreateDto.getTags())
                .link(recruitCreateDto.getLink())
                .build();
    }
}
