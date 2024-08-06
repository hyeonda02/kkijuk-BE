package umc.kkijuk.server.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.login.controller.dto.LoginRequestDto;
import umc.kkijuk.server.login.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> Login(
            HttpServletRequest request,
            @RequestBody LoginRequestDto loginRequestDto) {
        LoginInfo loginInfo = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpSession session = request.getSession(true); // 있으면 기존 세션 사용, 없으면 세션을 새로 만듬
        session.setAttribute(SessionConst.LOGIN_MEMBER_INFO, loginInfo);

        return ResponseEntity
                .ok()
                .body("login success");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> Logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("logout");
            session.invalidate();
        }
        return ResponseEntity
                .ok()
                .body("logout success");
    }

    @GetMapping("sessionInfo")
    public void SessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println("session.getId() = " + session.getId());
        System.out.println("session = " + session.getAttributeNames());
        System.out.println("session.getLastAccessedTime() = " + session.getLastAccessedTime());
    }
}

