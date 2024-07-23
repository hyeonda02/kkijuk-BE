package umc.kkijuk.server.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import umc.kkijuk.server.member.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

}
