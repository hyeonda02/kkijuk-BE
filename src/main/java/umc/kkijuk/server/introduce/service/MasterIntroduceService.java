package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.IntroOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.introduce.domain.Introduce;
import umc.kkijuk.server.introduce.domain.IntroduceRepository;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;

import java.util.ArrayList;
import java.util.Collections;
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
    public MasterIntroduceResDto saveMasterIntro(Member requestMember, MasterIntroduceReqDto masterIntroduceReqDto){
        if(masterIntroduceRepository.findAll().stream().count()>0 ){
            throw new IntroFoundException("이미 마스터 자기소개가 존재합니다");
        }

        MasterIntroduce masterIntroduce=MasterIntroduce.builder()
                .member(requestMember)
                .oneLiner(masterIntroduceReqDto.getOneLiner())
                .motive(masterIntroduceReqDto.getMotive())
                .introduction(masterIntroduceReqDto.getIntroduction())
                .prosAndCons(masterIntroduceReqDto.getProsAndCons())
                .jobSuitability(masterIntroduceReqDto.getJobSuitability())
                .build();


        masterIntroduceRepository.save(masterIntroduce);

        return new MasterIntroduceResDto(masterIntroduce/*, null*/);
    }

    @Transactional
    public List<MasterIntroduceResDto> getMasterIntro(Member requestMember){
        List<MasterIntroduce> masterIntroduces =masterIntroduceRepository.findAll();

/*
        List<String> introduceList=getIntroduceTitles();
*/

        return masterIntroduces.stream()
                .map(masterIntroduce -> new MasterIntroduceResDto(masterIntroduce/*, introduceList*/))
                .collect(Collectors.toList());
    }

    @Transactional
    public MasterIntroduceResDto updateMasterIntro(Member requestMember, Long id, MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        MasterIntroduce masterIntroduce = masterIntroduceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("masterIntroduce ", id));
        if (!masterIntroduce.getMember().getId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        masterIntroduce.update(
                masterIntroduceReqDto.getOneLiner(),
                masterIntroduceReqDto.getIntroduction(),
                masterIntroduceReqDto.getMotive(),
                masterIntroduceReqDto.getProsAndCons(),
                masterIntroduceReqDto.getJobSuitability());
/*
        List<String> introduceList=getIntroduceTitles();
*/
        return new MasterIntroduceResDto(masterIntroduce/*, introduceList*/);
    }

/*    @Transactional
    public List<String> getIntroduceTitles() {
        List<Introduce> introduces = introduceRepository.findAll();

        return introduces.stream()
                .map(introduce -> recruitJpaRepository.findById(introduce.getRecruit().toModel().getId()))
                .filter(Optional::isPresent)
                .map(opt -> opt.get().toModel().getTitle())
                .collect(Collectors.toList());
    }*/
}
