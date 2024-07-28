package umc.kkijuk.server.introduce.dto;

import jakarta.transaction.Transactional;
import lombok.*;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MasterIntroduceResDto {
    private Long id;
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;
    private String updatedAt;
    private List<String> introduceList;

    public MasterIntroduceResDto(MasterIntroduce masterIntroduce, List<String> introduceList) {
        this.id = masterIntroduce.getId();
        this.oneLiner = masterIntroduce.getOneLiner();
        this.introduction = masterIntroduce.getIntroduction();
        this.motive = masterIntroduce.getMotive();
        this.prosAndCons = masterIntroduce.getProsAndCons();
        this.updatedAt = masterIntroduce.getUpdated_at();
        this.introduceList = introduceList;
    }


}
