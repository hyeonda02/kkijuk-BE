package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;
import java.util.List;

@Entity
@Table(name="introduce")
@Getter
@NoArgsConstructor
public class Introduce extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "recruit_id", nullable = false)
    @NotNull
    private RecruitEntity recruit;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull
    private Member member;

    @NotNull
    @OneToMany(mappedBy = "introduce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @NotNull
    private int state;

    @Builder
    public Introduce(RecruitEntity recruit, List<Question> questions, int state) {
        this.recruit = recruit;
        this.questions = questions;
        this.state = state;
        setQuestions(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        for (Question question : questions) {
            question.setIntroduce(this);
        }
    }

    public void update(int state) {
        this.state=state;
    }

}
