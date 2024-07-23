package umc.kkijuk.server.introduce.dto;

import lombok.*;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;

import java.time.LocalDateTime;

@Getter
@Setter
public class MasterIntroduceResDto {
    private String oneLiner;
    private String subTitle;
    private String content;
    private String updatedAt;

    @Builder
    public MasterIntroduceResDto(MasterIntroduce masterIntroduce) {
        this.oneLiner = masterIntroduce.getOneLiner();
        this.subTitle = masterIntroduce.getSubTitle();
        this.content = masterIntroduce.getContent();
        this.updatedAt = masterIntroduce.getUpdated_at();
    }

}
