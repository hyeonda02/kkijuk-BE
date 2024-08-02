package umc.kkijuk.server.careerdetail.service;

import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;

public interface CareerDetailService {
    CareerDetail create(CareerDetailRequestDto.CareerDetailCreate request, Long careerId);
}
