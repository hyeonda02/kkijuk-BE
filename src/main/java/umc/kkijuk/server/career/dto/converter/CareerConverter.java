package umc.kkijuk.server.career.dto.converter;

import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.dto.CareerDetailResponseDto;
import umc.kkijuk.server.careerdetail.dto.converter.CareerDetailConverter;

import java.util.*;
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
                                .map(CareerConverter::toCareerDto)
                                .collect(Collectors.toList())).build()).collect(Collectors.toList());
    }

    public static List<CareerResponseDto.CareerGroupedByYearDto> toCareerGroupedByYearDto(Map<String, List<Career>> groupedCareers) {
        return groupedCareers.entrySet().stream()
                .map(entry -> CareerResponseDto.CareerGroupedByYearDto.builder()
                        .year(Integer.parseInt(entry.getKey()))
                        .count(entry.getValue().size())
                        .careers(entry.getValue().stream()
                                .map(CareerConverter::toCareerDto)
                                .collect(Collectors.toList())).build()).collect(Collectors.toList());
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
                .details(career.getCareerDetailList().stream()
                        .map(CareerDetailConverter::toCareerDetailResult)
                        .collect(Collectors.toList())).build();

    }
    private static CareerResponseDto.CareerSearchDto createCareerSearchDto(Career career, List<CareerDetailResponseDto.CareerDetailResult> details) {
        return CareerResponseDto.CareerSearchDto.builder()
                .id(career.getId())
                .careerName(career.getName())
                .alias(career.getAlias())
                .summary(career.getSummary())
                .startDate(career.getStartdate())
                .endDate(career.getEnddate())
                .details(details)
                .categoryName(career.getCategory().getName())
                .build();
    }

    private static CareerResponseDto.CareerNameSearchDto createCareerNameSearchDto(Career career) {
        Optional<CareerDetail> latestCareerDetail = career.getCareerDetailList().stream()
                .max(Comparator.comparing(CareerDetail::getStartDate));

        return CareerResponseDto.CareerNameSearchDto.builder()
                .id(career.getId())
                .categoryName(career.getCategory().getName())
                .careerName(career.getName())
                .alias(career.getAlias())
                .startDate(career.getStartdate())
                .endDate(career.getEnddate())
                .careerDetail(latestCareerDetail.map(CareerDetailConverter::toCareerDetailResult).orElse(null))
                .summary(latestCareerDetail.isPresent() ? null : career.getSummary())
                .build();
    }

    public static List<CareerResponseDto.CareerNameSearchDto> toCareerNameSearchDto(List<Career> careers) {
        return careers.stream()
                .map(CareerConverter::createCareerNameSearchDto)
                .collect(Collectors.toList());
    }

    public static List<CareerResponseDto.CareerSearchDto> toCareerSearchDto(List<CareerDetail> details, List<Career> careers) {
        Map<Long, List<CareerDetail>> groupedByCareer = details.stream().collect(Collectors.groupingBy(detail -> detail.getCareer().getId()));

        List<CareerResponseDto.CareerSearchDto> resultWithDetails = groupedByCareer.entrySet().stream()
                .map(entry ->{
                    Career firstCareer = entry.getValue().get(0).getCareer();
                    List<CareerDetailResponseDto.CareerDetailResult> careerDetails = entry.getValue().stream()
                            .map(CareerDetailConverter::toCareerDetailResult)
                            .collect(Collectors.toList());
                    return createCareerSearchDto(firstCareer, careerDetails);
                }).collect(Collectors.toList());
        if(careers!=null) {
            resultWithDetails.addAll(mapCareersWithoutDetails(careers));
        }
        return resultWithDetails;
    }

    private static List<CareerResponseDto.CareerSearchDto> mapCareersWithoutDetails(List<Career> careers) {
        return careers.stream()
                .map(career -> createCareerSearchDto(career, null))
                .collect(Collectors.toList());
    }


}
