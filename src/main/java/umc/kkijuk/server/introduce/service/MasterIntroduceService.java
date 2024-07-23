package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
import umc.kkijuk.server.introduce.error.BaseException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MasterIntroduceService{
    private final MasterIntroduceRepository MasterIntroduceRepository;
    private final MasterIntroduceRepository masterIntroduceRepository;

    @Transactional
    public MasterIntroduceResDto saveMasterIntro(MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        if( MasterIntroduceRepository.findAll().stream().count()>0 ){
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 마스터 자기소개가 존재합니다");
        }

        MasterIntroduce masterIntroduce=MasterIntroduce.builder()
                .oneLiner(masterIntroduceReqDto.getOneLiner())
                .content(masterIntroduceReqDto.getContent())
                .subTitle(masterIntroduceReqDto.getSubTitle())
                .build();

        MasterIntroduceRepository.save(masterIntroduce);

        return new MasterIntroduceResDto(masterIntroduce);
    }

    @Transactional
    public List<MasterIntroduce> getMasterIntro(){
        return MasterIntroduceRepository.findAll();
    }

    @Transactional
    public MasterIntroduceResDto updateMasterIntro(Long id, MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        MasterIntroduce masterIntroduce=masterIntroduceRepository.findById(id)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "아이디를 다시 확인해주세요"));

        masterIntroduce.update(masterIntroduceReqDto.getOneLiner(),
                masterIntroduceReqDto.getSubTitle(),
                masterIntroduceReqDto.getContent());

        return new MasterIntroduceResDto(masterIntroduce);
    }
}
