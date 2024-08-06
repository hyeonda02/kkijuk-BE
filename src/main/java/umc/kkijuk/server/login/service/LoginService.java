package umc.kkijuk.server.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.EmailNotFoundException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.login.exception.IncorrectPasswordException;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginInfo login(String email, String password) {

        // 이메일로 멤버를 조회
        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException());

        findMemberWithAuthenticate(email, password);

//        // 비밀번호가 일치하는지 확인
//        if (!member.getPassword().equals(password)) {
//            throw new IncorrectPasswordException();
//        }

        //로그인 성공로직
        return LoginInfo.builder()
                .memberId(member.getId()) // 위에서 찾은 멤버의 ID를 넣어줘야함.
                .build();

    }
    private Member findMemberWithAuthenticate(String email, String rawPassword) {
        return memberJpaRepository.findByEmail(email)
                .filter(member -> passwordEncoder.matches(rawPassword, member.getPassword()))
                .orElseThrow(() -> new IncorrectPasswordException());
    }
}
