package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.RecruitListByMonthDto;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class RecruitListByMonthResponse {
    private int totalCount;
    private List<RecruitCountByDay> dates;

    public static RecruitListByMonthResponse from(List<RecruitListByMonthDto> dto) {
        RecruitListByMonthResponse response = RecruitListByMonthResponse.builder()
                .totalCount(0)
                .dates(new ArrayList<>())
                .build();

        dto.forEach(item -> {
            Optional<RecruitCountByDay> dayInfo = response.dates.stream().filter(e -> e.getDay() == item.getEndTime().getDayOfMonth()).findAny();
            if (dayInfo.isPresent()) {
                dayInfo.get().addItem(item);
            } else {
                RecruitCountByDay day = new RecruitCountByDay(item.getEndTime().getDayOfMonth());
                day.addItem(item);
                response.dates.add(day);
                response.totalCount++;
            }
        });

        response.dates.sort(Comparator.comparing(RecruitCountByDay::getDay));
        return response;
    }
}
