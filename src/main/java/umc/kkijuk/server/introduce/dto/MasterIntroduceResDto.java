package umc.kkijuk.server.introduce.dto;

import lombok.*;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MasterIntroduceResDto {
    private Long id;
    private Long memberId;
    private String oneLiner;
    private String motiveTitle;
    private String motive;
    private String prosAndConsTitle;
    private String prosAndCons;
    private String jobSuitabilityTitle;
    private String jobSuitability;
    private String updatedAt;

    public MasterIntroduceResDto(MasterIntroduce masterIntroduce) {
        this.id = masterIntroduce.getId();
        this.memberId=masterIntroduce.getMemberId();
        this.oneLiner = masterIntroduce.getOneLiner();
        this.motiveTitle = masterIntroduce.getMotiveTitle();
        this.motive = masterIntroduce.getMotive();
        this.prosAndConsTitle = masterIntroduce.getProsAndConsTitle();
        this.prosAndCons = masterIntroduce.getProsAndCons();
        this.jobSuitabilityTitle = masterIntroduce.getJobSuitabilityTitle();
        this.jobSuitability = masterIntroduce.getJobSuitability();
        this.updatedAt = formatUpdatedAt(masterIntroduce.getUpdatedAt());
    }


    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }


}
