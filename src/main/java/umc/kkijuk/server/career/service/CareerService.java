package umc.kkijuk.server.career.service;

import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface CareerService {
    Career createCareer(Member member, CareerRequestDto.CreateCareerDto request);
    void deleteCareer(Member member, Long careerId);
    Optional<Career> findCareer(Long value);
    Career updateCareer(Member member, Long careerId, CareerRequestDto.UpdateCareerDto request);
    Career findCareerDetail(Member requestMember, Long careerId);
    List<? extends CareerGroupedByResponse> getCareerGroupedBy(Member member, String value);
}
