package umc.kkijuk.server.login.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
public class CorsCookieInterceptor implements HandlerInterceptor {
    private final String SESSION_COOKIE_NAME = "JSESSIONID";
    private final String SAME_SITE_ATTRIBUTE_VALUES = "; HttpOnly; Secure; SameSite=None";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Collection<String> headers = response.getHeaders(SET_COOKIE);
        if (headers != null && !headers.isEmpty()) {
            log.info("1");
            for (String header : headers) {
                log.info("2");
                log.info(header);
                if (header.startsWith(SESSION_COOKIE_NAME)) {
                    log.info("세션 쿠키 속성 설정");
                    String updatedHeader = header + SAME_SITE_ATTRIBUTE_VALUES;
                    response.setHeader(SET_COOKIE, updatedHeader);
                }
            }
        }

    }
}
