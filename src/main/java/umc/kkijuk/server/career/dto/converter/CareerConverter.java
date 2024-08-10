package umc.kkijuk.server.career.dto.converter;

import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CareerConverter {
    public static Career toCareer(CareerRequestDto.CreateCareerDto request, Long memberId){
        return Career.builder()
                .memberId(memberId)
                .name(request.getCareerName())
                .alias(request.getAlias())
                .summary(request.getSummary())
                .startdate(request.getStartDate())
                .enddate(request.getEndDate())
                .unknown(request.getIsUnknown())
                .build();
    }
    public static CareerResponseDto.CareerResultDto toCareerResultDto(Career career){
        return CareerResponseDto.CareerResultDto.builder()
                .careerId(career.getId())
                .build();
    }

    public static CareerResponseDto.CareerSearchDtoList toCareerSearchDtoList(List<Career> searchCareers) {
        return CareerResponseDto.CareerSearchDtoList.builder()
                .careers(searchCareers.stream().map(entry ->
                                CareerResponseDto.CareerSearchDto.builder()
                                        .id(entry.getId())
                                        .careerName(entry.getName())
                                        .alias(entry.getAlias())
                                        .summary(entry.getSummary())
                                        .startDate(entry.getStartdate())
                                        .endDate(entry.getEnddate())
                                        .details(entry.getCareerDetailList().stream().map(detail ->
                                                        CareerDetailResponseDto.CareerDetailResult.builder()
                                                                .id(detail.getId())
                                                                .careerId(detail.getCareer().getId())
                                                                .title(detail.getTitle())
                                                                .content(detail.getContent())
                                                                .startDate(detail.getStartDate())
                                                                .endDate(detail.getEndDate())
                                                                .careerTagList(detail.getCareerTagList().stream().map(tag ->
                                                                                CareerDetailResponseDto.CareerTag.builder()
                                                                                        .id(tag.getTag().getId())
                                                                                        .tagName(tag.getTag().getName())
                                                                                        .build()).collect(Collectors.toList()))
                                                                .build()).collect(Collectors.toList()))
                                        .build()).collect(Collectors.toList())).build();
    }



    public static CareerResponseDto.CareerDto toCareerDto(Career career) {
        return CareerResponseDto.CareerDto.builder()
                .id(career.getId())
                .careerName(career.getName())
                .alias(career.getAlias())
                .summary(career.getSummary())
                .isUnknown(career.getUnknown())
                .startDate(career.getStartdate())
                .endDate(career.getEnddate())
                .year(career.getYear())
                .categoryId(Math.toIntExact(career.getCategory().getId()))
                .categoryName(career.getCategory().getName())
                .build();
    }


    public static List<CareerResponseDto.CareerGroupedByCategoryDto> toCareerGroupedByCategoryDto( Map<String, List<Career>> groupedCareers ) {
        return groupedCareers.entrySet().stream()
                .map(entry -> CareerResponseDto.CareerGroupedByCategoryDto.builder()
                        .categoryName(entry.getKey())
                        .count(entry.getValue().size())
                        .careers(entry.getValue().stream()
                                .map(career -> CareerResponseDto.CareerDto.builder()
                                        .id(career.getId())
                                        .careerName(career.getName())
                                        .alias(career.getAlias())
                                        .summary(career.getSummary())
                                        .year(career.getYear())
                                        .startDate(career.getStartdate())
                                        .endDate(career.getEnddate())
                                        .isUnknown(career.getUnknown())
                                        .categoryId(Math.toIntExact(career.getCategory().getId()))
                                        .categoryName(career.getCategory().getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public static List<CareerResponseDto.CareerGroupedByYearDto> toCareerGroupedByYearDto(Map<String, List<Career>> groupedCareers) {
        return groupedCareers.entrySet().stream()
                .map(entry -> CareerResponseDto.CareerGroupedByYearDto.builder()
                        .year(Integer.parseInt(entry.getKey()))
                        .count(entry.getValue().size())
                        .careers(entry.getValue().stream()
                                .map(career -> CareerResponseDto.CareerDto.builder()
                                        .id(career.getId())
                                        .careerName(career.getName())
                                        .alias(career.getAlias())
                                        .summary(career.getSummary())
                                        .year(career.getYear())
                                        .startDate(career.getStartdate())
                                        .endDate(career.getEnddate())
                                        .isUnknown(career.getUnknown())
                                        .categoryId(Math.toIntExact(career.getCategory().getId()))
                                        .categoryName(career.getCategory().getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
    public static CareerResponseDto.CareerDetailDto toCareerDetailDto(Career career){
        return CareerResponseDto.CareerDetailDto.builder()
                .id(career.getId())
                .memberId(career.getMemberId())
                .careerName(career.getName())
                .alias(career.getAlias())
                .summary(career.getSummary())
                .isUnknown(career.getUnknown())
                .startDate(career.getStartdate())
                .endDate(career.getEnddate())
                .year(career.getYear())
                .categoryId(Math.toIntExact(career.getCategory().getId()))
                .categoryName(career.getCategory().getName())
                .totalDetailCount(career.getCareerDetailList().size())
                .details(career.getCareerDetailList().stream().map(careerDetail -> CareerDetailResponseDto.CareerDetailResult.builder()
                                .id(careerDetail.getId())
                                .careerId(careerDetail.getCareer().getId())
                                .title(careerDetail.getTitle())
                                .content(careerDetail.getContent())
                                .startDate(careerDetail.getStartDate())
                                .endDate(careerDetail.getEndDate())
                                .careerTagList(careerDetail.getCareerTagList().stream().map(careerTag -> CareerDetailResponseDto.CareerTag.builder()
                                                    .id(careerTag.getTag().getId())
                                                    .tagName(careerTag.getTag().getName())
                                                    .build()).collect(Collectors.toList())
                                )
                                .build()).collect(Collectors.toList()))
                .build();

    }


}
