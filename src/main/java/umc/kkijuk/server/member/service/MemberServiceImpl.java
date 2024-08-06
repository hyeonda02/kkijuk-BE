package umc.kkijuk.server.member.service;

import lombok.RequiredArgsConstructor;
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
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member getById(Long memberId) {
        return memberJpaRepository.findById(memberId)
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

        memberJpaRepository.save(joinMember);
        return joinMember;
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
        member.changeFieldInfo(memberFieldDto.getField());
        if(!member.getField().equals(memberFieldDto.getField())){
            throw new FieldUpdateException();
        }
        return member;
    }

    @Override
    @Transactional
    public Member updateMemberInfo(Long memberId, MemberInfoChangeDto memberInfoChangeDto){
        Member member = this.getById(memberId);
        member.changeMemberInfo(memberInfoChangeDto.getPhoneNumber(), memberInfoChangeDto.getBirthDate(), memberInfoChangeDto.getMarketingAgree());
        if(member.getPhoneNumber() == null || member.getBirthDate() == null || member.getMarketingAgree() == null){
            throw new InvalidMemberDataException();
        }
        return member;
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

        memberJpaRepository.save(member);
        return member;
    }

    @Override
    @Transactional
    public Member myPagePasswordAuth(Long memberId, MyPagePasswordAuthDto myPagePasswordAuthDto) {
        Member member = this.getById(memberId);

        if(!passwordEncoder.matches(myPagePasswordAuthDto.getCurrentPassword(), member.getPassword())){
            throw new CurrentPasswordMismatchException();
        }

        return member;
    }

    @Override
    @Transactional
    public EmailAuthResponse getEmailAuth(EmailAddressDto emailAddressDto, int authRandomNumber){
        return EmailAuthResponse.builder()
                .email(emailAddressDto.getEmail())
                .authNumber(authRandomNumber)
                .build();
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

        memberJpaRepository.save(member);

        return MemberStateResponse.builder()
                .deleteDate(member.getDeleteDate())
                .build();
    }

}
