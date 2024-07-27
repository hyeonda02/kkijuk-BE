package umc.kkijuk.server.introduce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Introduce;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class IntroduceResDto {
    private Long id;
    private Long recruitId;
    private String recruitTitle;
    private List<QuestionDto> questionList;
    private LocalDateTime deadline;
    private List<String> tags;
    private String link;
    private String updatedAt;
    private int state;

    @Builder
    public IntroduceResDto(Introduce introduce, List<QuestionDto> questionList) {
        this.id = introduce.getId();
        this.recruitId=introduce.getRecruit().toModel().getId();
        this.recruitTitle=introduce.getRecruit().toModel().getTitle();
        this.questionList = questionList;
        this.deadline=introduce.getRecruit().toModel().getEndTime();
        this.tags=introduce.getRecruit().toModel().getTags();
        this.link=introduce.getRecruit().toModel().getLink();
        this.updatedAt = introduce.getUpdated_at();
        this.state=introduce.getState();
    }
}
