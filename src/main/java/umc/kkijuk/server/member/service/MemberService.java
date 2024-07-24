package umc.kkijuk.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.repository.MemberJpaRepository;
import umc.kkijuk.server.recruit.domain.Recruit;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;

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

}
