package umc.kkijuk.server.recruit.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecruitListByMonthDto {
    private final LocalDateTime endTime;
    private final RecruitStatus status;

    public static RecruitListByMonthDto from(Recruit recruit) {
        return RecruitListByMonthDto.builder()
                .endTime(recruit.getEndTime())
                .status(recruit.getStatus())
                .build();
    }
}
