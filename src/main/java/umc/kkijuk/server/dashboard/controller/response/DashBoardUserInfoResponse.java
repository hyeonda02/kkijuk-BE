package umc.kkijuk.server.dashboard.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import umc.kkijuk.server.member.domain.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
public class DashBoardUserInfoResponse {
    private final String userName;
    private final long monthDuration;
    private final long careerCount;
    private final long recruitCount;

    public static DashBoardUserInfoResponse from(Member member, long careerCount, long recruitCount) {
        return DashBoardUserInfoResponse.builder()
                .userName(member.getName())
                .monthDuration(ChronoUnit.MONTHS.between(member.getCreatedAt() != null ? member.getCreatedAt().toLocalDate() : LocalDate.now(), LocalDate.now()))
                .careerCount(careerCount)
                .recruitCount(recruitCount)
                .build();
    }
}
