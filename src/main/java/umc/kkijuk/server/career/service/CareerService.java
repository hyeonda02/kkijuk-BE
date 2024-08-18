package umc.kkijuk.server.career.service;

import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface CareerService {
    Career createCareer(Member member, CareerRequestDto.CreateCareerDto request);
    void deleteCareer(Member member, Long careerId);
    Optional<Career> findCareer(Long value);
    Career updateCareer(Member member, Long careerId, CareerRequestDto.UpdateCareerDto request);
    Career findCareerDetail(Member member, Long careerId);
    List<CareerResponseDto.CareerGroupedByYearDto> getCareerGroupedByYear(Member requestMember);
    List<CareerResponseDto.CareerGroupedByCategoryDto> getCareerGroupedByCategory(Member requestMember);
    List<Career> searchCareer(Member member, CareerRequestDto.SearchCareerDto request);
    List<CareerResponseDto.CareerSearchDto> searchCareerDetail(Member member, CareerRequestDto.SearchCareerDto request);
}
