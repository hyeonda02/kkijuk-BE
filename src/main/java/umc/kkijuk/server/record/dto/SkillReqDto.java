package umc.kkijuk.server.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.record.domain.Workmanship;

@Builder
@AllArgsConstructor
@Getter
public class SkillReqDto {
    private String skillName;
    private Workmanship workmanship;
}
