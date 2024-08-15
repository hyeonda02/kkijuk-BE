package umc.kkijuk.server.member.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.common.domian.exception.*;
import umc.kkijuk.server.member.controller.response.EmailAuthResponse;
import umc.kkijuk.server.member.controller.response.MemberEmailResponse;
import umc.kkijuk.server.member.controller.response.MemberInfoResponse;
import umc.kkijuk.server.member.controller.response.MemberStateResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.dto.*;
import umc.kkijuk.server.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", memberId));
    }

    @Override
    @Transactional
    public Member join(MemberJoinDto memberJoinDto) {
        String passwordConfirm = memberJoinDto.getPasswordConfirm();
        if (!passwordConfirm.equals(memberJoinDto.getPassword())) {
            throw new ConfirmPasswordMismatchException();
        }

        Member joinMember = memberJoinDto.toEntity();

        String encodedPassword = passwordEncoder.encode(memberJoinDto.getPassword());
        joinMember.changeMemberPassword(encodedPassword);

        Optional<Member> member = memberRepository.findByEmail(memberJoinDto.getEmail());
        if (!member.isEmpty()){
            throw new EmailAlreadyExistsException();
        }

        return memberRepository.save(joinMember);
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = this.getById(memberId);
        if(member.getEmail() == null || member.getName() == null || member.getPhoneNumber() == null || member.getBirthDate() == null){
            throw new InvalidMemberDataException();
        }
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .build();
    }

    @Override
    public List<String> getMemberField(Long memberId){
        Member member = this.getById(memberId);
        return member.getField();
    }

    @Override
    public MemberEmailResponse getMemberEmail(Long memberId) {
        Member member = this.getById(memberId);
        if(member.getEmail() == null){
            throw new InvalidMemberDataException();
        }
        return MemberEmailResponse.builder()
                .email(member.getEmail())
                .build();
    }

    @Override
    @Transactional
    public Member updateMemberField(Long memberId, MemberFieldDto memberFieldDto){
        Member member = this.getById(memberId);

        if(member.getField().equals(memberFieldDto.getField())){
            throw new FieldUpdateException();
        }
        member.changeFieldInfo(memberFieldDto.getField());
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member updateMemberInfo(Long memberId, MemberInfoChangeDto memberInfoChangeDto){
        Member member = this.getById(memberId);
        if(member.getPhoneNumber() == null || member.getBirthDate() == null || member.getMarketingAgree() == null){
            throw new InvalidMemberDataException();
        }
        member.changeMemberInfo(memberInfoChangeDto.getPhoneNumber(), memberInfoChangeDto.getBirthDate(), memberInfoChangeDto.getMarketingAgree());
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member changeMemberPassword(Long memberId, MemberPasswordChangeDto memberPasswordChangeDto){
        Member member = this.getById(memberId);
        if(!memberPasswordChangeDto.getNewPassword().equals(memberPasswordChangeDto.getNewPasswordConfirm())){
            throw new ConfirmPasswordMismatchException();
        }
        if(!passwordEncoder.matches(memberPasswordChangeDto.getCurrentPassword(), member.getPassword())){
            throw new CurrentPasswordMismatchException();
        }

        String encodedPassword = passwordEncoder.encode(memberPasswordChangeDto.getNewPassword());
        member.changeMemberPassword(encodedPassword);

        return memberRepository.save(member);
    }

    @Override
    public Member myPagePasswordAuth(Long memberId, MyPagePasswordAuthDto myPagePasswordAuthDto) {
        Member member = this.getById(memberId);

        if(!passwordEncoder.matches(myPagePasswordAuthDto.getCurrentPassword(), member.getPassword())){
            throw new CurrentPasswordMismatchException();
        }

        return member;
    }

    @Override
    @Transactional
    public MemberStateResponse changeMemberState(Long memberId){
        Member member = this.getById(memberId);
        if(member.getUserState().equals(State.INACTIVATE)){
            member.activate();
        }
        else if(member.getUserState().equals(State.ACTIVATE)){
            member.inactivate();
        }

        memberRepository.save(member);

        return MemberStateResponse.builder()
                .memberState(member.getUserState())
                .build();
    }

    @Override
    @Transactional
    public Member resetMemberPassword(MemberPasswordResetDto memberPasswordResetDto){
        Optional<Member> member = memberRepository.findByEmail(memberPasswordResetDto.getEmail());

        if(!memberPasswordResetDto.getNewPassword().equals(memberPasswordResetDto.getNewPasswordConfirm())){
            throw new ConfirmPasswordMismatchException();
        }

        String encodedPassword = passwordEncoder.encode(memberPasswordResetDto.getNewPassword());
        member.get().changeMemberPassword(encodedPassword);

        return memberRepository.save(member.get());
    }



    @Override
    public Boolean confirmDupEmail(MemberEmailDto memberEmailDto) {
        Optional<Member> member = memberRepository.findByEmail(memberEmailDto.getEmail());
        return member.isEmpty();
    }
}
