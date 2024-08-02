package umc.kkijuk.server.introduce.dto;

import jakarta.transaction.Transactional;
import lombok.*;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MasterIntroduceResDto {
    private Long id;
    private Long memberId;
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;
    private String jobSuitability;
    private String updatedAt;
/*
    private List<String> introduceList;
*/

    public MasterIntroduceResDto(MasterIntroduce masterIntroduce/*, List<String> introduceList*/) {
        this.id = masterIntroduce.getId();
        this.memberId=masterIntroduce.getMember().getId();
        this.oneLiner = masterIntroduce.getOneLiner();
        this.introduction = masterIntroduce.getIntroduction();
        this.motive = masterIntroduce.getMotive();
        this.prosAndCons = masterIntroduce.getProsAndCons();
        this.jobSuitability = masterIntroduce.getJobSuitability();
        this.updatedAt = formatUpdatedAt(masterIntroduce.getUpdatedAt());
/*
        this.introduceList = introduceList;
*/
    }


    private String formatUpdatedAt(LocalDateTime updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updatedAt != null ? updatedAt.format(formatter) : null;
    }


}
