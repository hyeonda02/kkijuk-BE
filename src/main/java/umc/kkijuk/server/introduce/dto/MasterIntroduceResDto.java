package umc.kkijuk.server.introduce.dto;

import lombok.*;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;

import java.time.LocalDateTime;

@Getter
@Setter
public class MasterIntroduceResDto {
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;
    private String updatedAt;

    @Builder
    public MasterIntroduceResDto(MasterIntroduce masterIntroduce) {
        this.oneLiner = masterIntroduce.getOneLiner();
        this.introduction = masterIntroduce.getIntroduction();
        this.motive = masterIntroduce.getMotive();
        this.prosAndCons = masterIntroduce.getProsAndCons();
        this.updatedAt = masterIntroduce.getUpdated_at();
    }

}
