package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.common.domian.base.BaseEntity;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_introduce")
@Getter
@NoArgsConstructor
public class MasterIntroduce extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @NotNull
    @OneToMany(mappedBy = "masterIntroduce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MasterQuestion> masterQuestion;

    @NotNull
    private int state;

    @Builder
    public MasterIntroduce(Long memberId, List<MasterQuestion> masterQuestion, int state) {
        this.memberId = memberId;
        this.masterQuestion = masterQuestion;
        this.state = state;
        setMasterQuestions(masterQuestion);
    }

    public void setMasterQuestions(List<MasterQuestion> masterQuestions) {
        this.masterQuestion = masterQuestions;
        for (MasterQuestion masterQuestion : masterQuestions) {
            masterQuestion.setMasterIntroduce(this);
        }
    }

    public void update(int state) {
        this.state=state;
    }


}
