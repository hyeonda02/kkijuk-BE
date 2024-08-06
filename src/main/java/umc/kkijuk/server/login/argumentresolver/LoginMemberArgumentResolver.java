package umc.kkijuk.server.login.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import umc.kkijuk.server.login.controller.SessionConst;
import umc.kkijuk.server.login.controller.dto.LoginInfo;
import umc.kkijuk.server.login.exception.UnauthorizedException;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotations = parameter.hasParameterAnnotation(Login.class);
        boolean hasLoginInfoType = LoginInfo.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotations && hasLoginInfoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null || request.getAttribute(SessionConst.LOGIN_MEMBER_INFO) == null) throw new UnauthorizedException();

        return session.getAttribute(SessionConst.LOGIN_MEMBER_INFO);
    }
}
