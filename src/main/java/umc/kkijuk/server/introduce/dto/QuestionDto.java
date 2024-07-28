package umc.kkijuk.server.introduce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.kkijuk.server.introduce.domain.Question;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionDto {
    private String title;
    private String content;
    private int number;

    public QuestionDto(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
        this.number = question.getNumber();
    }
}
