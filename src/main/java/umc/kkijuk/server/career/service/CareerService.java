package umc.kkijuk.server.career.service;

import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;

public interface CareerService {
    Career createCareer(CareerRequestDto.CareerDto request);
    void deleteCareer(Long careerId);
}
