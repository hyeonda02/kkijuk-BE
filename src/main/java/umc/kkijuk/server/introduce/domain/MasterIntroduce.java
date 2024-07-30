package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;

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

    @Size(max = 24)
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;

/*    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;*/

    @Builder
    public MasterIntroduce(String oneLiner, String introduction, String motive, String prosAndCons) {
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
