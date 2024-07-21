package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final boolean active;
    private final LocalDateTime disabledTime;

    public static Recruit from(RecruitCreateDto recruitCreateDto) {
        return Recruit.builder()
                .title(recruitCreateDto.getTitle())
                .status(recruitCreateDto.getStatus())
                .startTime(recruitCreateDto.getStartTime())
                .endTime(recruitCreateDto.getEndTime())
                .applyDate(recruitCreateDto.getApplyDate())
                .tags(recruitCreateDto.getTags() != null ? recruitCreateDto.getTags() : new ArrayList<>())
                .link(recruitCreateDto.getLink())
                .active(true)
                .build();
    }

    public Recruit update(RecruitUpdate recruitUpdate) {
        return Recruit.builder()
                .id(this.id)
                .title(recruitUpdate.getTitle())
                .status(recruitUpdate.getStatus())
                .startTime(recruitUpdate.getStartTime())
                .endTime(recruitUpdate.getEndTime())
                .applyDate(recruitUpdate.getApplyDate())
                .tags(recruitUpdate.getTags() != null ? recruitUpdate.getTags() : new ArrayList<>())
                .link(recruitUpdate.getLink())
                .active(this.active)
                .build();
    }

    public Recruit updateStatus(RecruitStatusUpdate recruitStatusUpdate) {
        return Recruit.builder()
                .id(this.id)
                .title(this.getTitle())
                .status(recruitStatusUpdate.getStatus())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .applyDate(this.getApplyDate())
                .tags(this.getTags())
                .link(this.getLink())
                .active(this.active)
                .build();
    }

    public Recruit disable() {
        return Recruit.builder()
                .id(this.id)
                .title(this.getTitle())
                .status(this.getStatus())
                .startTime(this.getStartTime())
                .endTime(this.getEndTime())
                .applyDate(this.getApplyDate())
                .tags(this.getTags())
                .link(this.getLink())
                .active(false)
                .disabledTime(LocalDateTime.now())
                .build();
    }
}