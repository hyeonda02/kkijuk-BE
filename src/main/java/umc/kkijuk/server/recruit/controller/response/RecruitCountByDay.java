package umc.kkijuk.server.recruit.controller.response;

import lombok.Getter;
import umc.kkijuk.server.recruit.domain.RecruitListByMonthDto;
import umc.kkijuk.server.recruit.domain.RecruitStatus;

import java.util.Map;
import java.util.function.Consumer;

@Getter
public class RecruitCountByDay {
    private int day;
    private int count;
    private int unapplied;
    private int planned;
    private int applying;
    private int accepted;
    private int rejected;

    public RecruitCountByDay(int day) {
        this.day = day;
        this.count = 0;
        this.unapplied = 0;
        this.planned = 0;
        this.applying = 0;
        this.accepted = 0;
        this.rejected = 0;
    }

    public void addItem(RecruitListByMonthDto dto) {
        Map<RecruitStatus, Consumer<RecruitListByMonthDto>> statusActions = Map.of(
                RecruitStatus.UNAPPLIED, item -> this.unapplied++,
                RecruitStatus.PLANNED, item -> this.planned++,
                RecruitStatus.APPLYING, item -> this.applying++,
                RecruitStatus.ACCEPTED, item -> this.accepted++,
                RecruitStatus.REJECTED, item -> this.rejected++
        );

        statusActions.get(dto.getStatus()).accept(dto);
        count++;
    }
}
