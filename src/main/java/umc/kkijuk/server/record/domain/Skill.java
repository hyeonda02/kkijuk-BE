package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.common.domian.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @NotNull
    private SkillTag skillTag;

    @NotNull
    @Size(max = 30)
    private String skillName;
    @NotNull
    private Workmanship workmanship;

    public void changeSkillInfo(SkillTag skillTag, String skillName, Workmanship workmanship){
        this.skillTag = skillTag;
        this.skillName = skillName;
        this.workmanship = workmanship;
    }
}
