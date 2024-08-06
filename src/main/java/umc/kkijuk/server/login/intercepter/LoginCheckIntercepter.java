package umc.kkijuk.server.login.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.exception.UnauthorizedException;

@Slf4j
public class LoginCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("{} 인증 필터 동작", requestURI);

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_INFO) == null) {
            log.info("미인증 사용자 요청");

            request.setAttribute("exception", new UnauthorizedException());
            request.getRequestDispatcher("/api/error").forward(request, response);

            return false;
        }

        return true;
    }
}
