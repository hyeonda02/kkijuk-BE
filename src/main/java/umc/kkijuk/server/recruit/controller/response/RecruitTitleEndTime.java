package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecruitTitleEndTime {
    private final Long id;
    private final String title;
    private final LocalDateTime endTime;
    private final long dDay;
}
