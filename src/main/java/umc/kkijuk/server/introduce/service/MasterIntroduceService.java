package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.IntroOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.introduce.domain.MasterIntroduce;
import umc.kkijuk.server.introduce.domain.MasterIntroduceRepository;
import umc.kkijuk.server.introduce.dto.MasterIntroduceReqDto;
import umc.kkijuk.server.introduce.dto.MasterIntroduceResDto;
import umc.kkijuk.server.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MasterIntroduceService{
    private final MasterIntroduceRepository masterIntroduceRepository;

    @Transactional
    public MasterIntroduceResDto saveMasterIntro(Member requestMember, MasterIntroduceReqDto masterIntroduceReqDto){
        if (masterIntroduceRepository.existsByMemberId(requestMember.getId())) {
            throw new IntroFoundException("이미 마스터 자기소개서가 존재합니다");
        }

        MasterIntroduce masterIntroduce=MasterIntroduce.builder()
                .memberId(requestMember.getId())
                .oneLiner(masterIntroduceReqDto.getOneLiner())
                .motive(masterIntroduceReqDto.getMotive())
                .motiveTitle(masterIntroduceReqDto.getMotiveTitle())
                .prosAndConsTitle(masterIntroduceReqDto.getProsAndConsTitle())
                .prosAndCons(masterIntroduceReqDto.getProsAndCons())
                .jobSuitabilityTitle(masterIntroduceReqDto.getJobSuitabilityTitle())
                .jobSuitability(masterIntroduceReqDto.getJobSuitability())
                .build();

        masterIntroduceRepository.save(masterIntroduce);

        return new MasterIntroduceResDto(masterIntroduce);
    }

    @Transactional
    public MasterIntroduceResDto getMasterIntro(Member requestMember){
        MasterIntroduce masterIntroduce = masterIntroduceRepository.findByMemberId(requestMember.getId());
        if (masterIntroduce == null) {
            throw new ResourceNotFoundException("memberRepository", requestMember.getId());
        }
        if (!masterIntroduce.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        return new MasterIntroduceResDto(masterIntroduce);
    }

    @Transactional
    public MasterIntroduceResDto updateMasterIntro(Member requestMember, Long id, MasterIntroduceReqDto masterIntroduceReqDto) throws Exception{
        MasterIntroduce masterIntroduce = masterIntroduceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("masterIntroduce ", id));
        if (!masterIntroduce.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }
        masterIntroduce.update(
                masterIntroduceReqDto.getOneLiner(),
                masterIntroduceReqDto.getMotiveTitle(),
                masterIntroduceReqDto.getMotive(),
                masterIntroduceReqDto.getProsAndConsTitle(),
                masterIntroduceReqDto.getProsAndCons(),
                masterIntroduceReqDto.getJobSuitabilityTitle(),
                masterIntroduceReqDto.getJobSuitability());

        return new MasterIntroduceResDto(masterIntroduce/*, introduceList*/);
    }
}
