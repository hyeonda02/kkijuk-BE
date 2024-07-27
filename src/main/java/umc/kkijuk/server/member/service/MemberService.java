package umc.kkijuk.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.dto.MemberFieldDto;
import umc.kkijuk.server.member.dto.MemberInfoChangeDto;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Member findOne(Long memberId) {
        return memberJpaRepository.findById(memberId).get();
    }


    //아직 인터페이스 구현 x, 추후에 구현 후 MemberService -> MemberServiceImpl로 수정 예정.
    @Transactional
    public Long join(Member member) {
        memberJpaRepository.save(member);
        return member.getId();
    }

    public Member getMemberInfo(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    public List<String> getMemberField(Long memberId){
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return member.getField();
    }

    @Transactional
    public List<String> updateMemberField(Long id,MemberFieldDto memberFieldDto){
        Member member = memberJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.changeFieldInfo(memberFieldDto.getField());
        return member.getField();
    }

    @Transactional
    public Long updateMemberInfo(Long id,MemberInfoChangeDto memberInfoChangeDto){
        Member member = memberJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.changeMemberInfo(memberInfoChangeDto.getPhoneNumber(), memberInfoChangeDto.getBirthDate(), memberInfoChangeDto.getMarketingAgree());
        return member.getId();
    }


}
