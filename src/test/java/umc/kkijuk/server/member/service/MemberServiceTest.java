package umc.kkijuk.server.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import umc.kkijuk.server.member.controller.response.EmailAuthResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.dto.*;
import umc.kkijuk.server.member.emailauth.MailService;
import umc.kkijuk.server.member.mock.FakeMemberRepository;
import umc.kkijuk.server.member.repository.MemberRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;


class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    @BeforeEach
    void setUp() {
        memberRepository = new FakeMemberRepository();
        passwordEncoder = mock(PasswordEncoder.class);
        memberService = new MemberServiceImpl(memberRepository, passwordEncoder);
    }

}