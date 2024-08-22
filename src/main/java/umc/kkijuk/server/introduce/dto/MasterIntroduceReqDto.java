package umc.kkijuk.server.introduce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MasterIntroduceReqDto {
    private String oneLiner;
    private String motiveTitle;
    private String motive;
    private String prosAndConsTitle;
    private String prosAndCons;
    private String jobSuitabilityTitle;
    private String jobSuitability;
}
