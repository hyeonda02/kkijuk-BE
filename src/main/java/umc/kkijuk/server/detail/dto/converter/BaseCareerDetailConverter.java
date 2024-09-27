package umc.kkijuk.server.detail.dto.converter;

import umc.kkijuk.server.career.domain.BaseCareer;
import umc.kkijuk.server.detail.domain.BaseCareerDetail;
import umc.kkijuk.server.detail.dto.BaseCareerDetailReqDto;
import umc.kkijuk.server.member.domain.Member;

import java.util.ArrayList;

public class BaseCareerDetailConverter {
    public static BaseCareerDetail toBaseCareerDetail(Member requestMember, BaseCareerDetailReqDto request, BaseCareer baseCareer) {
        return BaseCareerDetail.builder()
                .baseCareer(baseCareer)
                .careerType(request.getCareerType())
                .memberId(requestMember.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .careerTagList(new ArrayList<>())
                .build();
    }

}
