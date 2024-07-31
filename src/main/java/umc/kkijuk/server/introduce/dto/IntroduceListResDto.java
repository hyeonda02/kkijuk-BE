package umc.kkijuk.server.introduce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.member.domain.Member;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class IntroduceListResDto {
    private Long id;
    private Long memberId;
    private Long recruitId;
    private String recruitTitle;
    private String deadline;
    private String updatedAt;
    private String timeSinceUpdate;
    private int state;

    @Builder
    public IntroduceListResDto(Introduce introduce) {
        this.id = introduce.getId();
        this.memberId = introduce.getMember().getId();
        this.recruitId=introduce.getRecruit().toModel().getId();
        this.recruitTitle=introduce.getRecruit().toModel().getTitle();
        this.deadline=formatUpdatedAt(introduce.getRecruit().toModel().getEndTime());
        this.updatedAt = formatUpdatedAt(introduce.getUpdatedAt());
        this.timeSinceUpdate = calculateTimeUntilDeadline(introduce.getUpdatedAt(), introduce.getRecruit().toModel().getEndTime());
        this.state=introduce.getState();
    }

    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }

    private String calculateTimeUntilDeadline(LocalDateTime updatedAt, LocalDateTime deadline) {
        Duration duration = Duration.between(updatedAt, deadline);
        long days = duration.toDays();
        return days > 0 ? "D-" + days : "공고 기한 마감";
    }
}
