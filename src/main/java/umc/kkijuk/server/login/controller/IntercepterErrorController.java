package umc.kkijuk.server.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import umc.kkijuk.server.login.exception.UnauthorizedException;

@Controller
public class IntercepterErrorController {

    @RequestMapping("/api/error")
    public void error(HttpServletRequest request, HttpServletResponse response){
        System.out.println("error 도착");
        throw new UnauthorizedException();
    }
}
