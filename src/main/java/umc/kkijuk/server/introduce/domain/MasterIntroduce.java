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

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;

    @Size(max = 24)
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;

    @Builder
    public MasterIntroduce(Member member, String oneLiner, String introduction, String motive, String prosAndCons) {
        this.member = member;
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.motive = motive;
        this.prosAndCons = prosAndCons;
    }

    public void update(String oneLiner, String introduction, String motive, String prosAndCons) {
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.motive = motive;
        this.prosAndCons = prosAndCons;
    }

}
