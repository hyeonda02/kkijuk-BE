package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.record.domain.SkillTag;
import umc.kkijuk.server.record.domain.Workmanship;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SkillReqDto {
    private SkillTag skillTag;
    private String skillName;
    private Workmanship workmanship;
}
