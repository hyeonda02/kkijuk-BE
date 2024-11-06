package umc.kkijuk.server.introduce.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.dto.QuestionDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class IntroduceResponse {
    private Long id;
    private Long recruitId;
    private Long memberId;
    private String recruitTitle;
    private List<QuestionDto> questionList;
    private String deadline;
    private List<String> tags;
    private String link;
    private String updatedAt;
    private String timeSinceUpdate;
    /*   private List<String> introduceList;*/
    private int state;

    @Builder
    public IntroduceResponse(Introduce introduce, List<QuestionDto> questionList/*, List<String> introduceList*/) {
        this.id = introduce.getId();
        this.recruitId=introduce.getRecruit().toModel().getId();
        this.memberId=introduce.getMemberId();
        this.recruitTitle=introduce.getRecruit().toModel().getTitle();
        this.questionList = questionList;
        this.deadline=formatUpdatedAt(introduce.getRecruit().toModel().getEndTime());
        this.tags=introduce.getRecruit().toModel().getTags();
        this.link=introduce.getRecruit().toModel().getLink();
        this.updatedAt = formatUpdatedAt(introduce.getUpdatedAt());
        this.timeSinceUpdate = calculateTimeUntilDeadline(introduce.getUpdatedAt(), introduce.getRecruit().toModel().getEndTime());
        /*this.introduceList = introduceList;*/
        this.state=introduce.getState();
    }

    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }

    private String calculateTimeUntilDeadline(LocalDateTime updatedAt, LocalDateTime deadline) {
        Duration duration = Duration.between(updatedAt, deadline);
        long days = duration.toDays() + 1;
        return "D-" + days;
    }
}
