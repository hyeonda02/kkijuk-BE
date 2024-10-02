package umc.kkijuk.server.detail.service;

import umc.kkijuk.server.detail.controller.response.BaseCareerDetailResponse;
import umc.kkijuk.server.detail.dto.BaseCareerDetailReqDto;
import umc.kkijuk.server.member.domain.Member;

public interface BaseCareerDetailService {
    BaseCareerDetailResponse createDetail(Member requestMember, BaseCareerDetailReqDto request, Long careerId);

    void deleteDetail(Member requestMember, Long careerId, Long detailId);

    BaseCareerDetailResponse updateDetail(Member requestMember, BaseCareerDetailReqDto request, Long careerId, Long detailId);
}