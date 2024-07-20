package umc.kkijuk.server.career.dto.converter;

import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;

public class CareerConverter {

    public static Career toCareer(CareerRequestDto.CareerDto request){
        return Career.builder()
                .name(request.getCareerName())
                .alias(request.getAlias())
                .startdate(request.getStartDate())
                .current(request.getIsCurrent())
                .enddate(request.getEndDate())
                .build();
    }
    public static CareerResponseDto.CareerResultDto tocareerResultDto(Career career){
        return CareerResponseDto.CareerResultDto.builder()
                .careerId(career.getId())
                .build();
    }
}
