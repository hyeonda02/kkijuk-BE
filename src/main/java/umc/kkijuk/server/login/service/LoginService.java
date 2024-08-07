package umc.kkijuk.server.login.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.MemberEmailNotFoundException;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.exception.IncorrectPasswordException;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.repository.MemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginInfo login(String email, String rawPassword) {
        // 이메일로 멤버를 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberEmailNotFoundException::new);

        validationLoginPassword(member, rawPassword);

        //로그인 성공로직
        return LoginInfo.from(member);
    }

    public void makeLoginSession(LoginInfo loginInfo, HttpServletRequest request) {
        HttpSession session = request.getSession(true); // 있으면 기존 세션 사용, 없으면 세션을 새로 만듬
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, loginInfo);
    }

    private void validationLoginPassword(Member member, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, member.getPassword()))
            throw new IncorrectPasswordException();
    }
}
