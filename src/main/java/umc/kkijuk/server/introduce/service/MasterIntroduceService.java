package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
        if( masterIntroduceRepository.findAll().stream().count()>0 ){
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 마스터 자기소개가 존재합니다");
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
        /*return masterIntroduces.stream()
                .map(masterIntroduce -> MasterIntroduceResDto.builder()
                        .id(masterIntroduce.getId())
                        .oneLiner(masterIntroduce.getOneLiner())
                        .introduction(masterIntroduce.getIntroduction())
                        .motive(masterIntroduce.getMotive())
                        .prosAndCons(masterIntroduce.getProsAndCons())
                        .updatedAt(masterIntroduce.getUpdatedAt().toString())
                        .introduceList(introduceList)
                        .build())
                .collect(Collectors.toList());*/

        return masterIntroduces.stream()
                .map(masterIntroduce -> new MasterIntroduceResDto(masterIntroduce, introduceList))
                .collect(Collectors.toList());
    }

    @Transactional
    public MasterIntroduceResDto updateMasterIntro(Long id, MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        MasterIntroduce masterIntroduce=masterIntroduceRepository.findById(id)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "아이디를 다시 확인해주세요"));

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
        // Fetch all Introduce entities
        List<Introduce> introduces = introduceRepository.findAll();

        // Map Introduce entities to Recruit titles
        return introduces.stream()
                .map(introduce -> recruitJpaRepository.findById(introduce.getRecruit().toModel().getId())) // Get the Recruit entity
                .filter(Optional::isPresent) // Filter out any empty results
                .map(opt -> opt.get().toModel().getTitle()) // Get the title of the Recruit
                .collect(Collectors.toList()); // Collect titles into a List
    }
}
