package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "master_introduce")
@Getter
@NoArgsConstructor
public class MasterIntroduce extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;*/

    @Column(nullable = false)
    private Long memberId;

    @Size(max = 24)
    private String oneLiner;
    private String motiveTitle;
    private String motive;
    private String prosAndConsTitle;
    private String prosAndCons;
    private String jobSuitabilityTitle;
    private String jobSuitability;


    @Builder
    public MasterIntroduce(Long memberId, String oneLiner, String motiveTitle, String motive, String prosAndConsTitle, String prosAndCons
    , String jobSuitabilityTitle, String jobSuitability) {
        this.memberId = memberId;
        this.oneLiner = oneLiner;
        this.motiveTitle = motiveTitle;
        this.motive = motive;
        this.prosAndConsTitle=prosAndConsTitle;
        this.prosAndCons = prosAndCons;
        this.jobSuitabilityTitle = jobSuitabilityTitle;
        this.jobSuitability = jobSuitability;
    }

    public void update( String oneLiner, String motiveTitle, String motive, String prosAndConsTitle, String prosAndCons
            , String jobSuitabilityTitle, String jobSuitability) {
        this.oneLiner = oneLiner;
        this.motiveTitle = motiveTitle;
        this.motive = motive;
        this.prosAndConsTitle=prosAndConsTitle;
        this.prosAndCons = prosAndCons;
        this.jobSuitabilityTitle = jobSuitabilityTitle;
        this.jobSuitability = jobSuitability;
    }

}
