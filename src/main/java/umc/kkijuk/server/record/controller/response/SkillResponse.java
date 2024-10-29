package umc.kkijuk.server.record.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import umc.kkijuk.server.record.domain.Skill;
import umc.kkijuk.server.record.domain.SkillTag;
import umc.kkijuk.server.record.domain.Workmanship;

@Data
@AllArgsConstructor
@Getter
@Builder
public class SkillResponse {
    private Long skillId;
    private SkillTag skillTag;
    private String skillName;
    private Workmanship workmanship;

    public SkillResponse(Skill skill) {
        this.skillId = skill.getId();
        this.skillTag = skill.getSkillTag();
        this.skillName = skill.getSkillName();
        this.workmanship = skill.getWorkmanship();
    }
}
