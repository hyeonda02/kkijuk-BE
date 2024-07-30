package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.MasterFoundException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.introduce.dto.IntroduceListResDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
import umc.kkijuk.server.introduce.error.BaseException;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MasterIntroduceService{
    private final MasterIntroduceRepository masterIntroduceRepository;
    private final IntroduceRepository introduceRepository;
    private final RecruitJpaRepository recruitJpaRepository;

    @Transactional
    public MasterIntroduceResDto saveMasterIntro(MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        if(masterIntroduceRepository.findAll().stream().count()>0 ){
            throw new MasterFoundException("이미 마스터 자기소개가 존재합니다");
        }

        MasterIntroduce masterIntroduce=MasterIntroduce.builder()
                .oneLiner(masterIntroduceReqDto.getOneLiner())
                .motive(masterIntroduceReqDto.getMotive())
                .introduction(masterIntroduceReqDto.getIntroduction())
                .prosAndCons(masterIntroduceReqDto.getProsAndCons())
                .build();

        masterIntroduceRepository.save(masterIntroduce);

        return new MasterIntroduceResDto(masterIntroduce, null);
    }

    @Transactional
    public List<MasterIntroduceResDto> getMasterIntro(){
        List<MasterIntroduce> masterIntroduces= masterIntroduceRepository.findAll();
        List<String> introduceList=getIntroduceTitles();

        return masterIntroduces.stream()
                .map(masterIntroduce -> new MasterIntroduceResDto(masterIntroduce, introduceList))
                .collect(Collectors.toList());
    }

    @Transactional
    public MasterIntroduceResDto updateMasterIntro(Long id, MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        MasterIntroduce masterIntroduce = masterIntroduceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("masterIntroduce ", id));

        masterIntroduce.update(
                masterIntroduceReqDto.getOneLiner(),
                masterIntroduceReqDto.getIntroduction(),
                masterIntroduceReqDto.getMotive(),
                masterIntroduceReqDto.getProsAndCons());
        List<String> introduceList=getIntroduceTitles();
        return new MasterIntroduceResDto(masterIntroduce, introduceList);
    }

    @Transactional
    public List<String> getIntroduceTitles() {
        List<Introduce> introduces = introduceRepository.findAll();

        return introduces.stream()
                .map(introduce -> recruitJpaRepository.findById(introduce.getRecruit().toModel().getId()))
                .filter(Optional::isPresent)
                .map(opt -> opt.get().toModel().getTitle())
                .collect(Collectors.toList());
    }
}
