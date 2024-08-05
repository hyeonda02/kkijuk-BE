package umc.kkijuk.server.careerdetail.dto.converter;

import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CareerDetailConverter {
    public static CareerDetail toCareerDetail(Long requestMemberId ,CareerDetailRequestDto.CareerDetailCreate request) {
        return CareerDetail.builder()
                .memberId(requestMemberId)
                .title(request.getTitle())
                .content(request.getContent())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .careerTagList(new ArrayList<>())
                .build();
    }

    public static CareerDetailResponseDto.CareerDetailResult toCareerDetailResult(CareerDetail careerDetail) {
        return CareerDetailResponseDto.CareerDetailResult.builder()
                .id(careerDetail.getId())
                .careerId(careerDetail.getCareer().getId())
                .title(careerDetail.getTitle())
                .content(careerDetail.getContent())
                .startDate(careerDetail.getStartDate())
                .endDate(careerDetail.getEndDate())
                .careerTagList(careerDetail.getCareerTagList().stream()
                        .map(careerTag -> CareerDetailResponseDto.CareerTag.builder()
                                .id(careerTag.getTag().getId())
                                .tagName(careerTag.getTag().getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
