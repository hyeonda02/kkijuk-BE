package umc.kkijuk.server.introduce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Question;

@Getter
@Setter
@AllArgsConstructor
public class QuestionDto {
    private String title;
    private String content;

    @Builder
    public QuestionDto(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
    }
}
