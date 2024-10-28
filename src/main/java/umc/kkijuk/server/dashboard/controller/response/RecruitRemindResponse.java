package umc.kkijuk.server.dashboard.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.controller.response.RecruitTitleEndTime;
import umc.kkijuk.server.recruit.domain.Recruit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class RecruitRemindResponse {
    List<RecruitTitleEndTime> recruits;

    public static RecruitRemindResponse from(List<Recruit> recruits) {
        List<RecruitTitleEndTime> outputs = new ArrayList<>();
        LocalDate now = LocalDate.now();
        recruits.forEach(recruit -> outputs.add(RecruitTitleEndTime.builder()
                .id(recruit.getId())
                .title(recruit.getTitle())
                .endTime(recruit.getEndTime()).dDay(ChronoUnit.DAYS.between(now, recruit.getEndTime()))
                .build())
        );
        return RecruitRemindResponse.builder()
                .recruits(outputs)
                .build();
    }
}
