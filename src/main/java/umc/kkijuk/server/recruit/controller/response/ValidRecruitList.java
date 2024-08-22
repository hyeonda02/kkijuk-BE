package umc.kkijuk.server.recruit.controller.response;

import lombok.Builder;
import lombok.Getter;
import umc.kkijuk.server.recruit.domain.ValidRecruitDto;

import java.util.List;

@Getter
public class ValidRecruitList {
    private int count;
    private final List<ValidRecruitInfo> recruits;

    public ValidRecruitList(List<ValidRecruitInfo> recruits) {
        this.recruits = recruits;
    }

    public void addItem(ValidRecruitDto dto) {
        recruits.add(ValidRecruitInfo.from(dto));
        count = recruits.size();
    }
}
