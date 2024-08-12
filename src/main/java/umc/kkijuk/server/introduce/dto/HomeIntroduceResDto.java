package umc.kkijuk.server.introduce.dto;

import lombok.Builder;
import umc.kkijuk.server.introduce.domain.Introduce;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeIntroduceResDto {
    private String recruitTitle;
    private String deadline;

    @Builder
    public HomeIntroduceResDto(Introduce introduce) {
        this.recruitTitle = introduce.getRecruit().toModel().getTitle();
        this.deadline = calculateTimeUntilDeadline(LocalDateTime.now(), introduce.getRecruit().toModel().getEndTime());
    }

    private String calculateTimeUntilDeadline(LocalDateTime updatedAt, LocalDateTime deadline) {
        Duration duration = Duration.between(updatedAt, deadline);
        long days = duration.toDays() + 1;
        return "D-" + days;
    }
}
