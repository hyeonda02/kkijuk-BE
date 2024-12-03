package umc.kkijuk.server.introduce.service;

import umc.kkijuk.server.introduce.controller.response.MasterIntroduceResponse;
import umc.kkijuk.server.introduce.dto.IntroduceReqDto;

public interface MasterIntroduceService {

    MasterIntroduceResponse saveMasterIntro(Long memberId, IntroduceReqDto introduceReqDto);
    MasterIntroduceResponse getMasterIntro(Long memberId);
    MasterIntroduceResponse updateMasterIntro(Long memberId, IntroduceReqDto introduceReqDto) throws Exception;
}
