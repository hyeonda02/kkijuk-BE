package umc.kkijuk.server.careerdetail.service;

import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.member.domain.Member;

public interface CareerDetailService {
    CareerDetail create(Member member, CareerDetailRequestDto.CareerDetailCreate request, Long careerId);
    void delete(Member member, Long careerDetailId);
    CareerDetail update(Member member,CareerDetailRequestDto.CareerDetailUpdate request, Long careerId, Long detailId);

}
