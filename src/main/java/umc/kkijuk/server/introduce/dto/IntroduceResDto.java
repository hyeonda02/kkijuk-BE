package umc.kkijuk.server.introduce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Introduce;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class IntroduceResDto {
    private Long id;
    private Long recruitId;
    private String recruitTitle;
    private List<QuestionDto> questionList;
    private String deadline;
    private List<String> tags;
    private String link;
    private String updatedAt;
    private String timeSinceUpdate;
    private List<String> introduceList;
    private int state;

    @Builder
    public IntroduceResDto(Introduce introduce, List<QuestionDto> questionList, List<String> introduceList) {
        this.id = introduce.getId();
        this.recruitId=introduce.getRecruit().toModel().getId();
        this.recruitTitle=introduce.getRecruit().toModel().getTitle();
        this.questionList = questionList;
        this.deadline=formatUpdatedAt(introduce.getRecruit().toModel().getEndTime());
        this.tags=introduce.getRecruit().toModel().getTags();
        this.link=introduce.getRecruit().toModel().getLink();
        this.updatedAt = formatUpdatedAt(introduce.getUpdated_at());
        this.timeSinceUpdate = calculateTimeUntilDeadline(introduce.getUpdated_at());
        this.introduceList = introduceList;
        this.state=introduce.getState();
    }
    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }

    private String calculateTimeUntilDeadline(LocalDateTime deadline) {
        Duration duration = Duration.between(LocalDateTime.now(), deadline);
        long days = duration.toDays();
        return days > 0 ? "D-" + days : "공고 기한 마감";
    }

}
